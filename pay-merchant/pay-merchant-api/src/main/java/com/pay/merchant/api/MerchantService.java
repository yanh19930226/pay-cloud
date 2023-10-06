package com.pay.merchant.api;

import com.example.common.domain.BusinessException;
import com.pay.merchant.api.dto.MerchantDto;

/**
 * Created by Administrator.
 */
public interface MerchantService {

    //根据 id查询商户
    public MerchantDto queryMerchantById(Long id);

    /**
     *  注册商户服务接口，接收账号、密码、手机号，为了可扩展性使用merchantDto接收数据
     * @param merchantDTO 商户注册信息
     * @return 注册成功的商户信息
     */
    MerchantDto createMerchant(MerchantDto merchantDTO) throws BusinessException;

    /**
     * 资质申请接口
     * @param merchantId 商户id
     * @param merchantDTO 资质申请的信息
     * @throws BusinessException
     */
    void applyMerchant(Long merchantId,MerchantDto merchantDTO) throws BusinessException;
}
