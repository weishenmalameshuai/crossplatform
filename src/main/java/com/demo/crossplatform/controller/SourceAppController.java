package com.demo.crossplatform.controller;


import com.demo.crossplatform.commonutils.ReponseCode;
import com.demo.crossplatform.entity.BlogNews;
import com.demo.crossplatform.entity.SourceApp;
import com.demo.crossplatform.service.SourceAppService;
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
@RequestMapping("/crossplatform/sourceApp")
public class SourceAppController {

    @Resource
    private SourceAppService sourceAppService;

    //查询(列表数据)
    @GetMapping("search")
    public ReponseCode doSearch() {
        List<SourceApp> sourceApps = sourceAppService.list(null);
        return ReponseCode.ok().data("sourceApps", sourceApps);
    }

    //查询(单条数据)
    @GetMapping("select/{id}")
    public ReponseCode doSelect(@PathVariable("id") int id) {

        SourceApp sourceApp = sourceAppService.getById(id);
        return ReponseCode.ok().data("sourceApp", sourceApp);
    }

    //新增方法
    @PostMapping("insert")
    public ReponseCode doInsert(@RequestBody SourceApp sourceApp) {

        if (sourceAppService.save(sourceApp)) {
            return ReponseCode.ok().data("sourceApp", sourceApp);
        }
        else {
            return ReponseCode.error().data("sourceApp", sourceApp);
        }

    }

    //删除方法
    @DeleteMapping("delete/{id}")
    public ReponseCode doDelete(@PathVariable int id) {

        if (sourceAppService.removeById(id)) {
            return ReponseCode.ok();
        }
        else {
            return ReponseCode.error();
        }
    }

    //更新方法
    @PostMapping("update")
    public ReponseCode doUpdate(@RequestBody SourceApp sourceApp) {

        if (sourceAppService.updateById(sourceApp)) {
            return ReponseCode.ok().data("sourceApp", sourceApp);
        }
        else {
            return ReponseCode.error().data("sourceApp", sourceApp);
        }

    }
}

