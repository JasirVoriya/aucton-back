
import cn.voriya.auction.entity.enums.GoodsType;
import cn.voriya.framework.utils.JsonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

public class EnumsTest {
    @Test
    public void test() {
        System.out.println(JsonUtil.toObject("民间珍品", GoodsType.class));
    }
}
