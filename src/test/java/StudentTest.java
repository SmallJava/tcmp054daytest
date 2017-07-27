import base.TestBase;
import cn.tcmp.entity.Grade;
import cn.tcmp.entity.Student;
import cn.tcmp.service.StudentService;
import cn.tcmp.tools.PageUtil;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by TYY on 2017/7/4.
 */
public class StudentTest extends TestBase {

    @Resource
    private StudentService studentService;

    //分页展示全部学生
    @Test
    public void testQueryStudent() {
        PageUtil<Student> pageUtil = studentService.queryStudent(1, 5);
        System.out.println("记录数："+pageUtil.getTotal());
        List<Student> studentList = pageUtil.getObjs();
        for (int i=0;i<studentList.size();i++) {
            System.out.println(studentList.get(i));
        }
    }

    //添加学生
    @Test
    public void testAddStudent() {
        Student student = new Student();
        student.setStudentName("addName");
        student.setGender("男");
        student.setAge(22);
        student.setStudentNum("0004");
        Grade grade = new Grade();
        grade.setId(4);
        student.setGrade(grade);
        Integer count = studentService.addStudent(student);
        System.out.println(count);
    }

    //按id查单个学生
    @Test
    public void testQueryStudentById() {
        Student student = studentService.queryStudentById(2);
        System.out.println(student.toString());
    }

    //修改学生
    @Test
    public void testUpdateStudent() {
        Student student = new Student();
        student.setId(2);
        Grade grade = new Grade();
        grade.setId(3);
        student.setGrade(grade);
        student.setStudentName("谈ABC");
        student.setStudentNum("谈001");
        student.setGender("男");
        student.setAge(28);
        Integer count = studentService.updateStudent(student);
        System.out.println(count);
    }

    //删除单个学生
    @Test
    public void testDeleteStudentById() {
        Integer count = studentService.deleteStudentById(14);
        System.out.println(count);
    }

    //删除多个学生
    @Test
    public void testDeleteStudentByIdList() {
        List<Integer> idList = new ArrayList<>();
        idList.add(14);
        idList.add(15);
        Integer count = studentService.deleteStudentByIdList(idList);
        System.out.println(count);
    }

    //按外键班级id查学生
    @Test
    public void testQueryStudentByGradeId(){
        List<Student> studentList=studentService.queryStudentByGradeId(2);
        for(int i=0;i<studentList.size();i++){
            System.out.println(studentList.get(i));
        }
    }

    //添加学号唯一策略
    @Test
    public void testAddStudentNum(){
        Integer gradeId=1;
        String gradeName="ab班";
        String studentNum=studentService.addStudentNum(gradeId, gradeName);
        System.out.println("学号："+studentNum);
    }

}
