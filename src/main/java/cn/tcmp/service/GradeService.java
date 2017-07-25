package cn.tcmp.service;

import cn.tcmp.entity.Grade;
import cn.tcmp.tools.PageUtil;

import java.util.List;

/**
 * Created by TYY on 2017/6/27.
 */
public interface GradeService {
    //按班级名模糊查询并分页展示
    PageUtil queryGradeByName(Integer page, Integer rows, String gradeName);

    //按id查单个班级
    Grade queryGradeById(Integer id);

    //添加班级
    Integer addGrade(Grade grade);

    //修改班级
    Integer updateGrade(Grade grade);

    //删除单个班级
    Integer deleteGradeById(Integer gradeId);

    //删除多个班级
    Integer deleteGradeByIdList(List<Integer> idList);

    //添加、修改学生时查询全部班级
    List<Grade> queryAllGrade();
}
