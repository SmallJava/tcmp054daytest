package cn.tcmp.service;

import cn.tcmp.dao.GradeMapper;
import cn.tcmp.dao.StudentMapper;
import cn.tcmp.entity.Grade;
import cn.tcmp.tools.PageUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by TYY on 2017/6/27.
 */

@Service
public class GradeServiceImpl implements GradeService {

    @Resource
    private GradeMapper gradeMapper;

    //按班级名模糊查询并分页展示
    @Override
    public PageUtil queryGradeByName(Integer page, Integer rows, String gradeName) {

        PageUtil pageUtil = new PageUtil(page, rows);
        //1.计算总记录数
        Integer count = gradeMapper.queryCountGradeByName(gradeName);
        //2.计算开始位置start
        Integer start = pageUtil.getStart();
        //3.查列表信息
        List<Grade> gradeList = gradeMapper.queryGradeByName(start, rows, gradeName);
        //4.进行分页
        pageUtil.setTotal(count);
        pageUtil.setObjs(gradeList);
        //返回对象
        return pageUtil;
    }

    //按id查单个班级
    public Grade queryGradeById(Integer id){
        return gradeMapper.queryGradeById(id);
    }

    //添加班级
    public Integer addGrade(Grade grade){
        Date now=new Date();
        grade.setCreateDate(now);
        return gradeMapper.addGrade(grade);
    }

    //修改班级
    @Override
    public Integer updateGrade(Grade grade) {
        return gradeMapper.updateGrade(grade);
    }


    /*删除班级之前,要先删除班级内的学生*/
    @Resource
    private StudentMapper studentMapper;

    //删除单个班级
    @Override
    public Integer deleteGradeById(Integer gradeId) {
        //1.先删除班级内的学生
        Integer n1 = studentMapper.deleteStudentByGradeId(gradeId);
        System.out.println("删除学生记录共"+n1+"条");
        //2.再删除班级
        Integer n2 = gradeMapper.deleteGradeById(gradeId);
        return n2;
    }

    //删除多个班级
    @Override
    public Integer deleteGradeByIdList(List<Integer> idList) {
        //1.先删除班级内的学生
        Integer n1 = studentMapper.deleteStudentByGradeIdList(idList);
        System.out.println("删除学生记录共"+n1+"条");
        //2.再删除班级
        Integer n2 = gradeMapper.deleteGradeByIdList(idList);
        return n2;
    }

    //添加、修改学生时查询全部班级
    @Override
    public List<Grade> queryAllGrade() {
        return gradeMapper.queryAllGrade();
    }

}

