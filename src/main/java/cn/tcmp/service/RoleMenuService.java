package cn.tcmp.service;

import cn.tcmp.entity.Menu;

import java.util.List;

/**
 * Created by TYY on 2017/7/16.
 */
public interface RoleMenuService {
    //树形一级菜单
    List<Menu> queryMenuByRoleId(Integer roleId);
    //树形二级菜单
    List<Menu> queryMenuByParentId(Integer parentId, Integer roleId);

}
