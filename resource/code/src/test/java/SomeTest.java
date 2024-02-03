import com.core.constant.ServiceConstant;
import org.junit.Test;

/**
 * @author yuangy
 * @create 2020-07-10 14:35
 */
public class SomeTest {

    @Test
    public void Test() {
        String restPart = "doc.enable";
        String defaultValue = "false";
        System.out.println("${" + ServiceConstant.PROPERTIES_PREFIX + restPart + (defaultValue == null ? "}" : ":" + defaultValue + "}"));
    }

}
