package com.web.handler;

import com.core.exception.BaseException;
import com.core.http.Response;
import com.core.util.ThrowableUtil;
import com.fasterxml.jackson.core.exc.InputCoercionException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.ServletException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.List;
import java.util.Set;

/**
 * 全局异常处理：全局处理Controller层抛出来的异常：子类直接继承这个类就行了
 */
@Slf4j
@RestControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

    /**
     * 统一处理请求参数校验(普通传参)
     *
     * @param e ConstraintViolationException
     * @return Response
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public Response handleConstraintViolationException(ConstraintViolationException e) {
        StringBuilder message = new StringBuilder();
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        for (ConstraintViolation<?> violation : violations) {
            Path path = violation.getPropertyPath();
            String[] pathArr = StringUtils.splitByWholeSeparatorPreserveAllTokens(path.toString(), ".");
            message.append(pathArr[1]).append(violation.getMessage()).append(",");
        }
        message = new StringBuilder(message.substring(0, message.length() - 1));
        return Response.PARAM.BAD_GENERAL_PARAMETER.setDescription(message.toString());
    }

    /**
     * 统一处理请求参数校验(普通传参)
     *
     * @param e ConstraintViolationException
     * @return Response
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Response handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        StringBuilder message = new StringBuilder();
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        errors.forEach(x -> message.append(x instanceof FieldError ? ((FieldError) x).getField() : "").append(x.getDefaultMessage()).append(";"));
        message.deleteCharAt(message.length() - 1);
        return Response.PARAM.BAD_GENERAL_PARAMETER.setDescription(message.toString());
    }

    /**
     * 参数错误
     *
     * @param e HttpMessageNotReadableException
     * @return Response
     */
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public Response handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.warn("参数格式/语法异常", e);
        return Response.PARAM.GRAMMAR_ERROR.setDescription(ThrowableUtil.getSimpleMessage(e));
    }

    /**
     * 非法参数
     *
     * @param e HttpMessageNotReadableException
     * @return Response
     */
    @ExceptionHandler(value = IllegalArgumentException.class)
    public Response handleIllegalArgumentException(IllegalArgumentException e) {
        log.warn("非法参数", e);
        return Response.PARAM.ILLEGAL.setDescription(ThrowableUtil.getSimpleMessage(e));
    }

    /**
     * 参数越界
     *
     * @param e InputCoercionException
     * @return Response
     */
    @ExceptionHandler(value = InputCoercionException.class)
    public Response handleInputCoercionException(InputCoercionException e) {
        log.warn("参数异常，异常信息", e);
        return Response.PARAM.INPUT_COERCION.setDescription(ThrowableUtil.getSimpleMessage(e));
    }

    /**
     * 统一处理请求参数校验(实体对象传参)
     *
     * @param e BindException
     * @return Response
     */
    @ExceptionHandler(BindException.class)
    public Response handleBindException(BindException e) {
        StringBuilder message = new StringBuilder();
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        for (FieldError error : fieldErrors) {
            message.append(error.getField()).append(error.getDefaultMessage()).append(",");
        }
        message = new StringBuilder(message.substring(0, message.length() - 1));
        return Response.PARAM.BAD_BEAN_PARAMETER.setDescription(message.toString());
    }

    /**
     * 资源未找到
     *
     * @return Response
     */
    @ExceptionHandler(ResponseStatusException.class)
    public Response responseStatusException() {
        log.warn("未找到该资源");
        return Response.CLIENT.NOT_FOND;
    }

    @ExceptionHandler(value = ServletException.class)
    public Response handleServletException(ServletException e) {
        log.warn("服务请求异常", e);
        return Response.SERVER.REQUEST_EXCEPTION.setDescription(ThrowableUtil.getSimpleMessage(e));
    }

    @ExceptionHandler(value = BaseException.class)
    public Response handleException(BaseException e) {
        log.error("系统内部异常，异常信息", e);
        return Response.SERVER.INTERNAL_EXCEPTION.setDescription(ThrowableUtil.getSimpleMessage(e));
    }

    @ExceptionHandler(value = Exception.class)
    public Response handleException(Exception e) {
        log.error("系统错误", e);
        return Response.SERVER.ERROR.setDescription(ThrowableUtil.getSimpleMessage(e));
    }

}
