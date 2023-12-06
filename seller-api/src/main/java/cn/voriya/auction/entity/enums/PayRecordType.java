package cn.voriya.auction.entity.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

public enum PayRecordType {
    Deposit("参拍保证金"),
    PayGoods("拍品付款");


    @EnumValue
    private final String type;
    @JsonValue
    public String getType() {
        return type;
    }

    PayRecordType(String type) {
        this.type = type;
    }
    public static PayRecordType fromType(String type) {
        for (PayRecordType value : PayRecordType.values()) {
            if (value.type.equals(type)) {
                return value;
            }
        }
        return null;
    }
}
