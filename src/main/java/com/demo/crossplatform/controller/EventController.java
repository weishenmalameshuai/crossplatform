package com.demo.crossplatform.controller;


import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.crossplatform.commonutils.ReponseCode;
import com.demo.crossplatform.entity.BlogNews;
import com.demo.crossplatform.entity.Event;
import com.demo.crossplatform.entity.SourceApp;
import com.demo.crossplatform.entity.User;
import com.demo.crossplatform.entity.excel.EventExcel;
import com.demo.crossplatform.service.BlogNewsService;
import com.demo.crossplatform.service.EventService;
import com.demo.crossplatform.service.SourceAppService;
import com.demo.crossplatform.service.UserService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/crossplatform/event")
public class EventController {

    @Resource
    private EventService eventService;
    @Resource
    private UserService userService;
    @Resource
    private BlogNewsService blogNewsService;
    @Resource
    private SourceAppService sourceAppService;

    @RequestMapping("toPage")
    public Object toPage(){
        return new ModelAndView("event/eventPage");
    }

    @RequestMapping("toEventUpload")
    public Object toEventUpload(){
        return new ModelAndView("event/eventUpload");
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

        long currentPage = 1;
        long pageSize = 10;
        String nameLike = "";

        if (data.containsKey("currentPage")) {
            currentPage = Long.valueOf(data.get("currentPage") + "");
        }

        if (data.containsKey("pageSize")) {
            pageSize = Long.valueOf(data.get("pageSize") + "");
        }

        if (data.containsKey("name_like")) {
            nameLike = (String) data.get("name_like");
        }

        Page<Event> eventPage = new Page<>(currentPage, pageSize);
        QueryWrapper<Event> eduTeacherQueryWrapper = new QueryWrapper();

        if (!StringUtils.isEmpty(nameLike)) {
            eduTeacherQueryWrapper.like("name", nameLike);
        }

        eventService.page(eventPage, eduTeacherQueryWrapper);

        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("rows", eventPage.getRecords());
        resultMap.put("total", String.valueOf(eventPage.getTotal()));
        resultMap.put("pageNum", String.valueOf(eventPage.getCurrent()));
        resultMap.put("pageSize",String.valueOf(eventPage.getSize()));
        return ReponseCode.ok().data(resultMap);
    }

    @RequestMapping("getBaseData")
    public Object getBaseData(HttpSession session) {

        String eventId = (String) session.getAttribute("event_id");

        QueryWrapper<BlogNews> blogNewsQueryWrapper = new QueryWrapper();
        blogNewsQueryWrapper.eq("event_id", eventId);

        Map<Integer, Integer> userMap = new HashMap();
        List<BlogNews> BlogNewsLists = blogNewsService.list(blogNewsQueryWrapper);

        for (BlogNews blogNews : BlogNewsLists) {
            int userid = blogNews.getUserId();
            if (userMap.containsKey(userid)) {
                int BlogNewsCount = userMap.get(userid);
                userMap.put(userid, ++BlogNewsCount);
            }
            else {
                userMap.put(userid, 1);
            }
        }

        List< Map<String, Object>> resultList = new ArrayList<>();
        Map<String, Object> map;
        for (Map.Entry<Integer, Integer> entry : userMap.entrySet()) {

            map = new HashMap();

            int userId = entry.getKey();
            int BlogNewsCount = entry.getValue();

            User user = userService.getById(userId);
            SourceApp sourceApp = sourceAppService.getById(user.getSrcAppId());

            map.put("user_id", String.valueOf(userId));
            map.put("blog_num", String.valueOf(BlogNewsCount));
            map.put("user_name", user.getUserName());
            map.put("description", user.getDescription());
            map.put("sourceApp_name", sourceApp.getName());
            map.put("src_app_id", sourceApp.getId());
            resultList.add(map);
        }

        Map<String,Object> result=new HashMap<>();
        result.put("event_info",resultList);
        result.put("event_id",eventId);

        return ReponseCode.ok().data(result);
    }

    @PostMapping("search")
    public ReponseCode doSearch(@RequestBody long current,
                                @RequestBody long limit) {

        Page<Event> eventPage = new Page<>(current, limit);
        eventService.page(eventPage, null);

        Map<String, Object> pageMap = new HashMap();
        pageMap.put("total", eventPage.getTotal());
        pageMap.put("size", eventPage.getSize());
        pageMap.put("current", eventPage.getCurrent());
        pageMap.put("pages", eventPage.getPages());
        pageMap.put("hasprevious", eventPage.hasPrevious());
        pageMap.put("hasnext", eventPage.hasNext());
        pageMap.put("events", eventPage.getRecords());
        return ReponseCode.ok().data("events", pageMap);
    }

    @PostMapping("select/{id}")
    public ReponseCode doSelect(@PathVariable("id") int id) {
        System.err.println(id);
        Event event = eventService.getById(id);
        return ReponseCode.ok().data("event", event);
    }

    @PostMapping("insert")
    public ReponseCode doInsert(@RequestBody Event event) {
        if (eventService.save(event)) {
            return ReponseCode.ok().data("event", event);
        }
        else {
            return ReponseCode.error().data("event", event);
        }

    }

    @PostMapping("delete/{id}")
    public ReponseCode doDelete(@PathVariable("id") int id) {

        if (eventService.removeById(id)) {
            return ReponseCode.ok();
        }
        else {
            return ReponseCode.error();
        }
    }

