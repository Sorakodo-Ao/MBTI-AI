package com.caiwei.object.scoring.Impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.caiwei.object.model.dto.question.QuestionContentDTO;
import com.caiwei.object.model.entity.App;
import com.caiwei.object.model.entity.Question;
import com.caiwei.object.model.entity.ScoringResult;
import com.caiwei.object.model.entity.UserAnswer;
import com.caiwei.object.model.vo.QuestionVO;
import com.caiwei.object.scoring.ScoringStrategy;
import com.caiwei.object.scoring.strategyAnno.ScoringStrategyConfig;
import com.caiwei.object.service.QuestionService;
import com.caiwei.object.service.ScoringResultService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@ScoringStrategyConfig(appType = 0, scoringStrategy = 0)
public class CustomsScoreScoringStrategy implements ScoringStrategy {
    @Resource
    private QuestionService questionService;

    @Resource
    private ScoringResultService scoringResultService;


    @Override
    public UserAnswer doScore(List<String> choices, App app) throws Exception {
        //1.根据ID查询到题目和题目结果信息，按分数降序排序
        Long appId = app.getId();
        Question question = questionService.getOne(
                Wrappers.lambdaQuery(Question.class).eq(Question::getAppId, appId)
        );
        List<ScoringResult> scoringResultList = scoringResultService.list(
                Wrappers.lambdaQuery(ScoringResult.class).eq(ScoringResult::getAppId, appId)
                        .orderByDesc(ScoringResult::getResultScoreRange)
        );

        //2.统计用户总得分
        int totalScore = 0;
        QuestionVO questionVO = QuestionVO.objToVo(question);
        List<QuestionContentDTO> questionContentDTOList = questionVO.getQuestionContent();

        //遍历题目列表
        for (QuestionContentDTO questionContentDTO : questionContentDTOList) {
            //遍历答案列表
            for (String answer : choices) {
                //遍历题目中的选项
                for (QuestionContentDTO.Option option : questionContentDTO.getOptions()) {
                    //答案和选项中的key一致
                    if (option.getKey().equals(answer)) {
                        String result = option.getResult();

                        int score = Optional.of(option.getScore()).orElse(0);
                        totalScore += score;
                    }
                }
            }
        }

        //3.遍历得分结果，找到第一个用户分数大于得分范围的结果，作为最终结果
        ScoringResult maxScoringResult = scoringResultList.get(0);
        for (ScoringResult scoringResult : scoringResultList) {
            if (totalScore >= scoringResult.getResultScoreRange()) {
                maxScoringResult = scoringResult;
                break;
            }
        }


        //4.构造返回值返回
        UserAnswer userAnswer = new UserAnswer();

        userAnswer.setAppId(app.getId());
        userAnswer.setAppType(app.getAppType());
        userAnswer.setScoringStrategy(app.getScoringStrategy());
        userAnswer.setChoices(JSONUtil.toJsonStr(choices));
        userAnswer.setResultId(maxScoringResult.getId());
        userAnswer.setResultName(maxScoringResult.getResultName());
        userAnswer.setResultDesc(maxScoringResult.getResultDesc());
        userAnswer.setResultPicture(maxScoringResult.getResultPicture());
        userAnswer.setResultScore(totalScore);

        return userAnswer;
    }
}
