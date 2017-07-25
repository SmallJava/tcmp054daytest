package cn.tcmp.dao;

import cn.tcmp.entity.Student;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by TYY on 2017/7/4.
 */
public interface StudentMapper {
    //学生总记录数
    Integer queryCountStudent();

    //分页展示全部学生
    List<Student> queryStudent(@Param("start") Integer start, @Param("rows") Integer rows);

    //添加学生
    Integer addStudent(Student student);

    //按id查单个学生
    Student queryStudentById(Integer id);

    //修改学生
    Integer updateStudent(Student student);

    //删除单个学生
    Integer deleteStudentById(Integer id);

    //删除多个学生

    //删除单个班级之前先删除学生
    Integer deleteStudentByGradeId(Integer gradeId);

    //删除多个班级之前先删除学生
    Integer deleteStudentByGradeIdList(List<Integer> gradeList);

    //按外键班级id查学生
    List<Student> queryStudentByGradeId(Integer gradeId);
}
