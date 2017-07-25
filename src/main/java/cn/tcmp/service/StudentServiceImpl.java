package cn.tcmp.service;

import cn.tcmp.dao.StudentMapper;
import cn.tcmp.dao.StudentNumMapper;
import cn.tcmp.entity.Student;
import cn.tcmp.tools.PageUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by TYY on 2017/7/4.
 */

@Service
public class StudentServiceImpl implements StudentService {

    @Resource
    private StudentMapper studentMapper;

    //分页展示全部学生
    @Override
    public PageUtil<Student> queryStudent(Integer page, Integer rows) {
        PageUtil pageUtil = new PageUtil(page, rows);
        //1.计算总记录数
        Integer count = studentMapper.queryCountStudent();
        //2.计算开始位置start
        Integer start = pageUtil.getStart();
        //3.查列表信息
        List<Student> studentList = studentMapper.queryStudent(start, rows);
        //4.进行分页
        pageUtil.setTotal(count);
        pageUtil.setObjs(studentList);
        //返回对象
        return pageUtil;
    }

    //添加学生
    @Override
    public Integer addStudent(Student student) {
        //1.生成学生编号
        String studentNum=addStudentNum(student.getGrade().getId(), student.getGrade().getGradeName());
        //2.学生编号赋值
        student.setStudentNum(studentNum);
        return studentMapper.addStudent(student);
    }

    //按id查单个学生
    @Override
    public Student queryStudentById(Integer id) {
        return studentMapper.queryStudentById(id);
    }

    //修改学生
    @Override
    public Integer updateStudent(Student student) {
        return studentMapper.updateStudent(student);
    }

    //删除单个学生
    @Override
    public Integer deleteStudentById(Integer id) {
        return studentMapper.deleteStudentById(id);
    }

    //删除多个学生


    //按外键班级id查学生
    @Override
    public List<Student> queryStudentByGradeId(Integer gradeId) {
        return studentMapper.queryStudentByGradeId(gradeId);
    }


    @Resource
    private StudentNumMapper studentNumMapper;

    //添加学号唯一策略
    @Override
    public String addStudentNum(Integer gradeId, String gradeName) {
        //1. max_num加一
        Integer n=studentNumMapper.updateNumByGradeId(gradeId);
        //2. 查班级的max_num
        Integer max_num=studentNumMapper.queryNumByGradeId(gradeId);
        //3. 拼学生编号 gradeName+'00...'+max_num
        String str=gradeName+max_num;
        int length=15-str.length();
        for(int i=0;i<length;i++){
            gradeName=gradeName+0;
        }
        return gradeName+max_num;
    }

}
