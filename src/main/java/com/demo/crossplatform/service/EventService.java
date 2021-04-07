package com.demo.crossplatform.service;

import com.demo.crossplatform.entity.Event;
import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.crossplatform.entity.excel.EventExcel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author administrator
 * @since 2021-04-02
 */
public interface EventService extends IService<Event> {

    List<EventExcel> doBatchImport(MultipartFile file, EventService eventService);
}
