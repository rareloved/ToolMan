package com.andy.test.initsql;

/**
 * <p>DESCRIPTION:  类描述
 * <p>CALLED BY:   zhangshouzheng
 * <p>UPDATE BY:   zhangshouzheng
 * <p>CREATE DATE: 2017/3/2
 * <p>UPDATE DATE: 2017/3/2
 *
 * @version 1.0
 * @since java 1.7.0
 */
public enum OrderPropertiesEnum {
    /**
     * 续投订单
     */
    CONTINUE_ORDER(11),

    /**
     * 机构订单
     */
    OrderPropertiesEnum(12);


    private int properties;

    OrderPropertiesEnum(int properties) {
        this.properties = properties;
    }

    public int getProperties() {
        return properties;
    }

    public long getValue() {
        //计算该标位对应标值
        int off = (1 << (this.properties - 1));
        return off;
    }

    /**
     * 去标
     *
     * @param property 标值
     * @return 去标之后的标值
     */
    public long wipe(long property) {
        long result = property & ~this.getValue();
        return result;
    }

    /**
     * 打标
     *
     * @param property 标值
     * @return 打标之后的标值
     */
    public long mark(long property) {
        long result = property | this.getValue();
        return result;
    }

    /**
     * 传入一个标值，验证该标值对应的标位是否已经打标
     *
     * @param property 标值
     * @return 是否打标
     */
    public boolean vaild(long property) {
        int off = (1 << (this.properties - 1));
        return (off & property) == off;
    }

//    /**
//     * 传入一个标值，传入一个0,1标识枚举，验证该标值对应的标位和传入标识枚举值是否相等
//     *
//     * @param property 标值
//     * @param zoe      0,1枚举
//     * @return 是否相等
//     */
//    public boolean vaild(long property, ZeroOneEnum zoe) {
//        long off = property >> (this.properties - 1) & ZeroOneEnum.ONE.getValue();
//        return off == zoe.getValue();
//    }

    /**
     * 根据订单标对应int值查询订单标枚举
     * @param value 标值
     * @return 订单标枚举
     */
    public static OrderPropertiesEnum getOrderProperties(int value) {
        OrderPropertiesEnum[] orderPropertiesEnums = OrderPropertiesEnum.values();
        for (OrderPropertiesEnum orderPropertiesEnum : orderPropertiesEnums) {
            if (value == orderPropertiesEnum.getProperties()) {
                return orderPropertiesEnum;
            }
        }
        throw new IllegalArgumentException("传入订单标值不存在");
    }

}
