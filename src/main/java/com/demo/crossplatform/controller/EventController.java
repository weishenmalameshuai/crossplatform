package com.demo.crossplatform.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.crossplatform.commonutils.ReponseCode;
import com.demo.crossplatform.entity.BlogNews;
import com.demo.crossplatform.entity.Event;
import com.demo.crossplatform.service.EventService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author administrator
 * @since 2021-04-02
 */
@RestController
@RequestMapping("/crossplatform/event")
public class EventController {

    @Resource
    private EventService eventService;

    //查询(列表数据)
    @GetMapping("search/{current}/{limit}")
    public ReponseCode doSearch(@PathVariable long current,
                                @PathVariable long limit) {

        Page<Event> eventPage = new Page<>(current, limit);
        eventService.page(eventPage, null);

        Map<String, Object> pageMap = new HashMap();

        //总记录数
        pageMap.put("total", eventPage.getTotal());
        //每页显示记录数
        pageMap.put("size", eventPage.getSize());
        //当前页
        pageMap.put("current", eventPage.getCurrent());
        //总页数
        pageMap.put("pages", eventPage.getPages());
        //是否有上一页
        pageMap.put("hasprevious", eventPage.hasPrevious());
        //是否有下一页
        pageMap.put("hasnext", eventPage.hasNext());
        //记录数
        pageMap.put("events", eventPage.getRecords());

        return ReponseCode.ok().data("events", pageMap);
    }

    //查询(单条数据)
    @GetMapping("select/{id}")
    public ReponseCode doSelect(@PathVariable("id") int id) {
        Event event = eventService.getById(id);
        return ReponseCode.ok().data("event", event);
    }

    //新增方法
    @PostMapping("insert")
    public ReponseCode doInsert(@RequestBody Event event) {

        if (eventService.save(event)) {
            return ReponseCode.ok().data("event", event);
        }
        else {
            return ReponseCode.error().data("event", event);
        }

    }

    //删除方法
    @DeleteMapping("delete/{id}")
    public ReponseCode doDelete(@PathVariable int id) {

        if (eventService.removeById(id)) {
            return ReponseCode.ok();
        }
        else {
            return ReponseCode.error();
        }
    }

    //更新方法
    @PostMapping("update")
    public ReponseCode doUpdate(@RequestBody Event event) {

        if (eventService.updateById(event)) {
            return ReponseCode.ok().data("event", event);
        }
        else {
            return ReponseCode.error().data("event", event);
        }

    }
}

