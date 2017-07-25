package cn.tcmp.controller;

import cn.tcmp.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 用户登录
 * 预设用户名：abcd
 * 密码：1234
 */
@Controller
public class UserController {

    @RequestMapping(value="user",method= RequestMethod.POST)
    public String user(User user, Model model){
        if("abcd".equals(user.getUserName())&&"1234".equals(user.getPassword())){
            System.out.println("登录成功user");

            return "main";
        }
        System.out.println("登录失败user");
        model.addAttribute("msg","用户名或密码错误");
        return "index";
    }
}
