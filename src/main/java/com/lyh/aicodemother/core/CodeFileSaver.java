package com.lyh.aicodemother.core;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.lyh.aicodemother.ai.model.HtmlCodeResult;
import com.lyh.aicodemother.ai.model.MultiFileCodeResult;
import com.lyh.aicodemother.model.enums.CodeGenTypeEnum;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.charset.StandardCharsets;

public class CodeFileSaver {
    //文件保存的根目录
    public static final String FILE_SAVE_ROOT_DIR= System.getProperty("user.dir") +"/tmp/code_output";

    /**
     * 构建文件的唯一路径: tmp/code_output/bizType_雪花 ID
     *
     * @param bizType
     * @return
     */
    private static String buildUniqueDir(String bizType){
        String uniqueDirName= StrUtil.format("{}_{}",bizType,IdUtil.getSnowflakeNextIdStr());
        String dirPath = FILE_SAVE_ROOT_DIR + File.separator +uniqueDirName;
        FileUtil.mkdir(dirPath);
        return dirPath;
    }

    /**
     * 保存 HTML 网页代码
     * @param htmlCodeResult
     * @return
     */
     public static File saveHtmlCodeResult(HtmlCodeResult htmlCodeResult){
        String baseDirPath = buildUniqueDir(CodeGenTypeEnum.HTML.getValue());
        writeToFile(baseDirPath,"index.html",htmlCodeResult.getHtmlCode());
         return new File(baseDirPath);
     }

    /**
     * 保存多文件代码
     * @param multiFileCodeResult
     * @return
     */
     public static File saveMultiFileCodeResult(MultiFileCodeResult multiFileCodeResult){
        String baseDirPath = buildUniqueDir(CodeGenTypeEnum.MULTI_FILE.getValue());
        writeToFile(baseDirPath,"index.html", multiFileCodeResult.getHtmlCode());
        writeToFile(baseDirPath,"style.css", multiFileCodeResult.getJsCode());
        writeToFile(baseDirPath,"script.js", multiFileCodeResult.getJsCode());
        return new File(baseDirPath);
     }
    /**
     * 保存单个文件
     * @param dirPath
     * @param fileName
     * @param content
     */
    private static void writeToFile(String dirPath,String fileName,String content){
        String filePath = dirPath + File.separator + fileName;
        FileUtil.writeString(content,filePath, StandardCharsets.UTF_8);
    }
}
