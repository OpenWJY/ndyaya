package com.ndyaya.module.member.dal.mysql.level;

import com.ndyaya.framework.common.pojo.PageResult;
import com.ndyaya.framework.mybatis.core.mapper.BaseMapperX;
import com.ndyaya.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.ndyaya.module.member.controller.admin.level.vo.record.MemberLevelRecordPageReqVO;
import com.ndyaya.module.member.dal.dataobject.level.MemberLevelRecordDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员等级记录 Mapper
 *
 * @author owen
 */
@Mapper
public interface MemberLevelRecordMapper extends BaseMapperX<MemberLevelRecordDO> {

    default PageResult<MemberLevelRecordDO> selectPage(MemberLevelRecordPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<MemberLevelRecordDO>()
                .eqIfPresent(MemberLevelRecordDO::getUserId, reqVO.getUserId())
                .eqIfPresent(MemberLevelRecordDO::getLevelId, reqVO.getLevelId())
                .betweenIfPresent(MemberLevelRecordDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(MemberLevelRecordDO::getId));
    }

}
