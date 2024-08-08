package com.caiwei.object.scoring.Impl;

import com.caiwei.object.model.entity.App;
import com.caiwei.object.model.entity.UserAnswer;
import com.caiwei.object.scoring.ScoringStrategy;

import java.util.List;

public class TestScoringStrategy implements ScoringStrategy {
    @Override
    public UserAnswer doScore(List<String> choices, App app) throws Exception {
        return null;
    }
}
