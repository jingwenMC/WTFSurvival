import org.junit.Test;
import top.jingwenmc.wtfsurvival.util.ExceptionUtil;

import java.io.IOException;

public class ExceptionUtilUnitTest {
    @Test
    public void HandlerTest() {
        Throwable throwable = new Exception("Test",
                new IOException("CauseTest",
                new IllegalStateException()));
        ExceptionUtil.handle(throwable,System.out);
        System.out.println();
    }
}
