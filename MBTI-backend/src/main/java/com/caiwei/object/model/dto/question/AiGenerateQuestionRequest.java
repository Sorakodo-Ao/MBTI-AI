package com.caiwei.object.model.dto.question;

import lombok.Data;

import java.io.Serializable;

/**
 * ai生成题目请求
 */
@Data
public class AiGenerateQuestionRequest implements Serializable {
    private Long appId;

    private int questionNumber = 10;

    private int optionNumber = 4;

    private static final long serialVersionUID = 1L;
}
