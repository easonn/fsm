package com.esaon.fsm.order;

/**
 * @version 1.0
 * @author: eason
 * @descpription: 订单状态
 * @date: 2019/10/20 00:37
 **/
public enum OrderStateEnum {

    PRE_PAY(1, "待付款"),
    WAIT_DELIVER(2, "待发货"),

    REFUNDING(3,"退款中"),
    REFUND_FAIL(4,"退款失败"),
    REFUND_SUCCESS(5,"退款成功"),

    DELIVER_CONFIRM(6, "网仓系统确认，准备发货"),
    DELIVERED(7, "已发货"),
    REJECTING(8,"退货中"),
    REJECT_FAIL(9,"退货失败"),
    REJECT_SUCCESS(10,"退货成功"),

    FINISHED(11,"已完成"),

    CLOSED(12,"已关闭"),

    ;

    private int code;

    private String desc;

    OrderStateEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 传入的code是否包含在枚举中
     * @param code
     * @return
     */
    public static boolean contains(Integer code) {
        if (code == null) {
            return false;
        }

        OrderStateEnum[] values = OrderStateEnum.values();
        for (OrderStateEnum value : values) {
            if (value.getCode() == code) {
                return true;
            }
        }
        return false;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
