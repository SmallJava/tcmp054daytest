package cn.tcmp.service;

import cn.tcmp.dao.HobbyMapper;
import cn.tcmp.entity.Hobby;
import cn.tcmp.tools.PageUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by TYY on 2017/7/3.
 */

@Service
public class HobbyServiceImpl implements HobbyService{

    @Resource
    private HobbyMapper hobbyMapper;

    //分页展示全部爱好
    @Override
    public PageUtil<Hobby> queryHobby(Integer page, Integer rows) {
        PageUtil<Hobby> pageUtil = new PageUtil<Hobby>(page, rows);
        //1.计算总记录数
        Integer count = hobbyMapper.queryCountHobby();
        //2.计算开始位置start
        Integer start = pageUtil.getStart();
        //3.查询
        List<Hobby> hobbyList = hobbyMapper.queryHobby(start, rows);
        //4.进行分页
        pageUtil.setTotal(count);
        pageUtil.setObjs(hobbyList);
        //返回对象
        return pageUtil;
    }

    //添加爱好
    @Override
    public Integer addHobby(Hobby hobby) {
        Date now = new Date();
        hobby.setCreateTime(now);
        return hobbyMapper.addHobby(hobby);
    }

    //按id查单个爱好，用于修改前展示
    @Override
    public Hobby queryHobbyById(Integer id) {
        return hobbyMapper.queryHobbyById(id);
    }

    //修改爱好
    @Override
    public Integer updateHobby(Hobby hobby) {
        return hobbyMapper.updateHobby(hobby);
    }

    //删除爱好
    @Override
    public Integer deleteHobby(Integer id) {
        return hobbyMapper.deleteHobby(id);
    }

    //删除多个爱好
    @Override
    public Integer deleteHobbyByIdList(List<Integer> idList) {
        Integer count = hobbyMapper.deleteHobbyByIdList(idList);
        return count;
    }

}
