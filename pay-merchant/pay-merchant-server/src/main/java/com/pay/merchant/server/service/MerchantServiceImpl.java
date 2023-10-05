package com.pay.merchant.server.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.common.domain.BusinessException;
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

    /**
     * 注册商户服务接口，接收账号、密码、手机号，为了可扩展性使用merchantDto接收数据
     * 调用SaaS接口：新增租户、用户、绑定租户和用户的关系，初始化权限
     * @param merchantDTO 商户注册信息
     * @return 注册成功的商户信息
     */
    @Override
    public MerchantDto createMerchant(MerchantDto merchantDTO) throws BusinessException {

        Merchant merchant = new Merchant();
        merchant.setMobile(merchantDTO.getMobile());

        //审核状态为0-未进行资质申请
        merchant.setAuditStatus("0");
        //调用mapper向数据库写入记录
        merchantMapper.insert(merchant);
        return merchantDTO;
    }
}
