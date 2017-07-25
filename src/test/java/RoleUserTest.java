import base.TestBase;
import cn.tcmp.entity.RoleUser;
import cn.tcmp.entity.User;
import cn.tcmp.service.RoleUserService;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Created by TYY on 2017/7/16.
 */
public class RoleUserTest extends TestBase {

    @Resource
    private RoleUserService roleUserService;

    @Test
    public void testQueryUser(){
        User user=new User();
        user.setUserName("admin");
        user.setPassword("admin");
        RoleUser roleUser=roleUserService.queryUserByUserNameAndPassword(user);
        System.out.println(roleUser);
    }
}
