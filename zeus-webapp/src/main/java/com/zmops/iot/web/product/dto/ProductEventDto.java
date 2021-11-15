package com.zmops.iot.web.product.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zmops.iot.model.cache.filter.CachedValue;
import com.zmops.iot.model.cache.filter.CachedValueFilter;
import com.zmops.iot.model.cache.filter.DicType;
import lombok.Data;

/**
 * @author yefei
 **/
@Data
@JsonSerialize(using = CachedValueFilter.class)
public class ProductEventDto {

    private Long eventRuleId;

    private Byte eventNotify;

    private String eventRuleName;

    @CachedValue(value = "EVENT_LEVEL")
    private String eventLevel;

    @CachedValue(value = "STATUS")
    private String status;

    @CachedValue(value = "WHETHER")
    private String inherit;

    private String remark;
    private String classify;
    private String expLogic;
    @CachedValue(type = DicType.SysUserName)
    private Long createUser;
    private String createTime;
    @CachedValue(type = DicType.SysUserName)
    private Long updateUser;
    private String updateTime;
    private Integer taskId;
}
