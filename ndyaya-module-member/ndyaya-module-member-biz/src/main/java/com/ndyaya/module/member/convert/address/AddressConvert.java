package com.ndyaya.module.member.convert.address;

import com.ndyaya.framework.ip.core.utils.AreaUtils;
import com.ndyaya.module.member.api.address.dto.MemberAddressRespDTO;
import com.ndyaya.module.member.controller.admin.address.vo.AddressRespVO;
import com.ndyaya.module.member.controller.app.address.vo.AppAddressCreateReqVO;
import com.ndyaya.module.member.controller.app.address.vo.AppAddressRespVO;
import com.ndyaya.module.member.controller.app.address.vo.AppAddressUpdateReqVO;
import com.ndyaya.module.member.dal.dataobject.address.MemberAddressDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 用户收件地址 Convert
 *
 * @author 芋道源码
 */
@Mapper
public interface AddressConvert {

    AddressConvert INSTANCE = Mappers.getMapper(AddressConvert.class);

    MemberAddressDO convert(AppAddressCreateReqVO bean);

    MemberAddressDO convert(AppAddressUpdateReqVO bean);

    @Mapping(source = "areaId", target = "areaName",  qualifiedByName = "convertAreaIdToAreaName")
    AppAddressRespVO convert(MemberAddressDO bean);

    List<AppAddressRespVO> convertList(List<MemberAddressDO> list);

    MemberAddressRespDTO convert02(MemberAddressDO bean);

    @Named("convertAreaIdToAreaName")
    default String convertAreaIdToAreaName(Integer areaId) {
        return AreaUtils.format(areaId);
    }

    List<AddressRespVO> convertList2(List<MemberAddressDO> list);

}
