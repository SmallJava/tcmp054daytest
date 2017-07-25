import base.TestBase;
import cn.tcmp.entity.Hobby;
import cn.tcmp.service.HobbyService;
import cn.tcmp.tools.PageUtil;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by TYY on 2017/7/3.
 */
public class HobbyTest extends TestBase {

    @Resource
    private HobbyService hobbyService;

    //分页展示全部爱好
    @Test
    public void testQueryHobby() {
        PageUtil pageUtil = hobbyService.queryHobby(1, 5);
        System.out.println(pageUtil.getTotal());
        List<Hobby> hobbys = pageUtil.getObjs();
        for (int i = 0; i < hobbys.size(); i++) {
            Hobby hobby = hobbys.get(i);
            System.out.println(hobby.toString());
        }
    }

    //添加爱好
    @Test
    public void testAddHobby() {
        Hobby hobby = new Hobby();
        hobby.setHobbyName("写好多代码21");
        Integer count = hobbyService.addHobby(hobby);
        System.out.println(count);
    }

    //修改爱好
    @Test
    public void testUpdateHobby() {
        Hobby hobby = new Hobby();
        hobby.setId(10);
        hobby.setHobbyName("我爱修改11");
        Integer count = hobbyService.updateHobby(hobby);
        System.out.println(count);
    }

    //删除爱好
    @Test
    public void testDeleteHobby() {
        Integer count = hobbyService.deleteHobby(37);
        System.out.println(count);
    }

    //删除多个爱好
    @Test
    public void testDeleteHobbyByIdList() {
        List<Integer> idList = new ArrayList<>();
        idList.add(36);
        idList.add(39);
        Integer count = hobbyService.deleteHobbyByIdList(idList);
        System.out.println(count);
    }

}