    @PostMapping("update")
    public ReponseCode doUpdate(@RequestBody Event event) {

        if (eventService.updateById(event)) {
            return ReponseCode.ok().data("event", event);
        }
        else {
            return ReponseCode.error().data("event", event);
        }

    }

    @RequestMapping("uploadFile")
    public Object uploadFile(@RequestBody MultipartFile file){

        List<EventExcel> eventExcels = eventService.doBatchImport(file, eventService);

        return ReponseCode.ok().data("blogList",eventExcels);
    }

    @RequestMapping("batchAddEvents")
    public Object batchAddBlogNews(@RequestBody Map<String, Object> data,
                                   HttpSession session) throws ParseException {

        List<Map<String,Object>> datalist= (List<Map<String, Object>>) data.get("rows");
        List<EventExcel> eventExcels = new ArrayList<>();
        for (Map<String,Object> item:datalist){
            EventExcel eventExcel=new EventExcel();
            eventExcel.setEventName(item.get("eventName")+"");
            eventExcel.setUser_name(item.get("user_name")+"");
            eventExcel.setSource_app_name(item.get("source_app_name")+"");
            eventExcel.setLssue_date(item.get("lssue_date")+"");
            eventExcel.setContent(item.get("content")+"");
            eventExcels.add(eventExcel);
        }
        QueryWrapper<Event> eventQueryWrapper;
        QueryWrapper<SourceApp> sourceAppQueryWrapper;
        QueryWrapper<User> userQueryWrapper;
        for (EventExcel eventExcel : eventExcels) {
            eventQueryWrapper = new QueryWrapper();
            eventQueryWrapper.eq("name", eventExcel.getEventName());
            Event event = eventService.getOne(eventQueryWrapper);
            if (event == null) {
                event = new Event();
                event.setName(eventExcel.getEventName());
                eventService.save(event);
            }

            sourceAppQueryWrapper = new QueryWrapper();
            sourceAppQueryWrapper.eq("name", eventExcel.getSource_app_name());
            SourceApp sourceApp = sourceAppService.getOne(sourceAppQueryWrapper);

            if (sourceApp == null) {
                sourceApp = new SourceApp();
                sourceApp.setName(eventExcel.getSource_app_name());
                sourceAppService.save(sourceApp);
            }

            userQueryWrapper = new QueryWrapper();
            userQueryWrapper.eq("user_name", eventExcel.getUser_name());
            userQueryWrapper.eq("src_app_id", sourceApp.getId());
            User user = userService.getOne(userQueryWrapper);

            if (user == null) {
                user = new User();
                user.setUserName(eventExcel.getUser_name());
                user.setSrcAppId(sourceApp.getId());
                userService.save(user);
            }

            BlogNews blogNews = new BlogNews();
            blogNews.setSrcAppId(sourceApp.getId());
            blogNews.setUserId(user.getId());
            blogNews.setEventId(event.getId());
            blogNews.setContent(eventExcel.getContent());
            try {
                blogNews.setCreateTime(new SimpleDateFormat("yyyy-MM-dd")
                        .parse(eventExcel.getLssue_date()));
            }catch (Exception e){
                blogNews.setCreateTime(null);
            }

            blogNewsService.save(blogNews);
        }

        return ReponseCode.ok();
    }


    @RequestMapping("/download")
    public void download(@RequestParam(defaultValue = "",name = "event_id")String event_id,
                         HttpServletRequest request, HttpServletResponse response) throws Exception {

        int eventId = Integer.parseInt(event_id);
        QueryWrapper<Event> eventQueryWrapper =  new QueryWrapper();
        eventQueryWrapper.eq("id", eventId);
        Event event = eventService.getOne(eventQueryWrapper);


        QueryWrapper<BlogNews> blogNewsQueryWrapper = new QueryWrapper();
        blogNewsQueryWrapper.eq("event_id", eventId);
        List<BlogNews> BlogNewsLists = blogNewsService.list(blogNewsQueryWrapper);

        QueryWrapper<SourceApp> sourceAppQueryWrapper;
        QueryWrapper<User> userQueryWrapper;

        List<EventExcel> eventExcels = new ArrayList();

        for (BlogNews blogNews : BlogNewsLists) {

            int srcAppId = blogNews.getSrcAppId();
            int userId = blogNews.getUserId();

            sourceAppQueryWrapper = new QueryWrapper();
            sourceAppQueryWrapper.eq("id", srcAppId);
            SourceApp sourceApp = sourceAppService.getOne(sourceAppQueryWrapper);

            userQueryWrapper = new QueryWrapper();
            userQueryWrapper.eq("id", userId);
            User user = userService.getOne(userQueryWrapper);

            EventExcel eventExcel = new EventExcel();
            eventExcel.setSource_app_name(sourceApp.getName());
            eventExcel.setUser_name(user.getUserName());
            eventExcel.setContent(blogNews.getContent());
            try{
                eventExcel.setLssue_date(new SimpleDateFormat("yyyy-MM-dd").format(blogNews.getCreateTime()));
            }catch (Exception e){
                eventExcel.setLssue_date("");
            }
            eventExcel.setEventName(event.getName());
            eventExcels.add(eventExcel);
        }


        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode(event.getName(), "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), EventExcel.class).sheet("Sheet1").doWrite(eventExcels);

    }

}

