package com.example.model;

import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;

@Data
@Table(name = "hotel")
public class Hotel implements Serializable {
    private int code;
    private String address;
    private String name;
    private String weight;
    private String phone;
    private String img;
    private String level;
    private int type;
}
