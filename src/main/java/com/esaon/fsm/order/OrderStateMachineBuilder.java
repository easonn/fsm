package com.esaon.fsm.order;

import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.guard.Guard;

import java.util.EnumSet;

/**
 * @version 1.0
 * @author: eason
 * @descpription: 订单状态机
 * @date: 2019/10/20 01:25
 **/
@Configuration
@EnableStateMachine
public class OrderStateMachineBuilder
        extends EnumStateMachineConfigurerAdapter<OrderStateEnum, OrderEventEnum> {

    @Override
    public void configure(StateMachineStateConfigurer<OrderStateEnum, OrderEventEnum> states)
            throws Exception {
        states
                .withStates()
                    .initial(OrderStateEnum.PRE_PAY)
                    // 后续状态需要业务逻辑进行判断
                    .choice(OrderStateEnum.REFUNDING)
                    .states(EnumSet.allOf(OrderStateEnum.class));
        }

    @Override
    public void configure(StateMachineTransitionConfigurer<OrderStateEnum, OrderEventEnum> transitions)
            throws Exception {
        transitions
                // 1
                .withExternal()
                    .event(OrderEventEnum.PAY_SUCCESS)
                    .source(OrderStateEnum.PRE_PAY)
                    .target(OrderStateEnum.WAIT_DELIVER)
                    .and()
                // 2
                .withExternal()
                    .event(OrderEventEnum.PAY_FAIL)
                    .source(OrderStateEnum.PRE_PAY)
                    .target(OrderStateEnum.CLOSED)
                    .and()
                // 3
                .withExternal()
                    .event(OrderEventEnum.NOTIFY_WMS)
                    .source(OrderStateEnum.WAIT_DELIVER)
                    .target(OrderStateEnum.DELIVER_CONFIRM)
                    .and()
                // 4
                .withExternal()
                    .event(OrderEventEnum.WMS_DELIVER)
                    .source(OrderStateEnum.DELIVER_CONFIRM)
                    .target(OrderStateEnum.DELIVERED)
                    .and()
                // 5
                .withExternal()
                    .event(OrderEventEnum.CUSTOMER_CONFIRM)
                    .source(OrderStateEnum.DELIVERED)
                    .target(OrderStateEnum.FINISHED)
                    .and()
                // 6
                .withExternal()
                    .event(OrderEventEnum.WMS_CANCEL)
                    .source(OrderStateEnum.DELIVER_CONFIRM)
                    .target(OrderStateEnum.WAIT_DELIVER)
                    .and()
                // 7
                .withExternal()
                    .event(OrderEventEnum.APPLY_REFUND)
                    .source(OrderStateEnum.WAIT_DELIVER)
                    .target(OrderStateEnum.REFUNDING)
                    .and()
                // 8
                .withChoice()
                    .source(OrderStateEnum.REFUNDING)
                    .first(OrderStateEnum.REFUND_SUCCESS,checkOrderReFundState())
                    .last(OrderStateEnum.REFUND_FAIL)
        ;
    }


    public Guard<OrderStateEnum,OrderEventEnum> checkOrderReFundState() {
        return context -> {
            String orderId = (String) context.getMessageHeader(OrderConstants.FSM_CONTEXT_ORDER_KEY);
            System.out.println(OrderConstants.FSM_CONTEXT_ORDER_KEY + ":" + orderId);
            return true;
        };
    }
}
