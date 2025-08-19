package com.lyh.aicodemother.core.parse;

import com.lyh.aicodemother.exception.BusinessException;
import com.lyh.aicodemother.exception.ErrorCode;
import com.lyh.aicodemother.model.enums.CodeGenTypeEnum;

/**
 *
 */
public class CodeParserExecutor {
    private final static HtmlCodeParser htmlParser = new HtmlCodeParser();

    private final static MutiFileCodeParser mutiFileParser = new MutiFileCodeParser();

    public static Object executeParser(String codeContent, CodeGenTypeEnum codeGenTypeEnum){
      return switch (codeGenTypeEnum){
          case HTML ->htmlParser.parseCode(codeContent);
          case MULTI_FILE -> mutiFileParser.parseCode(codeContent);
          default -> throw new BusinessException(ErrorCode.SYSTEM_ERROR,"不支持该类的生成" +codeGenTypeEnum);
      };
    }
 }
