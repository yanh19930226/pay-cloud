package com.pay.merchant.app.controller;

import com.example.common.domain.BusinessException;
import com.example.common.domain.CommonErrorCode;
import com.example.common.util.PhoneUtil;
import com.pay.merchant.api.MerchantService;
import com.pay.merchant.api.dto.MerchantDto;
import com.pay.merchant.app.service.SmsService;
import com.pay.merchant.app.vo.MerchantRegisterVO;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value="商户平台应用接口",tags = "商户平台应用接口",description = "商户平台应用接口")
public class MerchantController {

    @org.apache.dubbo.config.annotation.Reference  //注入的远程调用的接口
    MerchantService merchantService;
    @Autowired //注入本地的bean
    SmsService smsService;


    @GetMapping("/merchants/{id}")
    @ApiOperation(value="根据id查询商户信息")
    @ApiResponses({ @ApiResponse(code = 200, message = "请求成功", response = MerchantDto.class), @ApiResponse(code = 404, message = "商户不存在") })
    public MerchantDto queryMerchantById(@PathVariable("id") Long id){

        MerchantDto merchantDTO = merchantService.queryMerchantById(id);
        return merchantDTO;
    }

    @ApiOperation("获取手机验证码")
    @GetMapping("/sms")
    @ApiImplicitParam(value = "手机号",name = "phone",required = true,dataType = "string",paramType = "query")
    public String getSMSCode(@RequestParam("phone") String phone){
        //向验证码服务请求发送验证码
        return smsService.sendMsg(phone);
    }

    @ApiOperation("商户注册")
    @ApiImplicitParam(value = "商户注册信息",name = "merchantRegisterVO",required = true,dataType = "MerchantRegisterVO",paramType = "body")
    @PostMapping("/merchants/register")
    public MerchantRegisterVO registerMerchant(@RequestBody MerchantRegisterVO merchantRegisterVO){

        //校验参数的合法性
        if(merchantRegisterVO == null){
            throw new BusinessException(CommonErrorCode.E_100108);
        }
        if(StringUtils.isBlank(merchantRegisterVO.getMobile())){
            throw new BusinessException(CommonErrorCode.E_100112);
        }
        //手机号格式校验
        if(!PhoneUtil.isMatches(merchantRegisterVO.getMobile())){
            throw new BusinessException(CommonErrorCode.E_100109);
        }

        //校验验证码
        smsService.checkVerifiyCode(merchantRegisterVO.getVerifiykey(),merchantRegisterVO.getVerifiyCode());

        return merchantRegisterVO;
    }

}
