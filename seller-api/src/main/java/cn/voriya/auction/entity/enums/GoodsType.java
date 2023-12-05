package cn.voriya.auction.entity.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

public enum GoodsType {
    Judicial("司法资产"),
    RareGoods("民间珍品");


    @EnumValue
    private final String type;
    @JsonValue
    public String getType() {
        return type;
    }

    GoodsType(String type) {
        this.type = type;
    }
    public static GoodsType fromType(String type) {
        for (GoodsType value : GoodsType.values()) {
            if (value.type.equals(type)) {
                return value;
            }
        }
        return null;
    }
}
