package com.demo.crossplatform.entity.excel;


import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;


@Data
public class BlogNewsExcel implements Serializable {

    private static final long serialVersionUID = 1411000936667843153L;
    @ExcelProperty(index = 0)
    private String source_app_name;

    @ExcelProperty(index = 1)
    private String user_name;

    @ExcelProperty(index = 2)
    private String lssue_date;

    @ExcelProperty(index = 3)
    private String content;

}
