import base.TestBase;
import cn.tcmp.entity.Grade;
import cn.tcmp.service.GradeService;
import cn.tcmp.tools.PageUtil;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by TYY on 2017/6/29.
 */
public class GradeTest extends TestBase {

    @Resource
    private GradeService gradeService;

    //按班级名模糊查询并分页展示
    @Test
    public void testQueryGradeByName() {
        PageUtil pageUtil = gradeService.queryGradeByName(1, 5,"05");
        System.out.println(pageUtil.getTotal());
        List<Grade> gradeList = pageUtil.getObjs();
        for (int i = 0; i < gradeList.size(); i++) {
            Grade grade = gradeList.get(i);
            System.out.println(grade.toString());
        }
    }

    //按id查单个班级
    @Test
    public void testqueryGradeById(){
        Grade grade=gradeService.queryGradeById(2);
        System.out.println(grade.toString());
    }

    //添加班级
    @Test
    public void addGrade(){
        Grade grade=new Grade();
        grade.setDetails("008班级详情说明");
        grade.setGradeName("008班");
        Integer count=gradeService.addGrade(grade);
        System.out.println("添加班级结果======="+count);
    }

    //修改班级
    @Test
    public void testupdateGrade(){
        Grade grade=new Grade();
        grade.setId(10);
        grade.setDetails("中文测试");
        Integer count=gradeService.updateGrade(grade);
        System.out.println(count);
    }

    //删除单个班级
    @Test
    public void testDeleteGradeById() {
        Integer count = gradeService.deleteGradeById(1);
        System.out.println(count);
    }

    //删除多个班级
    @Test
    public void testDeleteGradeByIdList() {
        List<Integer> idList = new ArrayList<>();
        idList.add(9);
        idList.add(10);
        Integer count = gradeService.deleteGradeByIdList(idList);
        System.out.println(count);
    }

    //添加、修改学生时查询全部班级
    @Test
    public void testQueryAllGrade() {
        List<Grade> gradeList = gradeService.queryAllGrade();
        for (int i = 0; i < gradeList.size(); i++) {
            System.out.println(gradeList.get(i));
        }
    }

}
