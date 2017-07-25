package cn.tcmp.service;

import cn.tcmp.entity.Score;
import cn.tcmp.tools.PageUtil;

/**
 * Created by TYY on 2017/7/16.
 */
public interface ScoreService {
    //按班级id或学生id查成绩并分页展示
    PageUtil<Score> queryScoreByGradeIdOrStudentId(
        Integer page, Integer rows,
        Integer gradeId, Integer studentId
    );


}
