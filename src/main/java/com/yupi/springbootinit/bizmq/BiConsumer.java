package com.yupi.springbootinit.bizmq;

import com.rabbitmq.client.Channel;
import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.constant.CommonConstant;
import com.yupi.springbootinit.exception.BusinessException;
import com.yupi.springbootinit.manager.AiManager;
import com.yupi.springbootinit.model.entity.Chart;
import com.yupi.springbootinit.service.ChartService;
import com.yupi.springbootinit.utils.ExcelUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @description：RabbitMQ 消费者代码
 * @author： songgt
 * @create： 2024/7/29 下午10:08
 */
@Component
@Slf4j
public class BiConsumer {

    @Resource
    private ChartService chartService;

    @Resource
    private AiManager aiManager;

    @RabbitListener(queues = {BiMqConstant.BI_QUEUE_NAME}, ackMode = "MANUAL")
    public void receiveMessage(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws Exception {
        //失败则拒绝
        if (StringUtils.isBlank(message)){
            channel.basicNack(deliveryTag,false,false);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "消息为空");
        }
        long chartId = Long.parseLong(message);
        Chart chart = chartService.getById(chartId);
        if (chart == null){
            channel.basicNack(deliveryTag,false,false);
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "图表为空");
        }

        //先修改图表的任务状态为“执行中”，等执行完成后，改为“已完成”，保存执行结果；
        //执行失败改为“失败”，记录失败信息
        Chart updateChart = new Chart();
        updateChart.setId(chart.getId());
        updateChart.setStatus("running");
        boolean updateById = chartService.updateById(updateChart);
        if (!updateById) {
            handleChartUpdateError(chart.getId(), "更新图表”执行中“状态失败!");
            return;
        }
        //调用AI
        String result = aiManager.doChat(CommonConstant.BI_MODEL_ID, buildUserInput(chart));
        //拆分字符串
        String[] splits = result.split("【【【【【");
        if (splits.length < 3) {
            channel.basicNack(deliveryTag,false,false);
            handleChartUpdateError(chart.getId(), "AI生成错误!");
            return;
        }
        String genChart = splits[1].trim();
        String genResult = splits[2].trim();

        Chart updateChartResult = new Chart();
        updateChartResult.setId(chart.getId());
        updateChartResult.setGenchart(genChart);
        updateChartResult.setGenResult(genResult);
        updateChartResult.setStatus("succeed");
        boolean updateById2 = chartService.updateById(updateChartResult);
        if (!updateById2) {
            channel.basicNack(deliveryTag,false,false);
            handleChartUpdateError(chart.getId(), "更新图表”成功“状态失败!");
        }
        //消息确认
        channel.basicAck(deliveryTag, false);
    }

    /**
     * 构建用户输入
     * @param chart
     * @return
     */
    private String buildUserInput(Chart chart) {
        String goal = chart.getGoal();
        String chartType = chart.getChartType();
        //获取图表原始数据
        String csvData = chartService.queryChartDate(chart.getId());

        StringBuffer userInput = new StringBuffer();
        userInput.append("分析目标：").append("\n");

        // 拼接图表类型
        String userGoal = goal;
        if (StringUtils.isNotBlank(chartType)) {
            userGoal += "\n请使用" + chartType;
        }
        userInput.append(userGoal).append("\n");
        userInput.append("原始数据").append("\n");
        //读取用户上传的文件，进行预处理
        userInput.append(csvData).append("\n");

        return userInput.toString();
    }

    private void handleChartUpdateError(long chartId, String execMessage) {
        Chart updateChartResult = new Chart();
        updateChartResult.setId(chartId);
        updateChartResult.setStatus("failed");
        updateChartResult.setExecMessage("execMessage:" + execMessage);
        boolean b = chartService.updateById(updateChartResult);
        if (!b) {
            log.error("更新失败" + chartId + "," + execMessage);
        }
    }
}
