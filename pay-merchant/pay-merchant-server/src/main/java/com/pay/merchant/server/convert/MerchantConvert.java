package com.pay.merchant.server.convert;

import com.pay.merchant.api.dto.MerchantDto;
import com.pay.merchant.server.entity.Merchant;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import java.util.List;

/**
 * 定义dto和entity之间的转换规则
 * Created by Administrator.
 */
@Mapper //对象属性的映射
public interface MerchantConvert {

    //转换类实例
    MerchantConvert INSTANCE = Mappers.getMapper(MerchantConvert.class);

    //把dto转换成entity
    Merchant dto2entity(MerchantDto merchantDTO);

    //把entity转换成dto
    MerchantDto entity2dto(Merchant merchant);

    //list之间也可以转换，很entity的List转成MerchantDTO list
    List<MerchantDto> entityList2dtoList(List<Merchant> merchants);
}
