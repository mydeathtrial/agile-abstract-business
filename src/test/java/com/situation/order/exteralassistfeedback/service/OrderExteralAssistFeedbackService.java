package com.situation.order.exteralassistfeedback.service;

import cloud.agileframework.abstractbusiness.service.AllBusinessService;
import cloud.agileframework.mvc.annotation.AgileService;
import cloud.agileframework.mvc.annotation.Mapping;
import com.situation.order.exteralassistfeedback.pojo.db.OrderExteralAssistFeedbackDo;
import com.situation.order.exteralassistfeedback.pojo.vo.OrderExteralAssistFeedbackInVo;
import com.situation.order.exteralassistfeedback.pojo.vo.OrderExteralAssistFeedbackOutVo;

/**
 * 描述：预警反馈表控制器
 *
 * @author agile generator
 */


@AgileService
@Mapping("/api/order/orderExteralAssistFeedback")
public class OrderExteralAssistFeedbackService implements AllBusinessService<OrderExteralAssistFeedbackDo, OrderExteralAssistFeedbackInVo, OrderExteralAssistFeedbackOutVo> {

}
