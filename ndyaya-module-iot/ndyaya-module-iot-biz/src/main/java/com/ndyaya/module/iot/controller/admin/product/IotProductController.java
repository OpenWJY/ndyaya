package com.ndyaya.module.iot.controller.admin.product;

import com.ndyaya.framework.common.pojo.CommonResult;
import com.ndyaya.framework.common.pojo.PageResult;
import com.ndyaya.framework.common.util.object.BeanUtils;
import com.ndyaya.module.iot.controller.admin.product.vo.IotProductPageReqVO;
import com.ndyaya.module.iot.controller.admin.product.vo.IotProductRespVO;
import com.ndyaya.module.iot.controller.admin.product.vo.IotProductSaveReqVO;
import com.ndyaya.module.iot.controller.admin.product.vo.IotProductSimpleRespVO;
import com.ndyaya.module.iot.dal.dataobject.product.IotProductDO;
import com.ndyaya.module.iot.service.product.IotProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ndyaya.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - IoT 产品")
@RestController
@RequestMapping("/iot/product")
@Validated
public class IotProductController {

    @Resource
    private IotProductService productService;

    @PostMapping("/create")
    @Operation(summary = "创建产品")
    @PreAuthorize("@ss.hasPermission('iot:product:create')")
    public CommonResult<Long> createProduct(@Valid @RequestBody IotProductSaveReqVO createReqVO) {
        return success(productService.createProduct(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新产品")
    @PreAuthorize("@ss.hasPermission('iot:product:update')")
    public CommonResult<Boolean> updateProduct(@Valid @RequestBody IotProductSaveReqVO updateReqVO) {
        productService.updateProduct(updateReqVO);
        return success(true);
    }

    @PutMapping("/update-status")
    @Operation(summary = "更新产品状态")
    @Parameter(name = "id", description = "编号", required = true)
    @Parameter(name = "status", description = "状态", required = true)
    @PreAuthorize("@ss.hasPermission('iot:product:update')")
    public CommonResult<Boolean> updateProductStatus(@RequestParam("id") Long id,
                                                     @RequestParam("status") Integer status) {
        productService.updateProductStatus(id, status);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除产品")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('iot:product:delete')")
    public CommonResult<Boolean> deleteProduct(@RequestParam("id") Long id) {
        productService.deleteProduct(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得产品")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('iot:product:query')")
    public CommonResult<IotProductRespVO> getProduct(@RequestParam("id") Long id) {
        IotProductDO product = productService.getProduct(id);
        return success(BeanUtils.toBean(product, IotProductRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得产品分页")
    @PreAuthorize("@ss.hasPermission('iot:product:query')")
    public CommonResult<PageResult<IotProductRespVO>> getProductPage(@Valid IotProductPageReqVO pageReqVO) {
        PageResult<IotProductDO> pageResult = productService.getProductPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, IotProductRespVO.class));
    }

    // TODO @haohao：改成 simple-list 哈
    @GetMapping("/list-all-simple")
    @Operation(summary = "获得所有产品列表")
    @PreAuthorize("@ss.hasPermission('iot:product:query')")
    public CommonResult<List<IotProductSimpleRespVO>> listAllSimpleProducts() {
        List<IotProductDO> list = productService.getProductList();
        return success(BeanUtils.toBean(list, IotProductSimpleRespVO.class));
    }

}