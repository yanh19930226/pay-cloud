package com.pay.merchant.app.convert;

import com.pay.merchant.api.dto.MerchantDto;
import com.pay.merchant.app.vo.MerchantDetailVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 将商户资质申请vo和dto进行转换
 * Created by Administrator.
 */
@Mapper
public interface MerchantDetailConvert {

    MerchantDetailConvert INSTANCE = Mappers.getMapper(MerchantDetailConvert.class);

    //将dto转成vo
    MerchantDetailVO dto2vo(MerchantDto merchantDTO);
    //将vo转成dto
    MerchantDto vo2dto(MerchantDetailVO merchantDetailVO);

}
