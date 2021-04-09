package com.demo.crossplatform.service;

import com.demo.crossplatform.entity.Event;
import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.crossplatform.entity.excel.EventExcel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface EventService extends IService<Event> {

    List<EventExcel> doBatchImport(MultipartFile file, EventService eventService);
}
