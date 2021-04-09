package com.demo.crossplatform.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class User implements Serializable {


    private static final long serialVersionUID = 4597555939031191652L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer srcAppId;
    private String userName;
    private String description;
    private String photoLoc;


}
