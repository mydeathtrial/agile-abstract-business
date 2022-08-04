package com.situation.order.exteralassistfeedbackcheck.service;

import cloud.agileframework.abstractbusiness.service.AllBusinessService;
import cloud.agileframework.mvc.annotation.AgileService;
import cloud.agileframework.mvc.annotation.Mapping;
import com.situation.order.exteralassistfeedbackcheck.pojo.db.OrderExteralAssistFeedbackCheckDo;
import com.situation.order.exteralassistfeedbackcheck.pojo.vo.OrderExteralAssistFeedbackCheckInVo;
import com.situation.order.exteralassistfeedbackcheck.pojo.vo.OrderExteralAssistFeedbackCheckOutVo;

/**
 * 描述：审批预警反馈表控制器
 *
 * @author agile generator
 */


@AgileService
@Mapping("/api/order/orderExteralAssistFeedbackCheck")
public class OrderExteralAssistFeedbackCheckService implements AllBusinessService<OrderExteralAssistFeedbackCheckDo, OrderExteralAssistFeedbackCheckInVo, OrderExteralAssistFeedbackCheckOutVo> {

}
