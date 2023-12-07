package cn.voriya.auction.entity.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
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

    @JsonCreator
    public static GoodsType fromString(String type) {
        for (GoodsType goodsType : GoodsType.values()) {
            if (goodsType.getType().equals(type)) {
                return goodsType;
            }
        }
        throw new IllegalArgumentException("Unknown enum type: " + type);
    }
}
