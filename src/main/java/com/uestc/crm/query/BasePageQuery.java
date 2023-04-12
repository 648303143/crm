package com.uestc.crm.query;


/**
 * @author zhangqingyang
 * @create 2023-03-2023/3/1 18:38
 */

public class BasePageQuery {
    protected Integer current = 1;
    protected Integer size = 10;

    public Integer getCurrent() {
        return this.current;
    }

    public Integer getSize() {
        return this.size;
    }
}
