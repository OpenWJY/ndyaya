package com.ndyaya.module.ai.dal.mysql.knowledge;

import com.ndyaya.framework.common.pojo.PageResult;
import com.ndyaya.framework.mybatis.core.mapper.BaseMapperX;
import com.ndyaya.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.ndyaya.module.ai.controller.admin.knowledge.vo.document.AiKnowledgeDocumentPageReqVO;
import com.ndyaya.module.ai.dal.dataobject.knowledge.AiKnowledgeDocumentDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * AI 知识库-文档 Mapper
 *
 * @author xiaoxin
 */
@Mapper
public interface AiKnowledgeDocumentMapper extends BaseMapperX<AiKnowledgeDocumentDO> {

    default PageResult<AiKnowledgeDocumentDO> selectPage(AiKnowledgeDocumentPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<AiKnowledgeDocumentDO>()
                .likeIfPresent(AiKnowledgeDocumentDO::getName, reqVO.getName())
                .orderByDesc(AiKnowledgeDocumentDO::getId));
    }

}
