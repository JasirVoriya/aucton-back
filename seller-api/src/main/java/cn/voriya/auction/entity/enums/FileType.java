package cn.voriya.auction.entity.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum FileType {
    PNG("image/png"),
    JPG("image/jpg"),
    JPEG("image/jpeg"),
    GIF("image/gif");


    private final String type;
    @JsonValue
    public String getType() {
        return type;
    }

    FileType(String type) {
        this.type = type;
    }
}
