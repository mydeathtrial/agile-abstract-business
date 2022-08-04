package com.situation.order.exteralassist.service;

import cloud.agileframework.abstractbusiness.service.AllBusinessService;
import cloud.agileframework.mvc.annotation.AgileService;
import cloud.agileframework.mvc.annotation.Mapping;
import com.situation.order.exteralassist.pojo.db.OrderExteralAssistDo;
import com.situation.order.exteralassist.pojo.vo.OrderExteralAssistInVo;
import com.situation.order.exteralassist.pojo.vo.OrderExteralAssistOutVo;

/**
 * 描述：外部协办任务控制器
 *
 * @author agile generator
 */


@AgileService
@Mapping("/api/order/orderExteralAssist")
public class OrderExteralAssistService implements AllBusinessService<OrderExteralAssistDo, OrderExteralAssistInVo, OrderExteralAssistOutVo> {

}
