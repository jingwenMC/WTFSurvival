import org.junit.Test;
import top.jingwenmc.wtfsurvival.util.LoginUtil;

public class LoginUtilTest {
    @Test
    public void login() {
        //System.out.println(LoginUtil.getLoginQRCodeLink());
        LoginUtil.oauthKey = "7085b23dc0ddaf247b061611d43cf6cf";
        LoginUtil.query();
    }
}
