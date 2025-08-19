package com.lyh.aicodemother.core.save;

import cn.hutool.core.util.StrUtil;
import com.lyh.aicodemother.ai.model.HtmlCodeResult;
import com.lyh.aicodemother.exception.BusinessException;
import com.lyh.aicodemother.exception.ErrorCode;
import com.lyh.aicodemother.model.enums.CodeGenTypeEnum;

public class HtmlCodeFileSaverTemplate extends CodeFileSaverTemplate<HtmlCodeResult>{
    @Override
    protected CodeGenTypeEnum getCodeType() {
        return CodeGenTypeEnum.HTML;
    }

    @Override
    protected void saveFiles(HtmlCodeResult result, String baseDirPath) {
        writeToFile(baseDirPath,"index.html", result.getHtmlCode());
    }

    @Override
    public void validateInput(HtmlCodeResult result) {
        super.validateInput(result);
        if(StrUtil.isBlank(result.getHtmlCode())){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"html文件不能为空为空");
        }
    }
}
