package com.ndyaya.module.product.dal.mysql.favorite;

import com.ndyaya.framework.common.pojo.PageResult;
import com.ndyaya.framework.mybatis.core.mapper.BaseMapperX;
import com.ndyaya.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.ndyaya.module.product.controller.admin.favorite.vo.ProductFavoritePageReqVO;
import com.ndyaya.module.product.controller.app.favorite.vo.AppFavoritePageReqVO;
import com.ndyaya.module.product.dal.dataobject.favorite.ProductFavoriteDO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductFavoriteMapper extends BaseMapperX<ProductFavoriteDO> {

    default ProductFavoriteDO selectByUserIdAndSpuId(Long userId, Long spuId) {
        return selectOne(ProductFavoriteDO::getUserId, userId,
                ProductFavoriteDO::getSpuId, spuId);
    }

    default PageResult<ProductFavoriteDO> selectPageByUserAndType(Long userId, AppFavoritePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapper<ProductFavoriteDO>()
                .eq(ProductFavoriteDO::getUserId, userId)
                .orderByDesc(ProductFavoriteDO::getId));
    }

    default PageResult<ProductFavoriteDO> selectPageByUserId(ProductFavoritePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ProductFavoriteDO>()
                .eqIfPresent(ProductFavoriteDO::getUserId, reqVO.getUserId())
                .orderByDesc(ProductFavoriteDO::getId));
    }

    default Long selectCountByUserId(Long userId) {
        return selectCount(ProductFavoriteDO::getUserId, userId);
    }

}
