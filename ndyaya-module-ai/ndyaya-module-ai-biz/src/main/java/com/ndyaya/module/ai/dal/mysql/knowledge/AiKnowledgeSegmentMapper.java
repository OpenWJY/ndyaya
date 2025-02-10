package com.ndyaya.module.ai.dal.mysql.knowledge;

import com.ndyaya.framework.common.pojo.PageResult;
import com.ndyaya.framework.mybatis.core.mapper.BaseMapperX;
import com.ndyaya.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.ndyaya.module.ai.controller.admin.knowledge.vo.segment.AiKnowledgeSegmentPageReqVO;
import com.ndyaya.module.ai.dal.dataobject.knowledge.AiKnowledgeSegmentDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * AI 知识库-分片 Mapper
 *
 * @author xiaoxin
 */
@Mapper
public interface AiKnowledgeSegmentMapper extends BaseMapperX<AiKnowledgeSegmentDO> {

    default PageResult<AiKnowledgeSegmentDO> selectPage(AiKnowledgeSegmentPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<AiKnowledgeSegmentDO>()
                .eq(AiKnowledgeSegmentDO::getDocumentId, reqVO.getDocumentId())
                .eqIfPresent(AiKnowledgeSegmentDO::getStatus, reqVO.getStatus())
                .likeIfPresent(AiKnowledgeSegmentDO::getContent, reqVO.getKeyword())
                .orderByDesc(AiKnowledgeSegmentDO::getId));
    }

    default List<AiKnowledgeSegmentDO> selectListByVectorIds(List<String> vectorIdList) {
        return selectList(new LambdaQueryWrapperX<AiKnowledgeSegmentDO>()
                .in(AiKnowledgeSegmentDO::getVectorId, vectorIdList)
                .orderByDesc(AiKnowledgeSegmentDO::getId));
    }

}
