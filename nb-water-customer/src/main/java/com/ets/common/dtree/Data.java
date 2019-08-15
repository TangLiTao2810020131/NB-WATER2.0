package com.ets.common.dtree;

import java.util.List;

/**
 * @author 姚轶文
 * @create 2018- 11-20 14:06
 */
public class Data {

    private String id;
    private String title;
    private boolean isLast;
    private String level;
    private String parentId;
    private List<Data> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean getIsLast() {
        return isLast;
    }

    public void setIsLast(boolean isLast) {
        this.isLast = isLast;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public List<Data> getChildren() {
        return children;
    }

    public void setChildren(List<Data> children) {
        this.children = children;
    }
}
