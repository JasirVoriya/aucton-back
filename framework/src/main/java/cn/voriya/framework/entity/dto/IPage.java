package cn.voriya.framework.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class IPage<T> {
    private List<T> records;
    private Long total;
    private Integer size;

}
