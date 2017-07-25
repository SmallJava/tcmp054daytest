package cn.tcmp.service;

import cn.tcmp.dao.RoleUserMapper;
import cn.tcmp.entity.RoleUser;
import cn.tcmp.entity.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by TYY on 2017/7/16.
 */

@Service
public class RoleUserServiceImpl implements RoleUserService {

    @Resource
    private RoleUserMapper roleUserMapper;

    @Override
    public RoleUser queryUserByUserNameAndPassword(User user) {
        return roleUserMapper.queryUserByUserNameAndPassword(user);
    }
}
