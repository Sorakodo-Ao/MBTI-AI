package com.caiwei.object.scoring;

import com.caiwei.object.common.ErrorCode;
import com.caiwei.object.exception.BusinessException;
import com.caiwei.object.model.entity.App;
import com.caiwei.object.model.entity.UserAnswer;
import com.caiwei.object.model.enums.AppScoringStategyEnum;
import com.caiwei.object.model.enums.AppTypeEnum;
import com.caiwei.object.scoring.Impl.CustomsTestScoringStrategy;
import com.caiwei.object.scoring.Impl.CustomsScoreScoringStrategy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Deprecated
public class ScoringStrategyContext {

    @Resource
    private CustomsScoreScoringStrategy customsScoreScoringStrategy;

    @Resource
    private CustomsTestScoringStrategy customsTestScoringStrategy;

    /**
     * 评分
     *
     * @param choiceList
     * @param app
     * @return
     * @throws Exception
     */
    public UserAnswer doScore(List<String> choiceList, App app) throws Exception {
        AppTypeEnum appTypeEnum = AppTypeEnum.getEnumByValue(app.getAppType());
        AppScoringStategyEnum appScoringStrategyEnum = AppScoringStategyEnum.getEnumByValue(app.getScoringStrategy());
        if (appTypeEnum == null || appScoringStrategyEnum == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "应用配置有误，未找到匹配的策略");
        }
        // 根据不同的应用类别和评分策略，选择对应的策略执行
        switch (appTypeEnum) {
            case SCORE:
                switch (appScoringStrategyEnum) {
                    case CUSTOM:
                        return customsScoreScoringStrategy.doScore(choiceList, app);
                    case AI:
                        break;
                }
                break;
            case TEST:
                switch (appScoringStrategyEnum) {
                    case CUSTOM:
                        return customsTestScoringStrategy.doScore(choiceList, app);
                    case AI:
                        break;
                }
                break;
        }
        throw new BusinessException(ErrorCode.SYSTEM_ERROR, "应用配置有误，未找到匹配的策略");
    }
}
