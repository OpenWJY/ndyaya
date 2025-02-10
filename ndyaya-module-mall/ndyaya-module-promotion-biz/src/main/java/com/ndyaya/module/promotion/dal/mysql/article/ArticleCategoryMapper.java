package com.ndyaya.module.promotion.dal.mysql.article;

import com.ndyaya.framework.common.pojo.PageResult;
import com.ndyaya.framework.mybatis.core.mapper.BaseMapperX;
import com.ndyaya.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.ndyaya.module.promotion.controller.admin.article.vo.category.ArticleCategoryPageReqVO;
import com.ndyaya.module.promotion.dal.dataobject.article.ArticleCategoryDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 文章分类 Mapper
 *
 * @author HUIHUI
 */
@Mapper
public interface ArticleCategoryMapper extends BaseMapperX<ArticleCategoryDO> {

    default PageResult<ArticleCategoryDO> selectPage(ArticleCategoryPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ArticleCategoryDO>()
                .likeIfPresent(ArticleCategoryDO::getName, reqVO.getName())
                .eqIfPresent(ArticleCategoryDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(ArticleCategoryDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ArticleCategoryDO::getSort));
    }

    default List<ArticleCategoryDO> selectListByStatus(Integer status) {
        return selectList(ArticleCategoryDO::getStatus, status);
    }

}
