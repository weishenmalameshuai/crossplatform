package com.demo.crossplatform.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.crossplatform.commonutils.ReponseCode;
import com.demo.crossplatform.entity.BlogNews;
import com.demo.crossplatform.entity.Event;
import com.demo.crossplatform.entity.SourceApp;
import com.demo.crossplatform.entity.User;
import com.demo.crossplatform.entity.excel.BlogNewsExcel;
import com.demo.crossplatform.service.BlogNewsService;
import com.demo.crossplatform.service.EventService;
import com.demo.crossplatform.service.SourceAppService;
import com.demo.crossplatform.service.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/crossplatform/blogNews")
public class BlogNewsController {

    @Resource
    private EventService eventService;
    @Resource
    private UserService userService;
    @Resource
    private BlogNewsService blogNewsService;
    @Resource
    private SourceAppService sourceAppService;

    @RequestMapping("toUpload")
    public Object toUpload(@RequestParam(defaultValue = "",name = "event_id")String event_id, HttpSession session){
        session.setAttribute("blogUpload_event_id",event_id);
        return new ModelAndView("blogNews/blogNewsUpload");
    }

    @RequestMapping("getBaseData")
    public Object getBaseData(HttpSession session){
        Map<String,Object> result=new HashMap<>();
        result.put("event_id",session.getAttribute("blogUpload_event_id"));
        List<Event> events = eventService.list(null);
        List<Map<String, Object>> eventList = new ArrayList<>();
        for (Event event : events) {
            eventList.add(JSONObject.parseObject("{value:\"" + event.getName() + "\",key:\"" + event.getId() + "\"}"));
        }
        result.put("eventList", eventList);
        return ReponseCode.ok().data(result);
    }

    @RequestMapping("uploadFile")
    public Object uploadFile(@RequestBody MultipartFile file){

        List<BlogNewsExcel> blogList = blogNewsService.doBatchImport(file, blogNewsService);

        return ReponseCode.ok().data("blogList",blogList);
    }

    @RequestMapping("batchAddBlogNews")
    public Object batchAddBlogNews(@RequestBody Map<String, Object> data) {
        List<Map<String,Object>> datalist= (List<Map<String, Object>>) data.get("rows");
        Object event_id=data.get("event_id");
        List<BlogNewsExcel> blogNewsExcels = new ArrayList<>();
        for (Map<String,Object> item:datalist){
            BlogNewsExcel blogNewsExcel=new BlogNewsExcel();
            blogNewsExcel.setUser_name(item.get("user_name")+"");
            blogNewsExcel.setSource_app_name(item.get("source_app_name")+"");
            blogNewsExcel.setLssue_date(item.get("lssue_date")+"");
            blogNewsExcel.setContent(item.get("content")+"");
            blogNewsExcels.add(blogNewsExcel);
        }
        QueryWrapper<SourceApp> sourceAppQueryWrapper;
        QueryWrapper<User> userQueryWrapper;
        for (BlogNewsExcel excel : blogNewsExcels) {
            sourceAppQueryWrapper = new QueryWrapper();
            sourceAppQueryWrapper.eq("name", excel.getSource_app_name());
            SourceApp sourceApp = sourceAppService.getOne(sourceAppQueryWrapper);
            if (sourceApp == null) {
                sourceApp = new SourceApp();
                sourceApp.setName(excel.getSource_app_name());
                sourceAppService.save(sourceApp);
            }
            userQueryWrapper = new QueryWrapper();
            userQueryWrapper.eq("user_name ", excel.getUser_name());
            userQueryWrapper.eq("src_app_id ", sourceApp.getId());
            User user = userService.getOne(userQueryWrapper);
            if (user == null) {
                user = new User();
                user.setUserName(excel.getUser_name());
                user.setSrcAppId(sourceApp.getId());
                userService.save(user);
            }
            BlogNews blogNews = new BlogNews();
            blogNews.setSrcAppId(sourceApp.getId());
            blogNews.setUserId(user.getId());
            blogNews.setEventId(Integer.parseInt(event_id.toString()) );
            blogNews.setContent(excel.getContent());
            try {
                blogNews.setCreateTime(new SimpleDateFormat("yyyy-MM-dd")
                        .parse(excel.getLssue_date()));
            }catch (Exception e){
                blogNews.setCreateTime(null);
            }
            blogNewsService.save(blogNews);
        }

        return ReponseCode.ok();
    }

    @RequestMapping("batchDelBlogNews")
    public Object deleteBlogNews(@RequestBody Map<String, Object> data) {
        Object event_id = data.get("event_id");
        Object user_id = data.get("user_id");
        QueryWrapper<BlogNews> blogNewsQueryWrapper = new QueryWrapper();
        blogNewsQueryWrapper.eq("event_id", event_id);
        blogNewsQueryWrapper.eq("user_id", user_id);
        if (blogNewsService.remove(blogNewsQueryWrapper)) {
            return ReponseCode.ok();
        }
        else {
            return ReponseCode.error();
        }
    }

    @GetMapping("search/{current}/{limit}")
    public ReponseCode doSearch(@PathVariable long current,
                                @PathVariable long limit) {
        Page<BlogNews> blogNewsPage = new Page<>(current, limit);
        blogNewsService.page(blogNewsPage, null);
        Map<String, Object> pageMap = new HashMap();
        pageMap.put("total", blogNewsPage.getTotal());
        pageMap.put("size", blogNewsPage.getSize());
        pageMap.put("current", blogNewsPage.getCurrent());
        pageMap.put("pages", blogNewsPage.getPages());
        pageMap.put("hasnext", blogNewsPage.hasNext());
        pageMap.put("blogNews", blogNewsPage.getRecords());
        return ReponseCode.ok().data("blogNews", pageMap);
    }

    @GetMapping("select/{id}")
    public ReponseCode doSelect(@PathVariable("id") int id) {

        BlogNews blogNews = blogNewsService.getById(id);
        return ReponseCode.ok().data("blogNews", blogNews);
    }

    @PostMapping("insert")
    public ReponseCode doInsert(@RequestBody BlogNews blogNews) {

        if (blogNewsService.save(blogNews)) {
            return ReponseCode.ok().data("blogNews", blogNews);
        }
        else {
            return ReponseCode.error().data("blogNews", blogNews);
        }

    }

    @DeleteMapping("delete/{id}")
    public ReponseCode doDelete(@PathVariable int id) {

        if (blogNewsService.removeById(id)) {
            return ReponseCode.ok();
        }
        else {
            return ReponseCode.error();
        }
    }

    @PostMapping("update")
    public ReponseCode doUpdate(@RequestBody BlogNews blogNews) {

        if (blogNewsService.updateById(blogNews)) {
            return ReponseCode.ok().data("blogNews", blogNews);
        }
        else {
            return ReponseCode.error().data("blogNews", blogNews);
        }

    }

}

