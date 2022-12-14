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

package io.github.pnoker.center.manager.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.pnoker.center.manager.service.EventService;
import io.github.pnoker.common.bean.Pages;
import io.github.pnoker.common.dto.DeviceEventDto;
import io.github.pnoker.common.dto.DriverEventDto;
import io.github.pnoker.common.model.DeviceEvent;
import io.github.pnoker.common.model.DriverEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author pnoker
 */
@Slf4j
@Service
public class EventServiceImpl implements EventService {

    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public void addDriverEvent(DriverEvent driverEvent) {
        if (ObjectUtil.isNotNull(driverEvent)) {
            mongoTemplate.insert(driverEvent);
        }
    }

    @Override
    public void addDriverEvents(List<DriverEvent> driverEvents) {
        if (null != driverEvents) {
            if (driverEvents.size() > 0) {
                mongoTemplate.insert(driverEvents, DriverEvent.class);
            }
        }
    }

    @Override
    public void addDeviceEvent(DeviceEvent deviceEvent) {
        if (ObjectUtil.isNotNull(deviceEvent)) {
            mongoTemplate.insert(deviceEvent);
        }
    }

    @Override
    public void addDeviceEvents(List<DeviceEvent> deviceEvents) {
        if (null != deviceEvents) {
            if (deviceEvents.size() > 0) {
                mongoTemplate.insert(deviceEvents, DeviceEvent.class);
            }
        }
    }

    @Override
    public Page<DriverEvent> driverEvent(DriverEventDto driverEventDto) {
        return null;
    }

    @Override
    public Page<DeviceEvent> deviceEvent(DeviceEventDto deviceEventDto) {
        Criteria criteria = new Criteria();
        if (null == deviceEventDto) {
            deviceEventDto = new DeviceEventDto();
        }
        if (StrUtil.isNotEmpty(deviceEventDto.getDeviceId())) {
            criteria.and("deviceId").is(deviceEventDto.getDeviceId());
        }
        if (StrUtil.isNotEmpty(deviceEventDto.getPointId())) {
            criteria.and("pointId").is(deviceEventDto.getPointId());
        }

        Pages pages = null == deviceEventDto.getPage() ? new Pages() : deviceEventDto.getPage();
        if (pages.getStartTime() > 0 && pages.getEndTime() > 0 && pages.getStartTime() <= pages.getEndTime()) {
            criteria.and("createTime").gte(pages.getStartTime()).lte(pages.getEndTime());
        }

        Query query = new Query(criteria);
        long count = mongoTemplate.count(query, DeviceEvent.class);

        query.with(Sort.by(Sort.Direction.DESC, "createTime"));
        long size = pages.getSize();
        long page = pages.getCurrent();
        query.limit((int) size).skip(size * (page - 1));

        List<DeviceEvent> deviceEvents = mongoTemplate.find(query, DeviceEvent.class);

        return (new Page<DeviceEvent>()).setCurrent(pages.getCurrent()).setSize(pages.getSize()).setTotal(count).setRecords(deviceEvents);
    }

}
