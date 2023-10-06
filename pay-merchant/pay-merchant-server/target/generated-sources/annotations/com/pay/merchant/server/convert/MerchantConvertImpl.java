package com.pay.merchant.server.convert;

import com.pay.merchant.api.dto.MerchantDto;
import com.pay.merchant.server.entity.Merchant;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-10-06T10:52:00+0800",
    comments = "version: 1.3.0.Final, compiler: javac, environment: Java 1.8.0_331 (Oracle Corporation)"
)
public class MerchantConvertImpl implements MerchantConvert {

    @Override
    public Merchant dto2entity(MerchantDto merchantDTO) {
        if ( merchantDTO == null ) {
            return null;
        }

        Merchant merchant = new Merchant();

        merchant.setId( merchantDTO.getId() );
        merchant.setMerchantName( merchantDTO.getMerchantName() );
        if ( merchantDTO.getMerchantNo() != null ) {
            merchant.setMerchantNo( String.valueOf( merchantDTO.getMerchantNo() ) );
        }
        merchant.setMerchantAddress( merchantDTO.getMerchantAddress() );
        merchant.setMerchantType( merchantDTO.getMerchantType() );
        merchant.setBusinessLicensesImg( merchantDTO.getBusinessLicensesImg() );
        merchant.setIdCardFrontImg( merchantDTO.getIdCardFrontImg() );
        merchant.setIdCardAfterImg( merchantDTO.getIdCardAfterImg() );
        merchant.setUsername( merchantDTO.getUsername() );
        merchant.setMobile( merchantDTO.getMobile() );
        merchant.setContactsAddress( merchantDTO.getContactsAddress() );
        merchant.setAuditStatus( merchantDTO.getAuditStatus() );
        merchant.setTenantId( merchantDTO.getTenantId() );

        return merchant;
    }

    @Override
    public MerchantDto entity2dto(Merchant merchant) {
        if ( merchant == null ) {
            return null;
        }

        MerchantDto merchantDto = new MerchantDto();

        merchantDto.setId( merchant.getId() );
        merchantDto.setMerchantName( merchant.getMerchantName() );
        if ( merchant.getMerchantNo() != null ) {
            merchantDto.setMerchantNo( Long.parseLong( merchant.getMerchantNo() ) );
        }
        merchantDto.setMerchantAddress( merchant.getMerchantAddress() );
        merchantDto.setMerchantType( merchant.getMerchantType() );
        merchantDto.setBusinessLicensesImg( merchant.getBusinessLicensesImg() );
        merchantDto.setIdCardFrontImg( merchant.getIdCardFrontImg() );
        merchantDto.setIdCardAfterImg( merchant.getIdCardAfterImg() );
        merchantDto.setUsername( merchant.getUsername() );
        merchantDto.setMobile( merchant.getMobile() );
        merchantDto.setContactsAddress( merchant.getContactsAddress() );
        merchantDto.setAuditStatus( merchant.getAuditStatus() );
        merchantDto.setTenantId( merchant.getTenantId() );

        return merchantDto;
    }

    @Override
    public List<MerchantDto> entityList2dtoList(List<Merchant> merchants) {
        if ( merchants == null ) {
            return null;
        }

        List<MerchantDto> list = new ArrayList<MerchantDto>( merchants.size() );
        for ( Merchant merchant : merchants ) {
            list.add( entity2dto( merchant ) );
        }

        return list;
    }
}
