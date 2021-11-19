package com.harry.boostrap.startup.analyze.excel;

public enum ColorType {
    /**
     * 黑色
     */
    BLACK(30),
    /**
     * 红色
     */
    RED(31),
    /**
     * 绿色
     */
    GREEN(32),
    /**
     * 黄色
     */
    YELLOW(33),
    /**
     * 蓝色
     */
    BLUE(34),
    /**
     * 紫色
     */
    PURPLE(35),
    /**
     * 天蓝色
     */
    SKY_BLUE(36),
    /**
     * 白色
     */
    WHITE(37);
    private int value;

    ColorType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}