package com.ndyaya.module.promotion.api.coupon;


import com.ndyaya.framework.common.util.object.BeanUtils;
import com.ndyaya.module.promotion.api.coupon.dto.CouponRespDTO;
import com.ndyaya.module.promotion.api.coupon.dto.CouponUseReqDTO;
import com.ndyaya.module.promotion.service.coupon.CouponService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Map;

/**
 * 优惠劵 API 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class CouponApiImpl implements CouponApi {

    @Resource
    private CouponService couponService;

    @Override
    public List<CouponRespDTO> getCouponListByUserId(Long userId, Integer status) {
        return BeanUtils.toBean(couponService.getCouponList(userId, status), CouponRespDTO.class);
    }

    @Override
    public void useCoupon(CouponUseReqDTO useReqDTO) {
        couponService.useCoupon(useReqDTO.getId(), useReqDTO.getUserId(),
                useReqDTO.getOrderId());
    }

    @Override
    public void returnUsedCoupon(Long id) {
        couponService.returnUsedCoupon(id);
    }

    @Override
    public List<Long> takeCouponsByAdmin(Map<Long, Integer> giveCoupons, Long userId) {
        return couponService.takeCouponsByAdmin(giveCoupons, userId);
    }

    @Override
    public void invalidateCouponsByAdmin(List<Long> giveCouponIds, Long userId) {
        couponService.invalidateCouponsByAdmin(giveCouponIds, userId);
    }

}
