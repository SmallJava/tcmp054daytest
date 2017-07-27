package cn.tcmp.service;

import cn.tcmp.entity.Hobby;
import cn.tcmp.tools.PageUtil;

import java.util.List;

/**
 * Created by TYY on 2017/7/3.
 */
public interface HobbyService {

    //分页展示全部爱好
    PageUtil<Hobby> queryHobby(Integer page, Integer rows);

    //添加爱好
    Integer addHobby(Hobby hobby);

    //按id查单个爱好，用于修改前展示
    Hobby queryHobbyById(Integer id);

    //修改爱好
    Integer updateHobby(Hobby hobby);

    //删除单个爱好
    Integer deleteHobby(Integer id);

    //删除多个爱好
    Integer deleteHobbyByIdList(List<Integer> idList);
}
