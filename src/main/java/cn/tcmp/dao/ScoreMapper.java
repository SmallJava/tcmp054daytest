package cn.tcmp.dao;

import cn.tcmp.entity.Score;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by TYY on 2017/7/16.
 */
public interface ScoreMapper {
    //按班级id或学生id查总记录数
    Integer queryCountScoreByGradeIdOrStudentId(
        @Param("gradeId") Integer gradeId, @Param("studentId") Integer studentId
    );

    //按班级id或学生id查成绩并分页展示
    List<Score> queryScoreByGradeIdOrStudentId(
        @Param("start") Integer start, @Param("rows") Integer rows,
        @Param("gradeId") Integer gradeId, @Param("studentId") Integer studentId
    );

}
