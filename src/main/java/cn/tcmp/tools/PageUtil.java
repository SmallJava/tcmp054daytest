package cn.tcmp.tools;

import java.util.List;

/**
 * Created by TYY on 2017/6/27.
 */
public class PageUtil<T>{
    /**
     * page	当前页码	从页面获取
     rows	每页显示记录条数	从页面获取
     total	总共记录条数	select count(1) from table 查询
     start
     */
    /**
     * 当前页码	从页面获取
     */
    Integer page;
    /**
     * 每页显示记录条数	从页面获取
     */
    Integer rows;
    /**
     * 总共记录条数	select count(1) from table 查询
     */
    Integer total;
    /**
     * 开始位置	通过page,total,rows计算,start=(page-1)*rows;
     */
    Integer start;

    List<T> objs;

    public List<T> getObjs() {
        return objs;
    }

    public void setObjs(List<T> objs) {
        this.objs = objs;
    }

    public PageUtil(Integer page, Integer rows) {
        this.page = page;
        this.rows = rows;
        this.start=(page-1)*rows;
    }

    public PageUtil() {
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }
}
