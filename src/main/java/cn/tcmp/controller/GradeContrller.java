package cn.tcmp.controller;

import cn.tcmp.entity.Grade;
import cn.tcmp.service.GradeService;
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
 * Created by TYY on 2017/6/26.
 */

@Controller
@RequestMapping(value = "grade")
public class GradeContrller {

    @Resource
    private GradeService gradeService;

    @RequestMapping(value = "toGradeList", method = RequestMethod.GET)
    public String toGradeList() {
        return "gradeList";
    }

    /**
     * 按班级名模糊查询并分页展示
     * @param page
     * @param rows
     * @param gradeName
     * @return
     */
    @RequestMapping(value = "quryGrade", method = RequestMethod.POST,
        produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String quryGrade(Integer page, Integer rows, String gradeName) {
        PageUtil pageUtil = gradeService.queryGradeByName(page, rows, gradeName);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("total", pageUtil.getTotal());
        map.put("rows", pageUtil.getObjs());
        String json = JSONArray.toJSONString(map);
        return json;
    }

    /**
     * 按id查单个班级
     * 用于修改
     * @param id
     * @return
     */
    @RequestMapping(value = "queryGradeById", method = RequestMethod.POST,
        produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String queryGradeById(Integer id) {
        Grade grade = gradeService.queryGradeById(id);
        String json = JSONArray.toJSONString(grade);
        return json;
    }

    /**
     * 添加班级
     * @param grade
     * @return
     */
    @RequestMapping(value = "addGrade", method = RequestMethod.POST,
        produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String addGrade(Grade grade) {
        Integer count = gradeService.addGrade(grade);
        Message msg = new Message();
        if (count > 0) {
            msg.setMsg("添加成功");
        } else {
            msg.setMsg("添加失败");
        }
        return JSONArray.toJSONString(msg);
    }

    /**
     * 修改班级
     * @param grade
     * @return
     */
    @RequestMapping(value = "updateGrade", method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String updateGrade(Grade grade) {
        Message msg = new Message();
        Integer count = gradeService.updateGrade(grade);
        if (count > 0) {
            msg.setMsg("修改班级成功");
        } else {
            msg.setMsg("修改班级失败");
        }
        return JSONArray.toJSONString(msg);
    }

    /**
     * 删除单个班级
     * @param gradeId
     * @return
     */
    @RequestMapping(value = "deleteGradeById", method = RequestMethod.POST,
        produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String deleteGradeById(Integer gradeId) {
        Integer count = gradeService.deleteGradeById(gradeId);
        Message msg = new Message();
        if (count > 0) {
            msg.setMsg("删除单个班级成功");
        } else {
            msg.setMsg("删除单个班级失败");
        }
        return JSONArray.toJSONString(msg);
    }

    /**
     * 删除多个班级
     * @param gradeIdListStr
     * @return
     */
    @RequestMapping(value = "deleteGradeByIdList", method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String deleteGradeByIdList(String gradeIdListStr) {
        //字符串转为数组
        String[] idStrArray = gradeIdListStr.split(",");
        List<Integer> idList = new ArrayList<>();
        for (int i=0;i<idStrArray.length;i++) {
            idList.add(Integer.parseInt(idStrArray[i]));
        }
        Integer count = gradeService.deleteGradeByIdList(idList);
        Message msg = new Message();
        if (count > 0) {
            msg.setMsg("删除多个班级成功");
        } else {
            msg.setMsg("删除多个班级失败");
        }
        return JSONArray.toJSONString(msg);
    }

    /**
     * 添加、修改学生时查询全部班级
     * @return
     */
    @RequestMapping(value = "queryAllGrade", method = RequestMethod.GET,
            produces = {"application/json; charset=UTF-8"})
    @ResponseBody
    public String queryAllGrade() {
        List<Grade> gradeList = gradeService.queryAllGrade();
        return JSONArray.toJSONString(gradeList);
    }

}

