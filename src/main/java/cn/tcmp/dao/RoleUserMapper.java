package cn.tcmp.dao;

import cn.tcmp.entity.RoleUser;
import cn.tcmp.entity.User;

/**
 * Created by TYY on 2017/7/16.
 */
public interface RoleUserMapper {

    RoleUser queryUserByUserNameAndPassword(User user);
}
