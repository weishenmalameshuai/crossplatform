package com.demo.crossplatform.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author administrator
 * @since 2021-04-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class User implements Serializable {


    private static final long serialVersionUID = 4597555939031191652L;
    /**
     * 用户ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 来源平台ID
     */
    private Integer srcAppId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 简介
     */
    private String description;

    /**
     * 头像地址
     */
    private String photoLoc;


}
