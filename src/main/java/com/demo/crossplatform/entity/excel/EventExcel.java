package com.demo.crossplatform.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class EventExcel {

    @ExcelProperty(value = "事件", index = 0)
    String eventName;

    @ExcelProperty(value = "平台", index = 1)
    private String source_app_name;

    @ExcelProperty(value = "用户名", index = 2)
    private String user_name;

    @ExcelProperty(value = "发文日期", index = 3)
    private String lssue_date;

    @ExcelProperty(value="博文内容", index = 4)
    private String content;
}
