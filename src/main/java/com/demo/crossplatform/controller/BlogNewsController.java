package com.demo.crossplatform.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.crossplatform.commonutils.ReponseCode;
import com.demo.crossplatform.entity.BlogNews;
import com.demo.crossplatform.service.BlogNewsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
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
    private BlogNewsService blogNewsService;

    @RequestMapping("toUpload")
    public Object toUpload(){
        return new ModelAndView("blogNews/blogNewsUpload");
    }

    @RequestMapping("uploadFile")
    public Object uploadFile(){
        //上传文件，处理文件，返回数据
        List<Map<String,Object>> blogList=new ArrayList<>();
        blogList.add(JSONObject.parseObject("{id:\"1\",sourceApp_name:\"pingtai1\",create_time:\"2021-3-31\",user_name:\"张三\",content:\"内容1\"}"));
        blogList.add(JSONObject.parseObject("{id:\"2\",sourceApp_name:\"pingtai2\",create_time:\"2021-2-2\",user_name:\"里斯\",content:\"内容2\"}"));
        blogList.add(JSONObject.parseObject("{id:\"3\",sourceApp_name:\"pingtai3\",create_time:\"2021-1-31\",user_name:\"撒旦\",content:\"内容3\"}"));
        blogList.add(JSONObject.parseObject("{id:\"4\",sourceApp_name:\"pingtai4\",create_time:\"2021-1-20\",user_name:\"迪迦\",content:\"内容4\"}"));
        return ReponseCode.ok().data("blogList",blogList);
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

