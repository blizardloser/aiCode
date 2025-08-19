package com.lyh.aicodemother.core.save;

import cn.hutool.core.util.StrUtil;
import com.lyh.aicodemother.ai.model.HtmlCodeResult;
import com.lyh.aicodemother.ai.model.MultiFileCodeResult;
import com.lyh.aicodemother.exception.BusinessException;
import com.lyh.aicodemother.exception.ErrorCode;
import com.lyh.aicodemother.model.enums.CodeGenTypeEnum;

public class MutilFileSaverTemplate extends CodeFileSaverTemplate<MultiFileCodeResult>{

    @Override
    protected CodeGenTypeEnum getCodeType() {
        return CodeGenTypeEnum.MULTI_FILE;
    }

    @Override
    protected void saveFiles(MultiFileCodeResult result, String baseDirPath) {
        //保存HTML文件
        writeToFile(baseDirPath,"index.html",result.getHtmlCode());

        //保存CSS文件
        writeToFile(baseDirPath,"style.css",result.getCssCode());

        //保存JavaScript文件
        writeToFile(baseDirPath,"javascript.js",result.getJsCode());
    }
    @Override
    public void validateInput(MultiFileCodeResult result) {
        super.validateInput(result);
        if(StrUtil.isBlank(result.getHtmlCode())){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"html文件不能为空为空");
        }
    }

}
