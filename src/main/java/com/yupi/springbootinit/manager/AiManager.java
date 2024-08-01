package com.yupi.springbootinit.manager;

import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.exception.BusinessException;
import com.yupi.yucongming.dev.client.YuCongMingClient;
import com.yupi.yucongming.dev.common.BaseResponse;
import com.yupi.yucongming.dev.model.DevChatRequest;
import com.yupi.yucongming.dev.model.DevChatResponse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @description：TODO
 * @author： songgt
 * @create： 2024/7/13 下午2:27
 */
@Service
public class AiManager {

    @Resource
    private YuCongMingClient client;

    /**
     * ai对话
     * @return
     */
    public String doChat(long modelId, String massage){
        DevChatRequest devChatRequest = new DevChatRequest();
        //模型id
        devChatRequest.setModelId(modelId);
        //输入内容
        devChatRequest.setMessage(massage);

        BaseResponse<DevChatResponse> response = client.doChat(devChatRequest);
        if (response == null){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "Ai相应错误");
        }
        return response.getData().getContent();
    }
}
