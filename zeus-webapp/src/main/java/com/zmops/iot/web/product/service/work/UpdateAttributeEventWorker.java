package com.zmops.iot.web.product.service.work;


import com.zmops.iot.domain.product.ProductAttributeEvent;
import com.zmops.iot.domain.product.query.QProductAttributeEvent;
import com.zmops.iot.web.product.dto.ProductAttr;
import com.zmops.zeus.server.async.callback.IWorker;
import com.zmops.zeus.server.async.wrapper.WorkerWrapper;
import io.ebean.DB;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author yefei
 * <p>
 * 产品属性修改 同步到设备 步骤
 */
@Slf4j
@Component
public class UpdateAttributeEventWorker implements IWorker<ProductAttr, Boolean> {


    @Override
    public Boolean action(ProductAttr productAttr, Map<String, WorkerWrapper> map) {
        log.debug("UpdateAttributeEventWorker…………");

        Long attrId = productAttr.getAttrId();

        List<ProductAttributeEvent> list = new QProductAttributeEvent().templateId.eq(attrId).findList();

        for (ProductAttributeEvent productAttributeEvent : list) {
            productAttributeEvent.setName(productAttr.getAttrName());
            productAttributeEvent.setKey(productAttr.getKey());
            productAttributeEvent.setUnits(productAttr.getUnits());
            productAttributeEvent.setValueType(productAttr.getValueType());
        }
        DB.updateAll(list);

        return true;
    }


    @Override
    public Boolean defaultValue() {
        return true;
    }

}
