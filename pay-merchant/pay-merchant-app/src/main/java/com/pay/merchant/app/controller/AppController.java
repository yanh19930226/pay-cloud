package com.pay.merchant.app.controller;

import com.pay.merchant.api.AppService;
import com.pay.merchant.api.dto.AppDTO;
import com.pay.merchant.app.common.util.SecurityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "商户平台-应用管理", tags = "商户平台-应用相关", description = "商户平台-应用相关")
@RestController
public class AppController {

    @Reference
    AppService appService;

    @ApiOperation("商户创建应用")
    @ApiImplicitParams({
    @ApiImplicitParam(name = "app", value = "应用信息", required = true, dataType = "AppDTO", paramType = "body")})
    @PostMapping(value = "/my/apps")
    public AppDTO createApp(@RequestBody AppDTO app){
        //得到商户id
        Long merchantId = SecurityUtil.getMerchantId();

        return  appService.createApp(merchantId,app);
    }
}
