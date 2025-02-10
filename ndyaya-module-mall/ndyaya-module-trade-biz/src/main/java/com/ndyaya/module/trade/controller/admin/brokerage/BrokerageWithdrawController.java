package com.ndyaya.module.trade.controller.admin.brokerage;

import com.ndyaya.framework.common.pojo.CommonResult;
import com.ndyaya.framework.common.pojo.PageResult;
import com.ndyaya.module.member.api.user.MemberUserApi;
import com.ndyaya.module.member.api.user.dto.MemberUserRespDTO;
import com.ndyaya.module.pay.api.notify.dto.PayTransferNotifyReqDTO;
import com.ndyaya.module.trade.controller.admin.brokerage.vo.withdraw.BrokerageWithdrawRejectReqVO;
import com.ndyaya.module.trade.controller.admin.brokerage.vo.withdraw.BrokerageWithdrawPageReqVO;
import com.ndyaya.module.trade.controller.admin.brokerage.vo.withdraw.BrokerageWithdrawRespVO;
import com.ndyaya.module.trade.convert.brokerage.BrokerageWithdrawConvert;
import com.ndyaya.module.trade.dal.dataobject.brokerage.BrokerageWithdrawDO;
import com.ndyaya.module.trade.enums.brokerage.BrokerageWithdrawStatusEnum;
import com.ndyaya.module.trade.service.brokerage.BrokerageWithdrawService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import java.util.Map;

import static com.ndyaya.framework.common.pojo.CommonResult.success;
import static com.ndyaya.framework.common.util.collection.CollectionUtils.convertSet;
import static com.ndyaya.framework.common.util.servlet.ServletUtils.getClientIP;

@Tag(name = "管理后台 - 佣金提现")
@RestController
@RequestMapping("/trade/brokerage-withdraw")
@Validated
@Slf4j
public class BrokerageWithdrawController {

    @Resource
    private BrokerageWithdrawService brokerageWithdrawService;

    @Resource
    private MemberUserApi memberUserApi;

    @PutMapping("/approve")
    @Operation(summary = "通过申请")
    @PreAuthorize("@ss.hasPermission('trade:brokerage-withdraw:audit')")
    public CommonResult<Boolean> approveBrokerageWithdraw(@RequestParam("id") Long id) {
        brokerageWithdrawService.auditBrokerageWithdraw(id,
                BrokerageWithdrawStatusEnum.AUDIT_SUCCESS, "", getClientIP());
        return success(true);
    }

    @PutMapping("/reject")
    @Operation(summary = "驳回申请")
    @PreAuthorize("@ss.hasPermission('trade:brokerage-withdraw:audit')")
    public CommonResult<Boolean> rejectBrokerageWithdraw(@Valid @RequestBody BrokerageWithdrawRejectReqVO reqVO) {
        brokerageWithdrawService.auditBrokerageWithdraw(reqVO.getId(),
                BrokerageWithdrawStatusEnum.AUDIT_FAIL, reqVO.getAuditReason(), getClientIP());
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得佣金提现")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('trade:brokerage-withdraw:query')")
    public CommonResult<BrokerageWithdrawRespVO> getBrokerageWithdraw(@RequestParam("id") Long id) {
        BrokerageWithdrawDO brokerageWithdraw = brokerageWithdrawService.getBrokerageWithdraw(id);
        return success(BrokerageWithdrawConvert.INSTANCE.convert(brokerageWithdraw));
    }

    @GetMapping("/page")
    @Operation(summary = "获得佣金提现分页")
    @PreAuthorize("@ss.hasPermission('trade:brokerage-withdraw:query')")
    public CommonResult<PageResult<BrokerageWithdrawRespVO>> getBrokerageWithdrawPage(@Valid BrokerageWithdrawPageReqVO pageVO) {
        // 分页查询
        PageResult<BrokerageWithdrawDO> pageResult = brokerageWithdrawService.getBrokerageWithdrawPage(pageVO);

        // 拼接信息
        Map<Long, MemberUserRespDTO> userMap = memberUserApi.getUserMap(
                convertSet(pageResult.getList(), BrokerageWithdrawDO::getUserId));
        return success(BrokerageWithdrawConvert.INSTANCE.convertPage(pageResult, userMap));
    }

    // TODO @luchi：update-transferred，url 改成这个。和 update-paid 、update-refunded 保持一致
    @PostMapping("/update-transfer")
    @Operation(summary = "更新转账订单为转账成功") // 由 pay-module 支付服务，进行回调，可见 PayNotifyJob
    @PermitAll // 无需登录，安全由 PayDemoOrderService 内部校验实现
    public CommonResult<Boolean> updateBrokerageWithdrawTransferred(@RequestBody PayTransferNotifyReqDTO notifyReqDTO) {
        log.info("[updateAfterRefund][notifyReqDTO({})]", notifyReqDTO);
        brokerageWithdrawService.updateBrokerageWithdrawTransferred(
                Long.parseLong(notifyReqDTO.getMerchantTransferId()), notifyReqDTO.getPayTransferId());
        return success(true);
    }

}
