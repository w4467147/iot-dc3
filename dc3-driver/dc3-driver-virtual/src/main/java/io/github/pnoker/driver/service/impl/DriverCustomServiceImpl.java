/*
 * Copyright 2022 Pnoker All Rights Reserved
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.pnoker.driver.service.impl;

import cn.hutool.core.util.RandomUtil;
import io.github.pnoker.common.bean.driver.AttributeInfo;
import io.github.pnoker.common.constant.CommonConstant;
import io.github.pnoker.common.model.Device;
import io.github.pnoker.common.model.DeviceEvent;
import io.github.pnoker.common.model.Point;
import io.github.pnoker.common.sdk.bean.driver.DriverContext;
import io.github.pnoker.common.sdk.service.DriverCustomService;
import io.github.pnoker.common.sdk.service.DriverService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author pnoker
 */
@Slf4j
@Service
public class DriverCustomServiceImpl implements DriverCustomService {

    @Resource
    private DriverContext driverContext;
    @Resource
    private DriverService driverService;

    @Override
    public void initial() {
    }

    @Override
    public String read(Map<String, AttributeInfo> driverInfo, Map<String, AttributeInfo> pointInfo, Device device, Point point) throws Exception {
        if (point.getType().equals("string")) {
            return RandomUtil.randomString(8);
        }
        if (point.getType().equals("boolean")) {
            return String.valueOf(RandomUtil.randomBoolean());
        }
        return String.valueOf(RandomUtil.randomDouble(100));
    }

    @Override
    public Boolean write(Map<String, AttributeInfo> driverInfo, Map<String, AttributeInfo> pointInfo, Device device, AttributeInfo value) throws Exception {
        return false;
    }

    @Override
    public void schedule() {

        /*
        TODO:????????????
        ???????????????????????????????????????????????????????????????schedule()??????????????????????????????read?????????????????????????????????
        ?????????????????????????????????????????????????????????????????????driverService.deviceEventSender???????????????????????????SDK?????????

        ???????????????DeviceStatus????????????
        ONLINE:??????
        OFFLINE:??????
        MAINTAIN:??????
        FAULT:??????
         */
        driverContext.getDriverMetadata().getDeviceMap().keySet().forEach(id -> driverService.deviceEventSender(new DeviceEvent(id, CommonConstant.Device.Event.HEARTBEAT, CommonConstant.Status.ONLINE, 25, TimeUnit.SECONDS)));
    }

}
