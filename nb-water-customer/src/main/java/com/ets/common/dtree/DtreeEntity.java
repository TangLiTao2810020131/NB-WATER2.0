package com.ets.common.dtree;

import java.util.List;

/**
 * @author 姚轶文
 * @create 2018- 11-20 14:05
 */
public class DtreeEntity {

    private Status status;
    private List<Data> data;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }
}
