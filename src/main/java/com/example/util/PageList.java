package com.example.util;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PageList<T> implements Serializable {

    private int totalCount;
    private int pageSize;
    private int pageIndex;
    private int pageCount;

    private List<T> list;
}
