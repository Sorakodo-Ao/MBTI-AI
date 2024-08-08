package com.caiwei.object.model.dto.question;

import lombok.Data;

import java.io.Serializable;

/**
 * 题目答案封装类，用于ai评分
 */
@Data
public class QuestionAnswerDTO implements Serializable {
    private String title;

    private String userAnswer;

    private static final long serialVersionUID = 1L;
}
