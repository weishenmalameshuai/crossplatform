package com.demo.crossplatform.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Event implements Serializable {


    private static final long serialVersionUID = 5216015687146429581L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String name;
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Integer isDelete;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;



}
