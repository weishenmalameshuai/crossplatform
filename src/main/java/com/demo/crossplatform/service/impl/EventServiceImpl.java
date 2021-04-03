package com.demo.crossplatform.service.impl;

import com.demo.crossplatform.entity.Event;
import com.demo.crossplatform.mapper.EventMapper;
import com.demo.crossplatform.service.EventService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
