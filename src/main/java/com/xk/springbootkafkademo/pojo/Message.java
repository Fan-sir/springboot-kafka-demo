package com.xk.springbootkafkademo.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
public class Message {

    private String id;
    private String message;
    private Date sendTime;

}
