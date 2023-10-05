package com.pay.merchant.server.service;

import com.pay.merchant.api.MerchantService;
import com.pay.merchant.api.dto.MerchantDto;
import com.pay.merchant.server.entity.Merchant;
import com.pay.merchant.server.mapper.MerchantMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class MerchantServiceImpl implements MerchantService {

    @Autowired
    MerchantMapper merchantMapper;
    @Override
    public MerchantDto queryMerchantById(Long id) {
        Merchant merchant = merchantMapper.selectById(id);
        MerchantDto merchantDTO = new MerchantDto();
        merchantDTO.setId(merchant.getId());
        merchantDTO.setMerchantName(merchant.getMerchantName());
        return merchantDTO;
    }
}
