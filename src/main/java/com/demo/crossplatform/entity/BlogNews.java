package com.demo.crossplatform.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BlogNews implements Serializable {


    private static final long serialVersionUID = 3041528375275559307L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer srcAppId;
    private Integer userId;
    private Integer eventId;
    private String content;
    private Date createTime;


}
