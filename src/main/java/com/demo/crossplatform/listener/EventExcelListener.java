package com.demo.crossplatform.listener;


import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.demo.crossplatform.entity.Event;
import com.demo.crossplatform.entity.excel.EventExcel;
import com.demo.crossplatform.service.EventService;

public class EventExcelListener extends AnalysisEventListener<EventExcel> {

    public EventService eventService;
    public EventExcelListener() {}
    public EventExcelListener(EventService eventService) {
        this.eventService = eventService;
    }

    @Override
    public void invoke(EventExcel eventExcel, AnalysisContext analysisContext) {

        //一行一行读取内容
        if (eventExcel == null) {
            return;
        }

        Event event = new Event();
        //事件名称
        event.setName(eventExcel.getEventName());
        eventService.save(event);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
