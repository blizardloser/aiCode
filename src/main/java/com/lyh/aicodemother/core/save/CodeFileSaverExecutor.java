package com.lyh.aicodemother.core.save;

import com.lyh.aicodemother.ai.model.HtmlCodeResult;
import com.lyh.aicodemother.ai.model.MultiFileCodeResult;
import com.lyh.aicodemother.exception.BusinessException;
import com.lyh.aicodemother.exception.ErrorCode;
import com.lyh.aicodemother.model.enums.CodeGenTypeEnum;
import org.springframework.validation.ObjectError;

import java.io.File;

/**
 * 代码文件保存执行器
 * 根据代码生成类型执行相应的保存逻辑
 */
public class CodeFileSaverExecutor {
    private static final MutilFileSaverTemplate mutilFileSaverTemplate = new MutilFileSaverTemplate();

    private static final HtmlCodeFileSaverTemplate htmlCodeFileSaverTemplate = new HtmlCodeFileSaverTemplate();

    public static File executeSaver(Object result,CodeGenTypeEnum codeGenTypeEnum){
        return switch (codeGenTypeEnum){
            case HTML -> htmlCodeFileSaverTemplate.saveCode((HtmlCodeResult) result);
            case MULTI_FILE -> mutilFileSaverTemplate.saveCode((MultiFileCodeResult) result);
            default -> throw new BusinessException(ErrorCode.SYSTEM_ERROR,"不支持该类型的生成");
        };
    }
}
