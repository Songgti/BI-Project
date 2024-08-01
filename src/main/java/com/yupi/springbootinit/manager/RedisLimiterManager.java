package com.yupi.springbootinit.manager;

import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.exception.BusinessException;
import com.yupi.springbootinit.exception.ThrowUtils;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.redisson.client.RedisClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @description：专门提供 RedisLimiter 限流基础服务的（提供了通用能力）
 * @author： songgt
 * @create： 2024/7/22 下午6:49
 */
@Service
public class RedisLimiterManager {

    @Resource
    private RedissonClient redissonClient;

    /**
     * 限流操作
     * @param key 用于区分不同的限流器，比如不同的用户id应该分别统计
     */
    public void doReteLimit(String key){

        // 创建或获取名为"userAccessLimiter"的RateLimiter实例，限制每秒最多访问5次
        RRateLimiter rateLimiter = redissonClient.getRateLimiter(key);
        // 初始化RateLimiter，设置类型为PER_CLIENT，每秒生成5个令牌，适用于每个独立客户端
        rateLimiter.trySetRate(RateType.OVERALL, 2, 1, RateIntervalUnit.SECONDS);

        boolean result = rateLimiter.tryAcquire(1);
        if (!result){
            throw new BusinessException(ErrorCode.TOO_MANY_REQUEST);
        }
    }
}
