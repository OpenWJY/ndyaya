package com.ndyaya.module.trade.job.order;

import com.ndyaya.framework.quartz.core.handler.JobHandler;
import com.ndyaya.framework.tenant.core.job.TenantJob;
import com.ndyaya.module.trade.service.order.TradeOrderUpdateService;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;

/**
 * 交易订单的自动过期 Job
 *
 * @author 芋道源码
 */
@Component
public class TradeOrderAutoCancelJob implements JobHandler {

    @Resource
    private TradeOrderUpdateService tradeOrderUpdateService;

    @Override
    @TenantJob
    public String execute(String param) {
        int count = tradeOrderUpdateService.cancelOrderBySystem();
        return String.format("过期订单 %s 个", count);
    }

}
