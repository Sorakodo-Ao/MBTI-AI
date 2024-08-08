package com.caiwei.object;

import com.caiwei.object.constant.ZhiPuAiKey;
import com.zhipu.oapi.ClientV4;
import com.zhipu.oapi.Constants;
import com.zhipu.oapi.service.v4.model.ChatCompletionRequest;
import com.zhipu.oapi.service.v4.model.ChatMessage;
import com.zhipu.oapi.service.v4.model.ChatMessageRole;
import com.zhipu.oapi.service.v4.model.ModelApiResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class ZhiPuAiTest {
    @Resource
    private ClientV4 clientV4;

    @Test
    void Zhipu() {
        String key = ZhiPuAiKey.KEY;
        /*ClientV4 client = new ClientV4.Builder(key).build();*/
        List<ChatMessage> messages = new ArrayList<>();
        ChatMessage chatMessage = new ChatMessage(ChatMessageRole.USER.value(), "请为春节写一段新年的对联");
        messages.add(chatMessage);

        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .model(Constants.ModelChatGLM4)
                .stream(Boolean.FALSE)
                .invokeMethod(Constants.invokeMethod)
                .messages(messages)
                .build();
        ModelApiResponse invokeModelApiResp = clientV4.invokeModelApi(chatCompletionRequest);
        System.out.println("model output:" + invokeModelApiResp.toString());
        System.out.println("msg=" + invokeModelApiResp.getMsg());
        System.out.println("code=" + invokeModelApiResp.getCode());
        System.out.println("answer=" + invokeModelApiResp.getData().getChoices().get(0));
    }

}
