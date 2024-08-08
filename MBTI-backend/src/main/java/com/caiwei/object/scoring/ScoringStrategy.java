package com.caiwei.object.scoring;

import com.caiwei.object.model.entity.App;
import com.caiwei.object.model.entity.UserAnswer;

import java.util.List;


/**
 *  评分策略接口
 */
public interface ScoringStrategy {

    /**
     * 执行评分
     *
     * @param choices
     * @param app
     * @return
     * @throws Exception
     */
    UserAnswer doScore(List<String> choices, App app) throws Exception;
}
