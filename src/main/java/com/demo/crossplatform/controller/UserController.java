package com.demo.crossplatform.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.crossplatform.commonutils.ReponseCode;
import com.demo.crossplatform.entity.BlogNews;
import com.demo.crossplatform.entity.Event;
import com.demo.crossplatform.entity.SourceApp;
import com.demo.crossplatform.entity.User;
import com.demo.crossplatform.service.BlogNewsService;
import com.demo.crossplatform.service.EventService;
import com.demo.crossplatform.service.SourceAppService;
import com.demo.crossplatform.service.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
@RequestMapping("/crossplatform/user")
public class UserController {

    @Resource
    private UserService userService;
    @Resource
    private EventService eventService;
    @Resource
    private SourceAppService sourceAppService;
    @Resource
    private BlogNewsService blogNewsService;

    @RequestMapping("toDetail")
    public Object toDetail(@RequestParam(defaultValue = "", name = "id") String id,
                           HttpSession session) {
        if (!"".equals(id)) {
            session.setAttribute("user_id", id);
        }
        return new ModelAndView("event/eventDetail");
    }

    @RequestMapping("toUserAssociation")
    public Object toUserAssociation(@RequestParam(defaultValue = "", name = "event_id") String event_id, HttpSession session) {
        session.setAttribute("UA_even_id", event_id);
        return new ModelAndView("user/userAssociation");
    }

    @RequestMapping("toUserMatching")
    public Object toUserMatching(@RequestParam(defaultValue = "", name = "event_id") String event_id, @RequestParam(defaultValue = "", name = "user_id") String user_id, HttpSession session) {
        Map<String, Object> data = new HashMap<>();
        data.put("user_id", user_id);
        data.put("event_id", event_id);
        session.setAttribute("UM-dataInfo", data);
        return new ModelAndView("user/userMatching");
    }

    @RequestMapping("getDataInfo")
    public Object getDataInfo(HttpSession session) {
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> data = (Map<String, Object>) session.getAttribute("UM-dataInfo");
        if (data.containsKey("event_id") && !"".equals(data.get("event_id"))) {
            resultMap.put("event_name", eventService.getById(data.get("event_id").toString()).getName());
        }
        if (data.containsKey("user_id") && !"".equals(data.get("user_id"))) {
            resultMap.put("user_name", userService.getById(data.get("user_id").toString()).getUserName());
        }
        return ReponseCode.ok().data("dataInfo", resultMap);
    }

    @RequestMapping("getUAbaseData")
    public Object getUAbaseData(HttpSession session) {
        Object event_id=session.getAttribute("UA_even_id");
        Map<String, Object> resultMap = new HashMap<>();

        List<Event> events = eventService.list(null);
        List<Map<String, Object>> eventList = new ArrayList<>();
        for (Event event : events) {
            eventList.add(JSONObject.parseObject("{value:\"" + event.getName() + "\",key:\"" + event.getId() + "\"}"));
        }
        resultMap.put("eventList", eventList);

        if(event_id!=null&&!"".equals(event_id)) {
            List<Map<String, Object>> salist = new ArrayList<>();
            QueryWrapper<BlogNews> blogNewsQueryWrapper = new QueryWrapper();
            blogNewsQueryWrapper.eq("event_id", event_id);
            Set<String> appIds = new HashSet<>();
            List<BlogNews> BlogNewsLists = blogNewsService.list(blogNewsQueryWrapper);
            for (BlogNews blogNews : BlogNewsLists) {
                if (!appIds.contains(blogNews.getSrcAppId() + "")) {
                    appIds.add(blogNews.getSrcAppId() + "");
                    SourceApp item = sourceAppService.getById(blogNews.getSrcAppId());
                    salist.add(JSONObject.parseObject("{value:\"" + item.getName() + "\",key:\"" + item.getId() + "\"}"));
                }
            }
            resultMap.put("salist", salist);
        }

        List<SourceApp> sourceApps = sourceAppService.list(null);
        List<Map<String, Object>> sourceAppList = new ArrayList<>();
        for (SourceApp sourceApp : sourceApps) {
            sourceAppList.add(JSONObject.parseObject("{value:\"" + sourceApp.getName() + "\",key:\"" + sourceApp.getId() + "\"}"));
        }
        resultMap.put("sourceAppList", sourceAppList);

        Map<String, Object> dataInfo = new HashMap<>();
        dataInfo.put("event_id", event_id+"");

        resultMap.put("dataInfo", dataInfo);

        return ReponseCode.ok().data(resultMap);
    }

