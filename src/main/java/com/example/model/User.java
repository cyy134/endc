package com.example.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import javax.persistence.Table;
import java.io.Serializable;


@Data
@Table(name = "user")
public class User implements Serializable {
    @Id
    private int code;
    private String acount;
    private String nickName;
    private String passworld;
    private String age;
    private String emil;
    private String createtime;
    private String isdel;
    private String isheadpacticre;
    private String ssex;
    private String phone;
    private String extendjson;
}
