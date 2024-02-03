package com.web.aspect;

import com.core.aspect.BaseAspectSupport;
import com.core.util.JsonUtil;
import com.web.util.AddressUtil;
import com.web.util.WebUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 处理日志，异常
 *
 * @author yuangy
 * @create 2020/4/3 10:49
 */
@Slf4j
@Aspect
public class ControllerEndpointAspect extends BaseAspectSupport {

    /**
     * 换行符
     */
    private static final String LINE_SEPARATOR = System.lineSeparator();

    @Pointcut(CONTROLLER_POINT)
    public void allQuest() {
    }

    @Pointcut("@annotation(com.web.annotation.ControllerEndpoint)")
    public void selfQuest() {
    }

    @Around("allQuest()")
    public Object around(ProceedingJoinPoint point) throws Throwable {

        long start = System.nanoTime();
        Method method = resolveMethod(point);
        Object result;

        Object[] args = point.getArgs();
        // 将 HttpServletResponse 和 HttpServletRequest 参数移除，不然会报异常
        if (Objects.nonNull(args)) {
            List<Object> list = new ArrayList<>();
            for (Object arg : args) {
                if (arg instanceof ServletResponse || arg instanceof ServletRequest) {
                    continue;
                }
                list.add(arg);
            }
            args = list.toArray();
        }

        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
        String[] paramNames = u.getParameterNames(method);

        HttpServletRequest request = WebUtil.getHttpServletRequest();
        String ip = WebUtil.getIp();

        // 开始打印请求日志
        log.info("========================================== Start ==========================================");
        log.info("URL            : {}", request.getRequestURL().toString());
        log.info("HTTP Method    : {}", request.getMethod());
        log.info("Class Method   : {}.{}", point.getSignature().getDeclaringTypeName(), point.getSignature().getName());
        log.info("IP(address)    : {}({})", ip, AddressUtil.getCityInfo(ip));
        if (args != null && paramNames != null) {
            Map<Object, Object> params = new HashMap<>();
            log.info("Request Args   : {}", JsonUtil.toJsonQuietly(handleParams(params, args, Arrays.asList(paramNames))));
        }

        // 执行
        result = point.proceed();

        log.info("Response Args  : {}", JsonUtil.toJson(result));
        log.info("Time-Consuming ：{} ms", (System.nanoTime() - start) / 1000000.0);
        log.info("=========================================== End ===========================================" + LINE_SEPARATOR);

        return result;
    }

    @SuppressWarnings("all")
    private Map<Object, Object> handleParams(Map<Object, Object> params, Object[] args, List paramNames) {
        try {
            for (int i = 0; i < args.length; i++) {
                if (args[i] instanceof Map) {
                    Set set = ((Map) args[i]).keySet();
                    List<Object> list = new ArrayList<>();
                    List<Object> paramList = new ArrayList<>();
                    for (Object key : set) {
                        list.add(((Map) args[i]).get(key));
                        paramList.add(key);
                    }
                    return handleParams(params, list.toArray(), paramList);
                } else {
                    if (args[i] instanceof Serializable) {
                        Class<?> aClass = args[i].getClass();
                        try {
                            aClass.getDeclaredMethod("toString", new Class[]{null});
                            // 如果不抛出 NoSuchMethodException 异常则存在 toString 方法 ，安全的 writeValueAsString ，否则 走 Object的 toString方法
                            params.put(paramNames.get(i), args[i]);
                        } catch (NoSuchMethodException e) {
                            params.put(paramNames.get(i), args[i]);
                        }
                    } else if (args[i] instanceof MultipartFile) {
                        MultipartFile file = (MultipartFile) args[i];
                        params.put(paramNames.get(i), file.getName());
                    } else {
                        params.put(paramNames.get(i), args[i]);
                    }
                }
            }
        } catch (Exception ignore) {
            params.put("exception", "参数解析失败");
        }
        return params;
    }

}



