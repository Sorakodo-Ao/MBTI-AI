package com.caiwei.object.manager;

import com.caiwei.object.common.ErrorCode;
import com.caiwei.object.exception.BusinessException;
import com.zhipu.oapi.ClientV4;
import com.zhipu.oapi.Constants;
import com.zhipu.oapi.service.v4.model.ChatCompletionRequest;
import com.zhipu.oapi.service.v4.model.ChatMessage;
import com.zhipu.oapi.service.v4.model.ChatMessageRole;
import com.zhipu.oapi.service.v4.model.ModelApiResponse;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 调用智谱AI
 */
@Component
public class ZhiPuAiManager {
    @Resource
    private ClientV4 clientV4;
    //随机回答稳定
    private static final Float STABLE_TEMPERATURE = 0.05f;
    //随机回答不稳定
    private static final Float UNSTABLE_TEMPERATURE = 0.99f;

    /**
     * 调用智谱AI通用请求
     *
     * @param messages
     * @param stream
     * @param temperature
     * @return
     */
    public String ZhiPuAi(List<ChatMessage> messages, Boolean stream, Float temperature) {
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .model(Constants.ModelChatGLM4)
                .stream(stream)
                .temperature(temperature)
                .invokeMethod(Constants.invokeMethod)
                .messages(messages)
                .build();
        try {
            ModelApiResponse invokeModelApiResp = clientV4.invokeModelApi(chatCompletionRequest);
            return invokeModelApiResp.getData().getChoices().get(0).toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, e.getMessage());
        }

    }

    /**
     * 调用智谱AI简化请求
     *
     * @param systemMessage
     * @param userMessage
     * @param stream
     * @param temperature
     * @return
     */
    public String ZhiPuAi(String systemMessage, String userMessage, Boolean stream, Float temperature) {
        List<ChatMessage> messages = new ArrayList<>();
        ChatMessage systemChatMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(), systemMessage);
        messages.add(systemChatMessage);
        ChatMessage userChatMessage = new ChatMessage(ChatMessageRole.USER.value(), userMessage);
        messages.add(userChatMessage);
        return ZhiPuAi(messages, stream, temperature);
    }

    /**
     * 调用智谱AI同步请求
     *
     * @param systemMessage
     * @param userMessage
     * @param temperature
     * @return
     */
    public String SyncZhiPuAi(String systemMessage, String userMessage, Float temperature) {
        return ZhiPuAi(systemMessage, userMessage, Boolean.FALSE, temperature);
    }

    /**
     * 调用智谱AI随机回答稳定请求
     *
     * @param systemMessage
     * @param userMessage
     * @return
     */
    public String SyncStableZhiPuAi(String systemMessage, String userMessage) {
        return ZhiPuAi(systemMessage, userMessage, Boolean.FALSE, STABLE_TEMPERATURE);
    }

    /**
     * 调用智谱AI随机回答稳定请求
     *
     * @param systemMessage
     * @param userMessage
     * @return
     */
    public String SyncUnstableZhiPuAi(String systemMessage, String userMessage) {
        return ZhiPuAi(systemMessage, userMessage, Boolean.FALSE, UNSTABLE_TEMPERATURE);
    }
}
