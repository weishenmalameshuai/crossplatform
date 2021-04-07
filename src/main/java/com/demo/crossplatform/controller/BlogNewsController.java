package com.demo.crossplatform.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.crossplatform.commonutils.ReponseCode;
import com.demo.crossplatform.entity.BlogNews;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
        return ReponseCode.ok().data(result);
    }

    @RequestMapping("uploadFile")
    public Object uploadFile(@RequestBody MultipartFile file){

        List<BlogNewsExcel> blogList = blogNewsService.doBatchImport(file, blogNewsService);

        return ReponseCode.ok().data("blogList",blogList);
    }

    @RequestMapping("batchAddBlogNews")
    public Object batchAddBlogNews(@RequestBody Map<String, Object> data) throws ParseException {
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

        //构建条件
        QueryWrapper<SourceApp> sourceAppQueryWrapper;
        //构建条件
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

        //构建条件
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

    //查询(列表数据)
    @GetMapping("search/{current}/{limit}")
    public ReponseCode doSearch(@PathVariable long current,
                                @PathVariable long limit) {
        Page<BlogNews> blogNewsPage = new Page<>(current, limit);
        blogNewsService.page(blogNewsPage, null);

        Map<String, Object> pageMap = new HashMap();

        //总记录数
        pageMap.put("total", blogNewsPage.getTotal());
        //每页显示记录数
        pageMap.put("size", blogNewsPage.getSize());
        //当前页
        pageMap.put("current", blogNewsPage.getCurrent());
        //总页数
        pageMap.put("pages", blogNewsPage.getPages());
        //是否有上一页
        pageMap.put("hasprevious", blogNewsPage.hasPrevious());
        //是否有下一页
        pageMap.put("hasnext", blogNewsPage.hasNext());
        //记录数
        pageMap.put("blogNews", blogNewsPage.getRecords());

        return ReponseCode.ok().data("blogNews", pageMap);
    }

    //查询(单条数据)
    @GetMapping("select/{id}")
    public ReponseCode doSelect(@PathVariable("id") int id) {

        BlogNews blogNews = blogNewsService.getById(id);
        return ReponseCode.ok().data("blogNews", blogNews);
    }

    //新增方法
    @PostMapping("insert")
    public ReponseCode doInsert(@RequestBody BlogNews blogNews) {

        if (blogNewsService.save(blogNews)) {
            return ReponseCode.ok().data("blogNews", blogNews);
        }
        else {
            return ReponseCode.error().data("blogNews", blogNews);
        }

    }

    //删除方法
    @DeleteMapping("delete/{id}")
    public ReponseCode doDelete(@PathVariable int id) {

        if (blogNewsService.removeById(id)) {
            return ReponseCode.ok();
        }
        else {
            return ReponseCode.error();
        }
    }

    //更新方法
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

