package com.ndyaya.module.iot.dal.mysql.product;

import com.ndyaya.framework.common.pojo.PageResult;
import com.ndyaya.framework.mybatis.core.mapper.BaseMapperX;
import com.ndyaya.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.ndyaya.module.iot.controller.admin.product.vo.IotProductPageReqVO;
import com.ndyaya.module.iot.dal.dataobject.product.IotProductDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * IoT 产品 Mapper
 *
 * @author ahh
 */
@Mapper
public interface IotProductMapper extends BaseMapperX<IotProductDO> {

    default PageResult<IotProductDO> selectPage(IotProductPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<IotProductDO>()
                .likeIfPresent(IotProductDO::getName, reqVO.getName())
                .likeIfPresent(IotProductDO::getProductKey, reqVO.getProductKey())
                .orderByDesc(IotProductDO::getId));
    }

    default IotProductDO selectByProductKey(String productKey) {
        return selectOne(new LambdaQueryWrapperX<IotProductDO>().eq(IotProductDO::getProductKey, productKey));
    }

}