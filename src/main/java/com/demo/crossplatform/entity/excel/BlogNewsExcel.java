package com.demo.crossplatform.entity.excel;


import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;


@Data
public class BlogNewsExcel implements Serializable {

    private static final long serialVersionUID = 1411000936667843153L;
    @ExcelProperty(value = "平台", index = 0)
    private String source_app_name;

    @ExcelProperty(value = "用户名", index = 1)
    private String user_name;

    @ExcelProperty(value= "发文日期", index = 2)
    private String lssue_date;

    @ExcelProperty(value = "博文内容", index = 3)
    private String content;

}
