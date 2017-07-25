package cn.tcmp.controller;

import cn.tcmp.entity.Menu;
import cn.tcmp.entity.RoleUser;
import cn.tcmp.service.RoleMenuService;
import com.alibaba.fastjson.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by TYY on 2017/7/17.
 */
@Controller
public class TreeController {

    @Resource
    private RoleMenuService roleMenuService;

    //树形一级菜单
    @RequestMapping(value="initTree" ,method = RequestMethod.GET,
            produces ={"application/json;charset=UTF-8"} )
    @ResponseBody
    public String initTree(HttpSession session){
        RoleUser ru=(RoleUser)session.getAttribute("roleUser");
        Integer roleId=ru.getRole().getId();
        List<Menu> menuList=roleMenuService.queryMenuByRoleId(roleId);
        String json= JSONArray.toJSONString(menuList);
        System.out.println(json);
        return json;
    }

    //树形二级菜单
    @RequestMapping(value ="appendTree",method = RequestMethod.GET,
            produces ={"application/json;charset=UTF-8"} )
    @ResponseBody
    public String appendTree(HttpSession session,Integer parentId){
        RoleUser ru=(RoleUser)session.getAttribute("roleUser");
        Integer roleId=ru.getRole().getId();
        List<Menu> menuList=roleMenuService.queryMenuByParentId(parentId,roleId);
        String json=JSONArray.toJSONString(menuList);
        System.out.println(json);
        return json;
    }

}
