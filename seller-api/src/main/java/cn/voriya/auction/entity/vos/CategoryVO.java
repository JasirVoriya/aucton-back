package cn.voriya.auction.entity.vos;

import cn.hutool.core.bean.BeanUtil;
import cn.voriya.auction.entity.dos.Category;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class CategoryVO extends Category {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long value;
    private String label;
    private List<CategoryVO> children;
    public static CategoryVO valueOf(Category category) {
        CategoryVO categoryVO = new CategoryVO();
        BeanUtil.copyProperties(category, categoryVO);
        categoryVO.setValue(category.getId());
        categoryVO.setLabel(category.getName());
        return categoryVO;
    }
}
