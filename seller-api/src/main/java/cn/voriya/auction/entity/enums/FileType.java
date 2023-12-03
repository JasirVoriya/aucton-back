package cn.voriya.auction.entity.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

public enum FileType {
    PNG("image/png"),
    JPG("image/jpg"),
    JPEG("image/jpeg"),
    GIF("image/gif");


    @EnumValue
    private final String type;
    @JsonValue
    public String getType() {
        return type;
    }

    FileType(String type) {
        this.type = type;
    }
    public static FileType fromType(String type) {
        for (FileType value : FileType.values()) {
            if (value.type.equals(type)) {
                return value;
            }
        }
        return null;
    }
}
