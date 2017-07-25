package cn.tcmp.controller;

import cn.tcmp.entity.Score;
import cn.tcmp.service.ScoreService;
import cn.tcmp.tools.PageUtil;
import com.alibaba.fastjson.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by TYY on 2017/7/16.
 */

@Controller
@RequestMapping(value = "score")
public class ScoreController {

    @Resource
    private ScoreService scoreService;

    @RequestMapping(value = "toScoreList", method = RequestMethod.GET)
    public String toScoreList() {
        return "scoreList";
    }

    /**
     * 按班级id或学生id查成绩并分页展示
     * @param page
     * @param rows
     * @param gradeId
     * @param studentId
     * @return
     */
    @RequestMapping(value = "queryScoreByGradeIdOrStudentId", method = RequestMethod.GET,
        produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String queryScoreByGradeIdOrStudentId(Integer page, Integer rows, Integer gradeId, Integer studentId) {
        PageUtil<Score> pageUtil = scoreService.queryScoreByGradeIdOrStudentId(page, rows, gradeId, studentId);
        Map<String, Object> map = new HashMap<>();
        map.put("total", pageUtil.getTotal());
        map.put("rows", pageUtil.getObjs());
        String json = JSONArray.toJSONString(map);
        System.out.println(json);
        return json;
    }
}

