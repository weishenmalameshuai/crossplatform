package com.demo.crossplatform.controller;


import com.demo.crossplatform.commonutils.ReponseCode;
import com.demo.crossplatform.entity.BlogNews;
import com.demo.crossplatform.service.BlogNewsService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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

    //查询(列表数据)
    @GetMapping("search")
    public ReponseCode doSearch() {
        List<BlogNews> blogNewses = blogNewsService.list(null);
        return ReponseCode.ok().data("blogNews", blogNewses);
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

