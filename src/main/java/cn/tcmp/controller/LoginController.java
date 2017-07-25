package cn.tcmp.controller;

import cn.tcmp.entity.RoleUser;
import cn.tcmp.entity.User;
import cn.tcmp.service.RoleUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * Created by TYY on 2017/7/13.
 */
@Controller
public class LoginController {

    @Resource
    private RoleUserService roleUserService;

    /**
     * 用户名和密码登录
     * @param user
     * @param session
     * @param model
     * @return
     */
    @RequestMapping(value = "login" ,method = RequestMethod.POST)
    public String login(User user, HttpSession session, Model model){
        RoleUser ru=roleUserService.queryUserByUserNameAndPassword(user);
        if(null!=ru){
            System.out.println("登录成功");
            session.setAttribute("roleUser",ru);
            return "main";
        }
        System.out.println("登录失败");
        model.addAttribute("msg","用户名或密码错误");
        return "index";
    }


}
