import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author yuangy
 * @create 2020-07-01 15:12
 */
public class PasswordTest {

    @Test
    public void encodeTest() {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode = passwordEncoder.encode("123456");
        System.out.println(encode);
        encode = passwordEncoder.encode("123456");
        System.out.println(encode);

    }

}
