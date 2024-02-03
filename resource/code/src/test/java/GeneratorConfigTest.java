import com.bean.mysql.GeneratorConfig;
import com.code.CodeApplication;
import com.code.mapper.GeneratorConfigMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @author yuangy
 * @create 2020-07-09 15:24
 */
@SpringBootTest(classes = CodeApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class GeneratorConfigTest {

    @Resource
    private GeneratorConfigMapper generatorConfigMapper;

    @Test
    public void insertTest() {
        GeneratorConfig generatorConfig = new GeneratorConfig();
        generatorConfig.setAuthor("test");
        generatorConfig.setBasePackage("com.test");
        generatorConfig.setEntityPackage("entity");
        generatorConfig.setMapperPackage("mapper");
        generatorConfig.setMapperXmlPackage("mapper");
        generatorConfig.setServicePackage("service");
        generatorConfig.setServiceImplPackage("service.impl");
        generatorConfig.setControllerPackage("controller");
        generatorConfig.setIsTrim(0);
        generatorConfig.setTrimValue("");
        generatorConfigMapper.insert(generatorConfig);

    }
}
