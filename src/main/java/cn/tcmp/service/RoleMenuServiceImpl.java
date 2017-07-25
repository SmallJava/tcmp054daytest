package cn.tcmp.service;

import cn.tcmp.dao.RoleMenuMapper;
import cn.tcmp.entity.Menu;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by TYY on 2017/7/16.
 */
@Service
public class RoleMenuServiceImpl implements RoleMenuService{

    @Resource
    private RoleMenuMapper roleMenuMapper;

    //树形一级菜单
    @Override
    public List<Menu> queryMenuByRoleId(Integer roleId) {
        return roleMenuMapper.queryMenuByRoleId(roleId);
    }

    //树形二级菜单
    @Override
    public List<Menu> queryMenuByParentId(Integer parentId, Integer roleId) {
        return roleMenuMapper.queryMenuByParentId(parentId,roleId);
    }

}
