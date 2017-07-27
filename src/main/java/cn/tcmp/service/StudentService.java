package cn.tcmp.service;

import cn.tcmp.entity.Student;
import cn.tcmp.tools.PageUtil;

import java.util.List;

/**
 * Created by TYY on 2017/7/4.
 */
public interface StudentService {
    //分页展示全部学生
    PageUtil<Student> queryStudent(Integer page, Integer rows);

    //添加学生
    Integer addStudent(Student student);

    //按id查单个学生
    Student queryStudentById(Integer id);

    //修改学生
    Integer updateStudent(Student student);

    //删除单个学生
    Integer deleteStudentById(Integer id);

    //删除多个学生
    Integer deleteStudentByIdList(List<Integer> idList);

    //按外键班级id查学生
    List<Student> queryStudentByGradeId(Integer gradeId);

    //添加学号唯一策略
    String addStudentNum(Integer gradeId, String gradeName);
}
