package com.ndyaya.module.product.controller.admin.history;

import cn.hutool.core.collection.CollUtil;
import com.ndyaya.framework.common.pojo.CommonResult;
import com.ndyaya.framework.common.pojo.PageResult;
import com.ndyaya.framework.common.util.object.BeanUtils;
import com.ndyaya.module.product.controller.admin.history.vo.ProductBrowseHistoryPageReqVO;
import com.ndyaya.module.product.controller.admin.history.vo.ProductBrowseHistoryRespVO;
import com.ndyaya.module.product.dal.dataobject.history.ProductBrowseHistoryDO;
import com.ndyaya.module.product.dal.dataobject.spu.ProductSpuDO;
import com.ndyaya.module.product.service.history.ProductBrowseHistoryService;
import com.ndyaya.module.product.service.spu.ProductSpuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

import static com.ndyaya.framework.common.pojo.CommonResult.success;
import static com.ndyaya.framework.common.util.collection.CollectionUtils.convertSet;

@Tag(name = "管理后台 - 商品浏览记录")
@RestController
@RequestMapping("/product/browse-history")
@Validated
public class ProductBrowseHistoryController {

    @Resource
    private ProductBrowseHistoryService browseHistoryService;
    @Resource
    private ProductSpuService productSpuService;

    @GetMapping("/page")
    @Operation(summary = "获得商品浏览记录分页")
    @PreAuthorize("@ss.hasPermission('product:browse-history:query')")
    public CommonResult<PageResult<ProductBrowseHistoryRespVO>> getBrowseHistoryPage(@Valid ProductBrowseHistoryPageReqVO pageReqVO) {
        PageResult<ProductBrowseHistoryDO> pageResult = browseHistoryService.getBrowseHistoryPage(pageReqVO);
        if (CollUtil.isEmpty(pageResult.getList())) {
            return success(PageResult.empty());
        }

        // 得到商品 spu 信息
        Map<Long, ProductSpuDO> spuMap = productSpuService.getSpuMap(
                convertSet(pageResult.getList(), ProductBrowseHistoryDO::getSpuId));
        return success(BeanUtils.toBean(pageResult, ProductBrowseHistoryRespVO.class,
                vo -> Optional.ofNullable(spuMap.get(vo.getSpuId()))
                        .ifPresent(spu -> vo.setSpuName(spu.getName()).setPicUrl(spu.getPicUrl()).setPrice(spu.getPrice())
                                .setSalesCount(spu.getSalesCount()).setStock(spu.getStock()))));
    }

}