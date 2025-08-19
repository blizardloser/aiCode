package com.lyh.aicodemother.core.parse;


import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.lyh.aicodemother.ai.model.MultiFileCodeResult;

/**
 * 多文件代码解析器（HTML + CSS + JS）
 *
 * @author yupi
 *///
public class MutiFileCodeParser implements CodeParser<MultiFileCodeResult>{
private static final Pattern HTML_CODE_PATTERN = Pattern.compile("```html\\s*\\n([\\s\\S]*?)```", Pattern.CASE_INSENSITIVE);
private static final Pattern CSS_CODE_PATTERN = Pattern.compile("```css\\s*\\n([\\s\\S]*?)```", Pattern.CASE_INSENSITIVE);
private static final Pattern JS_CODE_PATTERN = Pattern.compile("```(?:js|javascript)\\s*\\n([\\s\\S]*?)```", Pattern.CASE_INSENSITIVE);

@Override
    public MultiFileCodeResult parseCode(String codeContent) {
        MultiFileCodeResult result = new MultiFileCodeResult();
        String htmlCodeContent = extractMutiFileCode(codeContent,HTML_CODE_PATTERN);
        String cssCodeContent = extractMutiFileCode(codeContent,CSS_CODE_PATTERN);
        String jsCodeContent = extractMutiFileCode(codeContent,JS_CODE_PATTERN);
        if(htmlCodeContent!=null && !htmlCodeContent.trim().isEmpty()){
            result.setHtmlCode(htmlCodeContent);
        }
        if(cssCodeContent!=null && !cssCodeContent.trim().isEmpty()){
            result.setCssCode(cssCodeContent);
        }
        if(jsCodeContent!=null && !jsCodeContent.trim().isEmpty()){
            result.setJsCode(jsCodeContent);
        }
        return result;
    }
    /**
     * 根据正则模式提取代码
     *
     * @param content 原始内容
     * @param pattern 正则模式
     * @return 提取的代码
     */
    private String extractMutiFileCode(String content, Pattern pattern){
        Matcher matcher = pattern.matcher(content);
        if(matcher.find()){
            return matcher.group(1);
        }
        return null;
    }
}


