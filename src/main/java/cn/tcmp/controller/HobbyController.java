package cn.tcmp.controller;

import cn.tcmp.entity.Hobby;
import cn.tcmp.service.HobbyService;
import cn.tcmp.tools.Message;
import cn.tcmp.tools.PageUtil;
import com.alibaba.fastjson.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by TYY on 2017/7/3.
 */

@Controller
@RequestMapping(value = "hobby")
public class HobbyController {

    @Resource
    private HobbyService hobbyService;

    @RequestMapping(value = "toHobbyList", method = RequestMethod.GET)
    public String toHobbyList() {
        return "hobbyList";
    }

    /**
     * 分页展示全部爱好
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping(value = "queryHobby", method = RequestMethod.POST,
        produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String doHobbyList(Integer page, Integer rows) {
        PageUtil<Hobby> pageUtil = hobbyService.queryHobby(page, rows);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("total", pageUtil.getTotal());
        map.put("rows", pageUtil.getObjs());
        return JSONArray.toJSONString(map);
    }

    /**
     * 添加爱好
     * @param hobby
     * @return
     */
    @RequestMapping(value = "addHobby", method = RequestMethod.POST,
        produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String addHobby(Hobby hobby) {
        Integer count = hobbyService.addHobby(hobby);
        Message msg = new Message();
        if (count > 0) {
            msg.setMsg("添加成功");
        } else {
            msg.setMsg("添加失败，请联系管理员");
        }
        return JSONArray.toJSONString(msg);
    }

    /**
     * 按id查单个爱好
     * 用于修改前展示
     * @param id
     * @return
     */
    @RequestMapping(value = "queryHobbyById", method = RequestMethod.POST,
        produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String queryHobbyById(Integer id) {
        Hobby hobby = hobbyService.queryHobbyById(id);
        String json = JSONArray.toJSONString(hobby);
        System.out.println(json);
        return json;
    }

    /**
     * 修改爱好
     * @param hobby
     * @return
     */
    @RequestMapping(value = "updateHobby", method = RequestMethod.POST,
        produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String updateHobby(Hobby hobby) {
        Integer count = hobbyService.updateHobby(hobby);
        Message msg = new Message();
        if (count > 0) {
            msg.setMsg("修改成功");
        } else {
            msg.setMsg("修改失败，请联系管理员");
        }
        return JSONArray.toJSONString(msg);
    }

    /**
     * 删除单个爱好
     * @param id
     * @return
     */
    @RequestMapping(value = "deleteHobbyById", method = RequestMethod.POST,
        produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String deleteHobbyById(Integer id) {
        Integer count = hobbyService.deleteHobby(id);
        Message msg = new Message();
        if (count > 0) {
            msg.setMsg("删除成功");
        } else {
            msg.setMsg("删除失败，请联系管理员");
        }
        return JSONArray.toJSONString(msg);
    }

    /**
     * 删除多个爱好
     * @param hobbyIdListStr
     * @return
     */
    @RequestMapping(value = "deleteHobbyByIdList", method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String deleteHobbyByIdList(String hobbyIdListStr) {
        //字符串转为数组
        String[] idStrArray = hobbyIdListStr.split(",");
        List<Integer> idList = new ArrayList<>();
        for (int i=0;i<idStrArray.length;i++) {
            idList.add(Integer.parseInt(idStrArray[i]));
        }
        Integer count = hobbyService.deleteHobbyByIdList(idList);
        Message msg = new Message();
        if (count > 0) {
            msg.setMsg("删除多个成功");
        } else {
            msg.setMsg("删除多个失败");
        }
        return JSONArray.toJSONString(msg);
    }

}