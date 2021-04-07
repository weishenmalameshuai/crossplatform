package com.demo.crossplatform.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.alibaba.fastjson.JSONObject;
import com.demo.crossplatform.commonutils.ReponseCode;
import com.demo.crossplatform.entity.Event;
import com.demo.crossplatform.service.EventService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.http.HttpSession;

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
    private static final DateFormat datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    @RequestMapping("toPage")
    public Object toPage(){
        return new ModelAndView("event/eventPage");
    }

    @RequestMapping("toUserDetail")
    public Object toUserDetail(@RequestParam(defaultValue = "", name = "id") String id,
                           HttpSession session){
        if (!"".equals(id)) {
            session.setAttribute("user_id", id);
        }
        return new ModelAndView("user/userDetail");
    }

    @RequestMapping("toDetail")
    public Object toDetail(@RequestParam(defaultValue = "", name = "id") String id,
                           HttpSession session){
        if (!"".equals(id)) {
            session.setAttribute("event_id", id);
        }
        return new ModelAndView("event/eventDetail");
    }

    @RequestMapping("getData")
    public Object getData(@RequestBody Map<String, Object> data) {
        //输出data查看分页 参数
        List<Map<String,Object>> resultList=new ArrayList<>();
        resultList.add(JSONObject.parseObject("{id:\"1\",name:\"事件1\",createTime:\"2021-4-6\"}"));
        resultList.add(JSONObject.parseObject("{id:\"2\",name:\"事件2\",createTime:\"2021-4-6\"}"));
        resultList.add(JSONObject.parseObject("{id:\"3\",name:\"事件3\",createTime:\"2021-4-6\"}"));
        resultList.add(JSONObject.parseObject("{id:\"4\",name:\"事件4\",createTime:\"2021-4-6\"}"));
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("rows",resultList);
        resultMap.put("total",resultList.size());
        resultMap.put("pageNum",0);
        resultMap.put("pageSize",0);
        return ReponseCode.ok().data(resultMap);
    }

    @RequestMapping("getBaseData")
    public Object getBaseData() {
        List<Map<String,Object>> resultList=new ArrayList<>();
        resultList.add(JSONObject.parseObject("{id:\"1\",user_id:\"1\",sourceApp_name:\"pingtai1\",user_name:\"张三\",description:\"张三的描述\",blog_num:\"20\"}"));
        resultList.add(JSONObject.parseObject("{id:\"2\",user_id:\"1\",sourceApp_name:\"pingtai2\",user_name:\"里斯\",description:\"里斯的描述\",blog_num:\"24\"}"));
        resultList.add(JSONObject.parseObject("{id:\"3\",user_id:\"1\",sourceApp_name:\"pingtai3\",user_name:\"撒旦\",description:\"撒旦的描述\",blog_num:\"11\"}"));
        resultList.add(JSONObject.parseObject("{id:\"4\",user_id:\"1\",sourceApp_name:\"pingtai4\",user_name:\"迪迦\",description:\"迪迦的描述\",blog_num:\"4255\"}"));
        return ReponseCode.ok().data("event_info",resultList);
    }

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
    @PostMapping("select/{id}")
    public ReponseCode doSelect(@PathVariable("id") int id) {
        System.err.println(id);
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

