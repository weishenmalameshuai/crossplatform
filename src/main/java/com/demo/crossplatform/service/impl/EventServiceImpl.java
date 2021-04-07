package com.demo.crossplatform.service.impl;

import com.alibaba.excel.EasyExcel;
import com.demo.crossplatform.entity.Event;
import com.demo.crossplatform.entity.excel.EventExcel;
import com.demo.crossplatform.listener.EventExcelListener;
import com.demo.crossplatform.mapper.EventMapper;
import com.demo.crossplatform.service.EventService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author administrator
 * @since 2021-04-02
 */
@Service
public class EventServiceImpl extends ServiceImpl<EventMapper, Event> implements EventService {

    @Override
    public void doBatchImport(MultipartFile file, EventService eventService) {

        try {
            InputStream in = file.getInputStream();

            EasyExcel.read(in, EventExcel.class, new EventExcelListener(eventService)).sheet().doRead();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
