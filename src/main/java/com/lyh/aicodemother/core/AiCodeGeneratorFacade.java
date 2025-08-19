package com.lyh.aicodemother.core;

import ch.qos.logback.core.spi.ErrorCodes;
import com.lyh.aicodemother.ai.AiCodeGeneratorService;
import com.lyh.aicodemother.ai.model.HtmlCodeResult;
import com.lyh.aicodemother.ai.model.MultiFileCodeResult;
import com.lyh.aicodemother.core.parse.CodeParserExecutor;
import com.lyh.aicodemother.core.save.CodeFileSaverExecutor;
import com.lyh.aicodemother.exception.BusinessException;
import com.lyh.aicodemother.exception.ErrorCode;
import com.lyh.aicodemother.model.enums.CodeGenTypeEnum;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.io.File;

@Slf4j
@Service
public class AiCodeGeneratorFacade {
    @Resource
    private AiCodeGeneratorService aiCodeGeneratorService;

//    public File aiCodeGenerator(String useMessage,CodeGenTypeEnum codeGenTypeEnum){
//        if(codeGenTypeEnum == null){
//            throw new BusinessException( ErrorCode.SYSTEM_ERROR,"生成类型为空");
//        }
//        return switch (codeGenTypeEnum){
//            case HTML -> generateAndSaveHtmlCode(useMessage);
//            case MULTI_FILE -> generatorAndSaveMultiFileCode(useMessage);
//            default -> {
//                String errorMessage = "不支持该类型生成" + codeGenTypeEnum.getValue();
//                throw new BusinessException( ErrorCode.SYSTEM_ERROR,errorMessage);
//            }
//        };
//
//    }
    /**
     * 统一入口：根据类型生成并保存代码
     *
     * @param userMessage     用户提示词
     * @param codeGenTypeEnum 生成类型
     * @return 保存的目录
     */
    public File generateAndSaveCode(String userMessage, CodeGenTypeEnum codeGenTypeEnum) {
        if (codeGenTypeEnum == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "生成类型为空");
        }
        return switch (codeGenTypeEnum) {
            case HTML -> {
                HtmlCodeResult result = aiCodeGeneratorService.generateHtmlCode(userMessage);
                yield CodeFileSaverExecutor.executeSaver(result, CodeGenTypeEnum.HTML);
            }
            case MULTI_FILE -> {
                MultiFileCodeResult result = aiCodeGeneratorService.generateMultiFileCode(userMessage);
                yield CodeFileSaverExecutor.executeSaver(result, CodeGenTypeEnum.MULTI_FILE);
            }
            default -> {
                String errorMessage = "不支持的生成类型：" + codeGenTypeEnum.getValue();
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, errorMessage);
            }
        };
    }

    /**
     * 统一入口：根据类型生成并保存代码（流式）
     *
     * @param userMessage     用户提示词
     * @param codeGenTypeEnum 生成类型
     */
    public Flux<String> generateAndSaveCodeStream(String userMessage, CodeGenTypeEnum codeGenTypeEnum) {
        if (codeGenTypeEnum == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "生成类型为空");
        }
        return switch (codeGenTypeEnum) {
            case HTML -> {
                Flux<String> codeStream = aiCodeGeneratorService.generateHtmlCodeStream(userMessage);
                yield processCodeStream(codeStream, CodeGenTypeEnum.HTML);
            }
            case MULTI_FILE -> {
                Flux<String> codeStream = aiCodeGeneratorService.generateMultiFileCodeStream(userMessage);
                yield processCodeStream(codeStream, CodeGenTypeEnum.MULTI_FILE);
            }
            default -> {
                String errorMessage = "不支持的生成类型：" + codeGenTypeEnum.getValue();
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, errorMessage);
            }
        };
    }


    /**
     * 通用流式代码处理方法
     *
     * @param codeStream  代码流
     * @param codeGenType 代码生成类型
     * @return 流式响应
     */
    private Flux<String> processCodeStream(Flux<String> codeStream, CodeGenTypeEnum codeGenType) {
        StringBuilder codeBuilder = new StringBuilder();
        return codeStream.doOnNext(chunk -> {
            // 实时收集代码片段
            codeBuilder.append(chunk);
        }).doOnComplete(() -> {
            // 流式返回完成后保存代码
            try {
                String completeCode = codeBuilder.toString();
                // 使用执行器解析代码
                Object parsedResult = CodeParserExecutor.executeParser(completeCode, codeGenType);
                // 使用执行器保存代码
                File savedDir = CodeFileSaverExecutor.executeSaver(parsedResult,codeGenType);
                log.info("保存成功，路径为：" + savedDir.getAbsolutePath());
            } catch (Exception e) {
                log.error("保存失败: {}", e.getMessage());
            }
        });
    }

    public File generateAndSaveHtmlCode(String  useMessage){
        HtmlCodeResult result  = aiCodeGeneratorService.generateHtmlCode(useMessage);
        return CodeFileSaver.saveHtmlCodeResult(result);
    }

    public File generatorAndSaveMultiFileCode(String  useMessage){
        MultiFileCodeResult result  = aiCodeGeneratorService.generateMultiFileCode(useMessage);
        return CodeFileSaver.saveMultiFileCodeResult(result);
    }
}
