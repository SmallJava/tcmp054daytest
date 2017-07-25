package cn.tcmp.service;

import cn.tcmp.dao.ScoreMapper;
import cn.tcmp.entity.Score;
import cn.tcmp.tools.PageUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by TYY on 2017/7/16.
 */
@Service
public class ScoreServiceImpl implements ScoreService {

    @Resource
    private ScoreMapper scoreMapper;

    //按班级id或学生id查成绩并分页展示
    @Override
    public PageUtil<Score> queryScoreByGradeIdOrStudentId(Integer page, Integer rows, Integer gradeId, Integer studentId) {
        PageUtil<Score> pageUtil = new PageUtil<Score>(page, rows);
        //1.计算总记录数
        Integer count = scoreMapper.queryCountScoreByGradeIdOrStudentId(gradeId, studentId);
        //2.计算开始位置start
        Integer start = pageUtil.getStart();
        //3.查列表信息
        List<Score> scoreList = scoreMapper.queryScoreByGradeIdOrStudentId(start, rows, gradeId, studentId);
        //4.进行分页
        pageUtil.setTotal(count);
        pageUtil.setObjs(scoreList);
        //返回对象
        return pageUtil;
    }

}
