package com.pay.merchant.app.controller;

import com.pay.merchant.api.MerchantService;
import com.pay.merchant.api.dto.MerchantDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value="商户平台应用接口",tags = "商户平台应用接口",description = "商户平台应用接口")
public class MerchantController {

    @org.apache.dubbo.config.annotation.Reference  //注入的远程调用的接口
    MerchantService merchantService;

    @GetMapping("/merchants/{id}")
    @ApiOperation(value="根据id查询商户信息")
    @ApiResponses({ @ApiResponse(code = 200, message = "请求成功", response = MerchantDto.class), @ApiResponse(code = 404, message = "商户不存在") })
    public MerchantDto queryMerchantById(@PathVariable("id") Long id){

        MerchantDto merchantDTO = merchantService.queryMerchantById(id);
        return merchantDTO;
    }
}
