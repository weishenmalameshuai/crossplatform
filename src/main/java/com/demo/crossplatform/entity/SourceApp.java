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
public class SourceApp implements Serializable {


    private static final long serialVersionUID = 1073592466191996196L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String name;


}
