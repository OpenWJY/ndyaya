package com.ndyaya.module.erp.controller.admin.purchase;

import com.ndyaya.framework.apilog.core.annotation.ApiAccessLog;
import com.ndyaya.framework.common.enums.CommonStatusEnum;
import com.ndyaya.framework.common.pojo.CommonResult;
import com.ndyaya.framework.common.pojo.PageParam;
import com.ndyaya.framework.common.pojo.PageResult;
import com.ndyaya.framework.common.util.object.BeanUtils;
import com.ndyaya.framework.excel.core.util.ExcelUtils;
import com.ndyaya.module.erp.controller.admin.purchase.vo.supplier.ErpSupplierPageReqVO;
import com.ndyaya.module.erp.controller.admin.purchase.vo.supplier.ErpSupplierRespVO;
import com.ndyaya.module.erp.controller.admin.purchase.vo.supplier.ErpSupplierSaveReqVO;
import com.ndyaya.module.erp.dal.dataobject.purchase.ErpSupplierDO;
import com.ndyaya.module.erp.service.purchase.ErpSupplierService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import static com.ndyaya.framework.apilog.core.enums.OperateTypeEnum.EXPORT;
import static com.ndyaya.framework.common.pojo.CommonResult.success;
import static com.ndyaya.framework.common.util.collection.CollectionUtils.convertList;

@Tag(name = "管理后台 - ERP 供应商")
@RestController
@RequestMapping("/erp/supplier")
@Validated
public class ErpSupplierController {

    @Resource
    private ErpSupplierService supplierService;

    @PostMapping("/create")
    @Operation(summary = "创建供应商")
    @PreAuthorize("@ss.hasPermission('erp:supplier:create')")
    public CommonResult<Long> createSupplier(@Valid @RequestBody ErpSupplierSaveReqVO createReqVO) {
        return success(supplierService.createSupplier(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新供应商")
    @PreAuthorize("@ss.hasPermission('erp:supplier:update')")
    public CommonResult<Boolean> updateSupplier(@Valid @RequestBody ErpSupplierSaveReqVO updateReqVO) {
        supplierService.updateSupplier(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除供应商")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('erp:supplier:delete')")
    public CommonResult<Boolean> deleteSupplier(@RequestParam("id") Long id) {
        supplierService.deleteSupplier(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得供应商")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('erp:supplier:query')")
    public CommonResult<ErpSupplierRespVO> getSupplier(@RequestParam("id") Long id) {
        ErpSupplierDO supplier = supplierService.getSupplier(id);
        return success(BeanUtils.toBean(supplier, ErpSupplierRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得供应商分页")
    @PreAuthorize("@ss.hasPermission('erp:supplier:query')")
    public CommonResult<PageResult<ErpSupplierRespVO>> getSupplierPage(@Valid ErpSupplierPageReqVO pageReqVO) {
        PageResult<ErpSupplierDO> pageResult = supplierService.getSupplierPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ErpSupplierRespVO.class));
    }

    @GetMapping("/simple-list")
    @Operation(summary = "获得供应商精简列表", description = "只包含被开启的供应商，主要用于前端的下拉选项")
    public CommonResult<List<ErpSupplierRespVO>> getSupplierSimpleList() {
        List<ErpSupplierDO> list = supplierService.getSupplierListByStatus(CommonStatusEnum.ENABLE.getStatus());
        return success(convertList(list, supplier -> new ErpSupplierRespVO().setId(supplier.getId()).setName(supplier.getName())));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出供应商 Excel")
    @PreAuthorize("@ss.hasPermission('erp:supplier:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportSupplierExcel(@Valid ErpSupplierPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ErpSupplierDO> list = supplierService.getSupplierPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "供应商.xls", "数据", ErpSupplierRespVO.class,
                        BeanUtils.toBean(list, ErpSupplierRespVO.class));
    }

}