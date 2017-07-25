package cn.tcmp.entity;

/**
 * Created by TYY on 2017/7/16.
 */
public class RoleMenu {
    private Integer id;
    private Role role;
    private Menu menu;

    public RoleMenu() {
    }

    @Override
    public String toString() {
        return "RoleMenu{" +
                "id=" + id +
                ", role=" + role +
                ", menu=" + menu +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }
}
