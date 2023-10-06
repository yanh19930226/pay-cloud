package com.pay.merchant.server.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.common.domain.BusinessException;
import com.example.common.domain.CommonErrorCode;
import com.pay.merchant.api.AppService;
import com.pay.merchant.api.dto.AppDTO;
import com.pay.merchant.server.convert.AppCovert;
import com.pay.merchant.server.entity.App;
import com.pay.merchant.server.entity.Merchant;
import com.pay.merchant.server.mapper.AppMapper;
import com.pay.merchant.server.mapper.MerchantMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

@org.apache.dubbo.config.annotation.Service
public class AppServiceImpl implements AppService {

    @Autowired
    AppMapper appMapper;

    @Autowired
    MerchantMapper merchantMapper;
    @Override
    public AppDTO createApp(Long merchantId, AppDTO appDTO) throws BusinessException {
        if(merchantId==null || appDTO== null || StringUtils.isBlank(appDTO.getAppName())){
            throw new BusinessException(CommonErrorCode.E_300009);
        }
        //  1）校验商户是否通过资质审核
        Merchant merchant = merchantMapper.selectById(merchantId);
        if(merchant == null){
            throw new BusinessException(CommonErrorCode.E_200002);
        }
        //取出商户资质申请状态
        String auditStatus = merchant.getAuditStatus();
        if(!"2".equals(auditStatus)){
            throw new BusinessException(CommonErrorCode.E_200003);
        }

        // 应用名称需要校验唯一性
        //传入的应用名称
        String appName = appDTO.getAppName();
        Boolean existAppName = isExistAppName(appName);
        if (existAppName){
            throw new BusinessException(CommonErrorCode.E_200004);
        }

        //2）生成应用ID
        String appId = UUID.randomUUID().toString();

        App entity = AppCovert.INSTANCE.dto2entity(appDTO);
        entity.setAppId(appId);//应用id
        entity.setMerchantId(merchantId);//商户id

        //调用 appMapper向app表插入数据
        appMapper.insert(entity);

        return AppCovert.INSTANCE.entity2dto(entity);
    }

    @Override
    public List<AppDTO> queryAppByMerchant(Long merchantId) throws BusinessException {
        return null;
    }

    @Override
    public AppDTO getAppById(String appId) throws BusinessException {
        return null;
    }

    @Override
    public Boolean queryAppInMerchant(String appId, Long merchantId) {
        return null;
    }

    //判断应用名称是否存在
    private Boolean isExistAppName(String appName){
        Integer count = appMapper.selectCount(new LambdaQueryWrapper<App>().eq(App::getAppName, appName));
        return count >0;
    }
}
