package com.example.util;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class pageList<T> implements Serializable {

    private int pageSize;
    private int pageIndex;
    private int pageCount;
    private int totalCount;
    private List<T> list;
}
