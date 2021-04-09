package com.demo.crossplatform.service.impl;

import com.alibaba.excel.EasyExcel;
import com.demo.crossplatform.entity.Event;
import com.demo.crossplatform.entity.excel.BlogNewsExcel;
import com.demo.crossplatform.entity.excel.EventExcel;
import com.demo.crossplatform.listener.BlogNewsExcelListener;
import com.demo.crossplatform.listener.EventExcelListener;
import com.demo.crossplatform.mapper.EventMapper;
import com.demo.crossplatform.service.EventService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

@Service
public class EventServiceImpl extends ServiceImpl<EventMapper, Event> implements EventService {

    @Override
    public List<EventExcel> doBatchImport(MultipartFile file, EventService eventService) {
        EventExcelListener excelListener = new EventExcelListener();

        try {
            InputStream in = file.getInputStream();
            EasyExcel.read(in, EventExcel.class, excelListener).sheet().doRead();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return excelListener.getEventExcels();
    }
}
