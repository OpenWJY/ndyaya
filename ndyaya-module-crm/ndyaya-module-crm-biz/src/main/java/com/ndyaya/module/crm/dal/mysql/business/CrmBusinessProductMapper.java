package com.ndyaya.module.crm.dal.mysql.business;


import com.ndyaya.framework.mybatis.core.mapper.BaseMapperX;
import com.ndyaya.module.crm.dal.dataobject.business.CrmBusinessProductDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 商机产品 Mapper
 *
 * @author lzxhqs
 */
@Mapper
public interface CrmBusinessProductMapper extends BaseMapperX<CrmBusinessProductDO> {

    default List<CrmBusinessProductDO> selectListByBusinessId(Long businessId) {
        return selectList(CrmBusinessProductDO::getBusinessId, businessId);
    }

}
