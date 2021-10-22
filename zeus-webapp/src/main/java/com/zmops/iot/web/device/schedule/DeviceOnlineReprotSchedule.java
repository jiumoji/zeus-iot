package com.zmops.iot.web.device.schedule;

import com.zmops.iot.domain.device.Device;
import com.zmops.iot.domain.device.DeviceOnlineReport;
import com.zmops.iot.domain.device.query.QDevice;
import com.zmops.iot.util.LocalDateTimeUtils;
import io.ebean.DB;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author yefei
 **/
@EnableScheduling
@Component
@Slf4j
public class DeviceOnlineReprotSchedule {

    @Scheduled(cron = "0 59 23 1/1 * ? ")
    public void report() {
        log.info("开始查询设备在线情况");

        //统计出 当前 设备在线情况
        List<Device> deviceList = new QDevice().findList();

        Map<Integer, Long> onLinemap = deviceList.parallelStream()
                .collect(Collectors.groupingBy(o -> Optional.ofNullable(o.getOnline()).orElse(0), Collectors.counting()));

        DeviceOnlineReport deviceOnlineReport = DeviceOnlineReport.builder()
                .createTime(LocalDateTimeUtils.formatTimeDate(LocalDateTime.now()))
                .online(Optional.ofNullable(onLinemap.get(1)).orElse(0L))
                .offline(Optional.ofNullable(onLinemap.get(0)).orElse(0L)).build();
        //插入 在线情况表
        DB.save(deviceOnlineReport);
    }

}
