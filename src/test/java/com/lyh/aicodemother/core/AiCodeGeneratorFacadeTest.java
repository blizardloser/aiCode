package com.lyh.aicodemother.core;

import com.lyh.aicodemother.model.enums.CodeGenTypeEnum;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class AiCodeGeneratorFacadeTest {
    @Resource
    private AiCodeGeneratorFacade aiCodeGeneratorFacade;

    @Test
    void generateAndSaveHtmlCode() {

    }

    @Test
    void generatorAndSaveMultiFileCode() {
    }

    @Test
    void generateAndSaveCode() {
        File file = aiCodeGeneratorFacade.generateAndSaveCode("生成一个登录页面，不超过二十行代码", CodeGenTypeEnum.HTML);
        Assertions.assertNotNull(file);
    }

    @Test
    void generateAndSaveCodeStream() {
        Flux<String> codeStream = aiCodeGeneratorFacade.generateAndSaveCodeStream("生成一个登录页面，总共不超过二十行代码",CodeGenTypeEnum.HTML);
        // 阻塞等待所有数据收集完成
        List<String> result = codeStream.collectList().block();
        //验证结果
        Assertions.assertNotNull(result);
        //拼接字符串得到完整内容
        String completeContent = String.join("",result);
        Assertions.assertNotNull(completeContent);
    }

    @Test
    void testGenerateAndSaveHtmlCode() {
    }

    @Test
    void testGeneratorAndSaveMultiFileCode() {
    }
}
