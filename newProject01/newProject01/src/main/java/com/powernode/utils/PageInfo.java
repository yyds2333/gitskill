package com.powernode.utils;

import java.util.List;

public class PageInfo <T>{
    /**
     * 页数
     */
    private int pagaColumn;
    /**
     * 每页容量
     */
    private int pageSize;
    /**
     * 总页数
     */
    private int totalCount;
    /**
     * 总条数
     */
    private int totalNums;

    /**
     * 返回列表
     */
    private List<T> list;

    public PageInfo() {
    }

    public int getPagaColumn() {
        return pagaColumn;
    }

    public void setPagaColumn(int pagaColumn) {
        this.pagaColumn = pagaColumn;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 设置总页数
     *
     * @return
     */
    public int getTotalCount() {
        if (totalNums % pageSize == 0) {
            totalCount = totalNums / pageSize;
        } else {
            totalCount = totalNums / pageSize + 1;
        }
        return totalCount;
    }

    public void setTotalNums(int totalNums) {
        this.totalNums = totalNums;
    }

    public int getTotalNums() {

        return totalNums;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
