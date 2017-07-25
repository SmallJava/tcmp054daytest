package cn.tcmp.controller;

import cn.tcmp.entity.Student;
import cn.tcmp.service.StudentService;
import cn.tcmp.tools.Message;
import cn.tcmp.tools.PageUtil;
import com.alibaba.fastjson.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * Created by TYY on 2017/7/4.
 */

@Controller
@RequestMapping(value = "student")
public class StudentController {

    @Resource
    private StudentService studentService;

    @RequestMapping(value = "toStudentList", method = RequestMethod.GET)
    public String toStudentList() {
        return "studentList";
    }

    /**
     * 分页展示全部学生
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping(value = "queryStudent", method = RequestMethod.POST,
        produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String queryStudent(Integer page, Integer rows) {
        PageUtil<Student> p = studentService.queryStudent(page, rows);
        Map<String, Object> map = new HashMap<>();
        map.put("total", p.getTotal());
        map.put("rows", p.getObjs());
        String json = JSONArray.toJSONString(map);
        System.out.println(json);
        return json;
    }

    /**
     * 添加学生
     * @param student
     * @return
     */
    @RequestMapping(value = "addStudent", method = RequestMethod.POST,
        produces = {"application/json; charset=UTF-8"})
    @ResponseBody
    public String addStudent(Student student) {
        Integer count = studentService.addStudent(student);
        Message msg = new Message();
        if (count > 0) {
            msg.setMsg("增加成功");
        } else {
            msg.setMsg("增加失败，请联系管理员");
        }
        return JSONArray.toJSONString(msg);
    }

    /**
     * 按id查单个学生
     * @param id
     * @return
     */
    @RequestMapping(value = "queryStudentById", method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String queryStudentById(Integer id) {
        Student student = studentService.queryStudentById(id);
        return JSONArray.toJSONString(student);
    }

    /**
     * 修改学生
     * @param student
     * @return
     */
    @RequestMapping(value = "updateStudent", method = RequestMethod.POST,
        produces = {"application/json; charset=UTF-8"})
    @ResponseBody
    public String updateStudent(Student student) {
        Integer count = studentService.updateStudent(student);
        Message msg = new Message();
        if (count > 0) {
            msg.setMsg("修改成功");
        } else {
            msg.setMsg("修改失败，请联系管理员");
        }
        return JSONArray.toJSONString(msg);
    }

    /**
     * 删除单个学生
     * @param id
     * @return
     */
    @RequestMapping(value = "deleteStudentById", method = RequestMethod.POST,
        produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String deleteStudentById(Integer id) {
        Integer count = studentService.deleteStudentById(id);
        Message msg = new Message();
        if (count > 0) {
            msg.setMsg("删除单个学生成功");
        } else {
            msg.setMsg("删除单个学生失败");
        }
        return JSONArray.toJSONString(msg);
    }

    /**
     * 按外键班级id查学生
     * @param gradeId
     * @return
     */
    @RequestMapping(value="queryStudentByGradeId",method = RequestMethod.GET,
        produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String queryStudentByGradeId(Integer gradeId){
        List<Student> studentList=studentService.queryStudentByGradeId(gradeId);
        return JSONArray.toJSONString(studentList);
    }

}
