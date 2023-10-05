package com.pay.merchant.app.convert;

import com.pay.merchant.api.dto.MerchantDto;
import com.pay.merchant.app.vo.MerchantRegisterVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 将商户注册vo和dto进行转换
 * Created by Administrator.
 */
@Mapper
public interface MerchantRegisterConvert {

    MerchantRegisterConvert INSTANCE = Mappers.getMapper(MerchantRegisterConvert.class);

    //将dto转成vo
    MerchantRegisterVO dto2vo(MerchantDto merchantDTO);
    //将vo转成dto
    MerchantDto vo2dto(MerchantRegisterVO merchantRegisterVO);

}
