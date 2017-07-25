package cn.tcmp.dao;

/**
 * Created by thinkpad on 2017/7/18.
 */
public interface StudentNumMapper {
    /*添加学号唯一策略*/
    //编号最大值加1
    Integer updateNumByGradeId(Integer gradeId);
    //
    Integer queryNumByGradeId(Integer gradeid);
}
