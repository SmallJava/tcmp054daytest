import base.TestBase;
import cn.tcmp.entity.Menu;
import cn.tcmp.service.RoleMenuService;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by TYY on 2017/7/16.
 */
public class RoleMenuTest extends TestBase {

    @Resource
    private RoleMenuService roleMenuService;

    //树形一级菜单
    @Test
    public void testQueryMenuByRoleId(){
        List<Menu> menuList=roleMenuService.queryMenuByRoleId(1);
        if(null!=menuList){
            for(int i=0;i<menuList.size();i++){
                System.out.println(menuList.get(i));
            }
        }
    }

    //树形二级级菜单
    @Test
    public void testQueryMenuByParentId(){
        List<Menu> menuList=roleMenuService.queryMenuByParentId(1,1);
        if(null!=menuList){
            for(int i=0;i<menuList.size();i++){
                System.out.println(menuList.get(i));
            }
        }
    }
}
