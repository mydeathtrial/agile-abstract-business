package com.situation.order.exteralassistassign.service;

import cloud.agileframework.abstractbusiness.service.AllBusinessService;
import cloud.agileframework.mvc.annotation.AgileService;
import cloud.agileframework.mvc.annotation.Mapping;
import com.situation.order.exteralassistassign.pojo.db.OrderExteralAssistAssignDo;
import com.situation.order.exteralassistassign.pojo.vo.OrderExteralAssistAssignInVo;
import com.situation.order.exteralassistassign.pojo.vo.OrderExteralAssistAssignOutVo;

/**
 * 描述：外部协办任务-处置-中间表控制器
 *
 * @author agile generator
 */


@AgileService
@Mapping("/api/order/orderExteralAssistAssign")
public class OrderExteralAssistAssignService implements AllBusinessService<OrderExteralAssistAssignDo, OrderExteralAssistAssignInVo, OrderExteralAssistAssignOutVo> {

}
