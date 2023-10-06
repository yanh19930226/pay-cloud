package com.pay.merchant.server.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.common.domain.BusinessException;
import com.example.common.domain.CommonErrorCode;
import com.example.common.util.PhoneUtil;
import com.pay.merchant.api.MerchantService;
import com.pay.merchant.api.dto.MerchantDto;
import com.pay.merchant.server.convert.MerchantConvert;
import com.pay.merchant.server.entity.Merchant;
import com.pay.merchant.server.mapper.MerchantMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
@org.apache.dubbo.config.annotation.Service
@Slf4j
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

        //校验参数的合法性
        if(merchantDTO == null){
            throw new BusinessException(CommonErrorCode.E_100108);
        }
        if(StringUtils.isBlank(merchantDTO.getMobile())){
            throw new BusinessException(CommonErrorCode.E_100112);
        }
        if(StringUtils.isBlank(merchantDTO.getPassword())){
            throw new BusinessException(CommonErrorCode.E_100111);
        }
        //手机号格式校验
        if(!PhoneUtil.isMatches(merchantDTO.getMobile())){
            throw new BusinessException(CommonErrorCode.E_100109);
        }
        //校验手机号的唯一性
        //根据手机号查询商户表，如果存在记录则说明手机号已存在
        Integer count = merchantMapper.selectCount(new LambdaQueryWrapper<Merchant>().eq(Merchant::getMobile, merchantDTO.getMobile()));
        if(count>0){
            throw new BusinessException(CommonErrorCode.E_100113);
        }

        Merchant merchant = MerchantConvert.INSTANCE.dto2entity(merchantDTO);
        //审核状态为0-未进行资质申请
        merchant.setAuditStatus("0");
        //调用mapper向数据库写入记录
        merchantMapper.insert(merchant);
        //将entity转成dto
        return MerchantConvert.INSTANCE.entity2dto(merchant);
    }

    @Override
    public void applyMerchant(Long merchantId, MerchantDto merchantDTO) throws BusinessException {
        if(merchantId == null || merchantDTO == null){
            throw new BusinessException(CommonErrorCode.E_300009);
        }
        //校验merchantId合法性，查询商户表，如果查询不到记录，认为非法
        Merchant merchant = merchantMapper.selectById(merchantId);
        if(merchant == null){
            throw new BusinessException(CommonErrorCode.E_200002);
        }
        //将dto转成entity
        Merchant entity = MerchantConvert.INSTANCE.dto2entity(merchantDTO);
        //将必要的参数设置到entity
        entity.setId(merchant.getId());
        entity.setMobile(merchant.getMobile());//因为资质申请的时候手机号不让改，还使用数据库中原来的手机号
        entity.setAuditStatus("1");//审核状态1-已申请待审核
        entity.setTenantId(merchant.getTenantId());
        //调用mapper更新商户表
        merchantMapper.updateById(entity);
    }
}
