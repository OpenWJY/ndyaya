package com.ndyaya.module.report.convert.goview;

import com.ndyaya.framework.common.pojo.PageResult;
import com.ndyaya.module.report.controller.admin.goview.vo.project.GoViewProjectCreateReqVO;
import com.ndyaya.module.report.controller.admin.goview.vo.project.GoViewProjectRespVO;
import com.ndyaya.module.report.controller.admin.goview.vo.project.GoViewProjectUpdateReqVO;
import com.ndyaya.module.report.dal.dataobject.goview.GoViewProjectDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GoViewProjectConvert {

    GoViewProjectConvert INSTANCE = Mappers.getMapper(GoViewProjectConvert.class);

    GoViewProjectDO convert(GoViewProjectCreateReqVO bean);

    GoViewProjectDO convert(GoViewProjectUpdateReqVO bean);

    GoViewProjectRespVO convert(GoViewProjectDO bean);

    PageResult<GoViewProjectRespVO> convertPage(PageResult<GoViewProjectDO> page);

}