    @RequestMapping("getAppListByEven")
    public Object getAppListByEven(@RequestBody Map<String, Object> data) {
        String event_id = data.get("event_id").toString();
        List<Map<String, Object>> sourceAppList = new ArrayList<>();
        QueryWrapper<BlogNews> blogNewsQueryWrapper = new QueryWrapper();
        blogNewsQueryWrapper.eq("event_id", event_id);
        Set<String> appIds=new HashSet<>();
        List<BlogNews> BlogNewsLists = blogNewsService.list(blogNewsQueryWrapper);
        for (BlogNews blogNews : BlogNewsLists) {
            if (!appIds.contains(blogNews.getSrcAppId()+"")) {
                appIds.add(blogNews.getSrcAppId()+"");
                SourceApp item = sourceAppService.getById(blogNews.getSrcAppId());
                sourceAppList.add(JSONObject.parseObject("{value:\""+item.getName()+"\",key:\""+item.getId()+"\"}"));
            }
        }
        return ReponseCode.ok().data("appList", sourceAppList);
    }

    @RequestMapping("getUserListByApp")
    public Object getUserListByApp(@RequestBody Map<String, Object> data) {
        List<Map<String, Object>> userlist = new ArrayList<>();
        if (data.containsKey("id")) {
            String appId = data.get("id").toString();

            QueryWrapper<User> userQueryWrapper = new QueryWrapper();
            userQueryWrapper.eq("src_app_id", appId);
            List<User> users = userService.list(userQueryWrapper);
            for (User user : users) {
                userlist.add(JSONObject.parseObject("{value:\"" + user.getUserName() + "\",key:\"" + user.getId() + "\"}"));
            }
        }
        return ReponseCode.ok().data("userlist", userlist);
    }

    @RequestMapping("getBaseData")
    public Object getBaseData(HttpSession session) {
        Object user_id = session.getAttribute("user_id");

        int userId = Integer.parseInt((String)user_id);
        User user = userService.getById(userId);

        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("id", user.getId());
        userMap.put("user_name", user.getUserName());
        userMap.put("description", user.getDescription());
        userMap.put("photo_loc", user.getPhotoLoc());

        QueryWrapper<BlogNews> blogNewsQueryWrapper = new QueryWrapper();
        blogNewsQueryWrapper.eq("user_id", user.getId());
        List<BlogNews> BlogNewsLists = blogNewsService.list(blogNewsQueryWrapper);
        List<Map<String, Object>> blogList = new ArrayList<>();

        Map<String, Object> blog;
        for (BlogNews blogNews : BlogNewsLists) {
            blog = new HashMap();
            blog.put("id", blogNews.getId());
            blog.put("create_time", blogNews.getCreateTime());
            blog.put("content", blogNews.getContent());
            blogList.add(blog);
        }
        resultMap.put("blogList", blogList);
        resultMap.put("user", userMap);
        return ReponseCode.ok().data(resultMap);
    }

    @GetMapping("search/{current}/{limit}")
    public ReponseCode doSearch(@PathVariable long current,
                                @PathVariable long limit) {

        Page<User> userPage = new Page<>(current, limit);
        userService.page(userPage, null);

        Map<String, Object> pageMap = new HashMap();

        pageMap.put("total", userPage.getTotal());
        pageMap.put("size", userPage.getSize());
        pageMap.put("current", userPage.getCurrent());
        pageMap.put("pages", userPage.getPages());
        pageMap.put("hasprevious", userPage.hasPrevious());
        pageMap.put("hasnext", userPage.hasNext());
        pageMap.put("users", userPage.getRecords());

        return ReponseCode.ok().data("users", pageMap);
    }

    @GetMapping("select/{id}")
    public ReponseCode doSelect(@PathVariable("id") int id) {

        User user = userService.getById(id);
        return ReponseCode.ok().data("user", user);
    }

    @PostMapping("insert")
    public ReponseCode doInsert(@RequestBody User user) {

        if (userService.save(user)) {
            return ReponseCode.ok().data("user", user);
        } else {
            return ReponseCode.error().data("user", user);
        }

    }

    @DeleteMapping("delete/{id}")
    public ReponseCode doDelete(@PathVariable int id) {

        if (userService.removeById(id)) {
            return ReponseCode.ok();
        } else {
            return ReponseCode.error();
        }
    }

    @RequestMapping("update")
    public ReponseCode doUpdate(@RequestBody Map<String, Object> data) {

        User user = new User();
        user.setId(Integer.parseInt(data.get("id") + ""));
        user.setUserName(data.get("user_name") + "");
        user.setDescription(data.get("description") + "");
        user.setPhotoLoc(data.get("photo_loc") + "");

        if (userService.updateById(user)) {
            return ReponseCode.ok().data("user", user);
        } else {
            return ReponseCode.error().data("user", user);
        }

    }

}

