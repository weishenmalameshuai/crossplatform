package com.demo.crossplatform.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class EventExcel {

    @ExcelProperty(index = 0)
    String eventName;


}
