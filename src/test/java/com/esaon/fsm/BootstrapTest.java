package com.esaon.fsm;

import com.esaon.fsm.Bootstarp;
import com.esaon.fsm.order.OrderConstants;
import com.esaon.fsm.order.OrderEventEnum;
import com.esaon.fsm.order.OrderStateEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @version 1.0
 * @author: eason
 * @descpription: 测试类
 * @date: 2019/10/20 14:20
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Bootstarp.class)
public class BootstrapTest {

    @Autowired
    private StateMachine<OrderStateEnum,OrderEventEnum> stateMachine;

    @Test
    public void fsm() {
        stateMachine.start();
        stateMachine.sendEvent(OrderEventEnum.PAY_SUCCESS);
//        stateMachine.sendEvent(OrderEventEnum.APPLY_REFUND);


        // 传递数据附带到事件中it
        Message<OrderEventEnum> eventMsg
                = MessageBuilder
                .withPayload(OrderEventEnum.APPLY_REFUND)
                .setHeader(OrderConstants.FSM_CONTEXT_ORDER_KEY,"d4b2758da0205c1e0aa9512cd188002a")
                .build();

        stateMachine.sendEvent(eventMsg);
        stateMachine.sendEvent(OrderEventEnum.PAY_FAIL);
        System.out.println(stateMachine.getState().getId());
    }
}
