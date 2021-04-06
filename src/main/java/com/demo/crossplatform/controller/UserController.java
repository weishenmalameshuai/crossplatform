package com.demo.crossplatform.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.crossplatform.commonutils.ReponseCode;
import com.demo.crossplatform.entity.User;
import com.demo.crossplatform.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
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
@RequestMapping("/crossplatform/user")
public class UserController {

    @Resource
    private UserService userService;

    //查询(列表数据)
    @GetMapping("search/{current}/{limit}")
    public ReponseCode doSearch(@PathVariable long current,
                                @PathVariable long limit) {

        Page<User> userPage = new Page<>(current, limit);
        userService.page(userPage, null);

        Map<String, Object> pageMap = new HashMap();

        //总记录数
        pageMap.put("total", userPage.getTotal());
        //每页显示记录数
        pageMap.put("size", userPage.getSize());
        //当前页
        pageMap.put("current", userPage.getCurrent());
        //总页数
        pageMap.put("pages", userPage.getPages());
        //是否有上一页
        pageMap.put("hasprevious", userPage.hasPrevious());
        //是否有下一页
        pageMap.put("hasnext", userPage.hasNext());
        //记录数
        pageMap.put("users", userPage.getRecords());

        return ReponseCode.ok().data("users", pageMap);
    }

    //查询(单条数据)
    @GetMapping("select/{id}")
    public ReponseCode doSelect(@PathVariable("id") int id) {

        User user = userService.getById(id);
        return ReponseCode.ok().data("user", user);
    }

    //新增方法
    @PostMapping("insert")
    public ReponseCode doInsert(@RequestBody User user) {

        if (userService.save(user)) {
            return ReponseCode.ok().data("user", user);
        }
        else {
            return ReponseCode.error().data("user", user);
        }

    }

    //删除方法
    @DeleteMapping("delete/{id}")
    public ReponseCode doDelete(@PathVariable int id) {

        if (userService.removeById(id)) {
            return ReponseCode.ok();
        }
        else {
            return ReponseCode.error();
        }
    }

    //更新方法
    @PostMapping("update")
    public ReponseCode doUpdate(@RequestBody User user) {

        if (userService.updateById(user)) {
            return ReponseCode.ok().data("user", user);
        }
        else {
            return ReponseCode.error().data("user", user);
        }

    }

}

