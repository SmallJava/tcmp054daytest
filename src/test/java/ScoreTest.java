import base.TestBase;
import cn.tcmp.entity.Score;
import cn.tcmp.service.ScoreService;
import cn.tcmp.tools.PageUtil;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by TYY on 2017/7/16.
 */
public class ScoreTest extends TestBase {

    @Resource
    private ScoreService scoreService;

    //按班级id或学生id查成绩并分页展示
    @Test
    public void testQueryScore() {
        PageUtil<Score> pageUtil = scoreService.queryScoreByGradeIdOrStudentId(1, 5, 2, null);
        List<Score> scoreList = pageUtil.getObjs();
        for (int i=0;i<scoreList.size();i++) {
            System.out.println(scoreList.get(i));
        }
    }
}
