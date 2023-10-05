package com.pay.merchant.api;

import com.pay.merchant.api.dto.MerchantDto;

/**
 * Created by Administrator.
 */
public interface MerchantService {

    //根据 id查询商户
    public MerchantDto queryMerchantById(Long id);
}
