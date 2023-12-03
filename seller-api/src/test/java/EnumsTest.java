import cn.voriya.auction.entity.dos.File;
import cn.voriya.auction.entity.enums.FileType;
import cn.voriya.framework.utils.JsonUtil;
import org.junit.jupiter.api.Test;

public class EnumsTest {
    @Test
    public void test() {
        final File file = new File();
        file.setType(FileType.PNG);
        System.out.println(JsonUtil.toJson(file));
    }
}
