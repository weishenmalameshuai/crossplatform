package com.demo.crossplatform.listener;


import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.demo.crossplatform.entity.Event;
import com.demo.crossplatform.entity.excel.BlogNewsExcel;
import com.demo.crossplatform.entity.excel.EventExcel;
import com.demo.crossplatform.service.EventService;

import java.util.ArrayList;
import java.util.List;

public class EventExcelListener extends AnalysisEventListener<EventExcel> {

    private List<EventExcel> eventExcels = new ArrayList();

    public List<EventExcel> getEventExcels() {
        return eventExcels;
    }

    @Override
    public void invoke(EventExcel eventExcel, AnalysisContext analysisContext) {
        eventExcels.add(eventExcel);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
