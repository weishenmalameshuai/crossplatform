package com.demo.crossplatform.controller;


import com.demo.crossplatform.commonutils.ReponseCode;
import com.demo.crossplatform.entity.BlogNews;
import com.demo.crossplatform.entity.User;
import com.demo.crossplatform.service.UserService;
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
@RequestMapping("/crossplatform/user")
public class UserController {

    @Resource
    private UserService userService;

    //查询(列表数据)
    @GetMapping("search")
    public ReponseCode doSearch() {
        List<User> users = userService.list(null);
        return ReponseCode.ok().data("users", users);
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

