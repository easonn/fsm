package com.esaon.fsm.order;

/**
 * @version 1.0
 * @author: eason
 * @descpription: 订单处理事件
 * @date: 2019/10/20 00:40
 **/
public enum OrderEventEnum {

    PAY_SUCCESS(1, "付款成功"),
    PAY_FAIL(2, "付款失败"),


    APPLY_REFUND(3,"申请退款"),
    REFUNDING(4,"执行退款"),
    REFUND_MONEY_CHECK(5,"退款-核对退款金额"),
    REFUND_SKU_CHECK(6,"退款-核对退款SKU"),

    NOTIFY_WMS(7, "通知网仓系统发货"),
    WMS_CANCEL(8,"网仓系统撤单"),

    WMS_DELIVER(9,"网仓系统发货"),

    DELIVER_TIMEOUT(10, "发货超15天"),
    CUSTOMER_CONFIRM(11, "顾客确认收货"),

    APPLY_REJECT(12, "申请退货"),
    REJECTING(13,"执行退货"),

    REJECT_MONEY_CHECK(14,"退货-核对退款金额"),

    REJECT_SKU_CHECK(15,"退货-核对退款SKU"),
    ;

    private int code;

    private String desc;

    OrderEventEnum(int code, String desc) {
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

        OrderEventEnum[] values = OrderEventEnum.values();
        for (OrderEventEnum value : values) {
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
