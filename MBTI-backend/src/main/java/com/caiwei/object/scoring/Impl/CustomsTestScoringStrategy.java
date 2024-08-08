package com.caiwei.object.scoring.Impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.caiwei.object.model.dto.question.QuestionContentDTO;
import com.caiwei.object.model.entity.App;
import com.caiwei.object.model.entity.Question;
import com.caiwei.object.model.entity.ScoringResult;
import com.caiwei.object.model.entity.UserAnswer;
import com.caiwei.object.model.vo.QuestionVO;
import com.caiwei.object.scoring.strategyAnno.ScoringStrategyConfig;
import com.caiwei.object.scoring.ScoringStrategy;
import com.caiwei.object.service.QuestionService;
import com.caiwei.object.service.ScoringResultService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 自定义评分测评
 */
@ScoringStrategyConfig(appType = 1, scoringStrategy = 0)
public class CustomsTestScoringStrategy implements ScoringStrategy {

    @Resource
    private QuestionService questionService;

    @Resource
    private ScoringResultService scoringResultService;


    @Override
    public UserAnswer doScore(List<String> choices, App app) throws Exception {
        //1.根据Id查询到题目和结果信息
        Question question = questionService.getOne(
                Wrappers.lambdaQuery(Question.class).eq(Question::getAppId, app.getId())
        );
        List<ScoringResult> scoringResultList = scoringResultService.list(
                Wrappers.lambdaQuery(ScoringResult.class).eq(ScoringResult::getAppId, app.getId())
        );

        //2.统计用户每个选择对应的属性个数
        Map<String, Integer> optionCount = new HashMap<>();

        QuestionVO questionVO = QuestionVO.objToVo(question);
        List<QuestionContentDTO> questionContentDTOList = questionVO.getQuestionContent();


        for (QuestionContentDTO questionContentDTO : questionContentDTOList) {
            for (String answer : choices) {
                for (QuestionContentDTO.Option option : questionContentDTO.getOptions()) {
                    if (option.getKey().equals(answer)) {
                        String result = option.getResult();

                        if (!optionCount.containsKey(result)) {
                            optionCount.put(result, 0);
                        }

                        optionCount.put(result, optionCount.get(result) + 1);
                    }
                }
            }
        }

        //3.遍历每种评分结果，计算哪个结果的得分更高
        int maxScore = 0;
        ScoringResult maxScoreResult = scoringResultList.get(0);

        for (ScoringResult scoringResult : scoringResultList) {
            List<String> resultProp = JSONUtil.toList(scoringResult.getResultProp(), String.class);
            int score = resultProp.stream().mapToInt(prop -> optionCount.getOrDefault(prop, 0)).sum();

            if (score > maxScore) {
                maxScore = score;
                maxScoreResult = scoringResult;
            }
        }
        //4.构造返回值
        UserAnswer userAnswer = new UserAnswer();

        userAnswer.setAppId(app.getId());
        userAnswer.setAppType(app.getAppType());
        userAnswer.setScoringStrategy(app.getScoringStrategy());
        userAnswer.setChoices(JSONUtil.toJsonStr(choices));
        userAnswer.setResultId(maxScoreResult.getId());
        userAnswer.setResultName(maxScoreResult.getResultName());
        userAnswer.setResultDesc(maxScoreResult.getResultDesc());
        userAnswer.setResultPicture(maxScoreResult.getResultPicture());

        return userAnswer;
    }
}
