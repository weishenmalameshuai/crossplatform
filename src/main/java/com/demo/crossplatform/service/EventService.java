package com.demo.crossplatform.service;

import com.demo.crossplatform.entity.Event;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author administrator
 * @since 2021-04-02
 */
public interface EventService extends IService<Event> {

    //导入
    void doBatchImport(MultipartFile file,EventService eventService);
}
