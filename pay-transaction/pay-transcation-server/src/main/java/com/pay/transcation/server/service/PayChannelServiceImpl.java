package com.pay.transcation.server.service;

import cn.itcast.sailing.common.cache.Cache;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.common.domain.BusinessException;
import com.pay.transaction.api.PayChannelService;
import com.pay.transaction.api.dto.PlatformChannelDTO;
import com.pay.transcation.server.convert.PlatformChannelConvert;
import com.pay.transcation.server.entity.AppPlatformChannel;
import com.pay.transcation.server.entity.PlatformChannel;
import com.pay.transcation.server.mapper.AppPlatformChannelMapper;
import com.pay.transcation.server.mapper.PayChannelParamMapper;
import com.pay.transcation.server.mapper.PlatformChannelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@org.apache.dubbo.config.annotation.Service
public class PayChannelServiceImpl implements PayChannelService {

    @Autowired
    Cache cache;

    @Autowired
    PlatformChannelMapper platformChannelMapper;

    @Autowired
    AppPlatformChannelMapper appPlatformChannelMapper;

    @Autowired
    PayChannelParamMapper payChannelParamMapper;

    /**
     * 查询平台的服务类型
     *
     * @return
     * @throws BusinessException
     */
    @Override
    public List<PlatformChannelDTO> queryPlatformChannel() throws BusinessException {
        //查询platform_channel表的全部记录
        List<PlatformChannel> platformChannels = platformChannelMapper.selectList(null);
        //将platformChannels转成包含dto的list
        return PlatformChannelConvert.INSTANCE.listentity2listdto(platformChannels);
    }

    /**
     * 为某个应用绑定一个服务类型
     *
     * @param appId                应用id
     * @param platformChannelCodes 服务类型的code
     * @throws BusinessException
     */
    @Override
    @Transactional
    public void bindPlatformChannelForApp(String appId, String platformChannelCodes) throws BusinessException {

        //根据应用id和服务类型code查询 ，如果已经绑定则不再插入，否则插入记录
        AppPlatformChannel appPlatformChannel = appPlatformChannelMapper.selectOne(new LambdaQueryWrapper<AppPlatformChannel>().eq(AppPlatformChannel::getAppId, appId)
                .eq(AppPlatformChannel::getPlatformChannel, platformChannelCodes));
        if(appPlatformChannel == null){
            //向app_platform_channel插入
            AppPlatformChannel entity = new AppPlatformChannel();
            entity.setAppId(appId);//应用id
            entity.setPlatformChannel(platformChannelCodes);//服务类型code
            appPlatformChannelMapper.insert(entity);
        }
    }


    /**
     * 应用绑定服务类型的状态
     *
     * @param appId
     * @param platformChannel
     * @return 已绑定 1,否则0
     * @throws BusinessException
     */
    @Override
    public int queryAppBindPlatformChannel(String appId, String platformChannel) throws BusinessException {
        AppPlatformChannel appPlatformChannel = appPlatformChannelMapper.selectOne(new LambdaQueryWrapper<AppPlatformChannel>().eq(AppPlatformChannel::getAppId, appId)
                .eq(AppPlatformChannel::getPlatformChannel, platformChannel));
        if(appPlatformChannel !=null){
            return 1;
        }
        return 0;
    }
}
