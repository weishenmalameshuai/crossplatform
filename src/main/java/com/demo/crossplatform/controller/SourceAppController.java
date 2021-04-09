package com.demo.crossplatform.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.crossplatform.commonutils.ReponseCode;
import com.demo.crossplatform.entity.SourceApp;
import com.demo.crossplatform.service.SourceAppService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/crossplatform/sourceApp")
public class SourceAppController {

    @Resource
    private SourceAppService sourceAppService;

    @GetMapping("search/{current}/{limit}")
    public ReponseCode doSearch(@PathVariable long current,
                                @PathVariable long limit) {

        Page<SourceApp> sourceAppPage = new Page<>(current, limit);
        sourceAppService.page(sourceAppPage, null);

        Map<String, Object> pageMap = new HashMap();

        pageMap.put("total", sourceAppPage.getTotal());
        pageMap.put("size", sourceAppPage.getSize());
        pageMap.put("current", sourceAppPage.getCurrent());
        pageMap.put("pages", sourceAppPage.getPages());
        pageMap.put("hasprevious", sourceAppPage.hasPrevious());
        pageMap.put("hasnext", sourceAppPage.hasNext());
        pageMap.put("sourceapps", sourceAppPage.getRecords());

        return ReponseCode.ok().data("sourceapps", pageMap);
    }

    @GetMapping("select/{id}")
    public ReponseCode doSelect(@PathVariable("id") int id) {

        SourceApp sourceApp = sourceAppService.getById(id);
        return ReponseCode.ok().data("sourceApp", sourceApp);
    }
    @PostMapping("insert")
    public ReponseCode doInsert(@RequestBody SourceApp sourceApp) {

        if (sourceAppService.save(sourceApp)) {
            return ReponseCode.ok().data("sourceApp", sourceApp);
        }
        else {
            return ReponseCode.error().data("sourceApp", sourceApp);
        }

    }

    @DeleteMapping("delete/{id}")
    public ReponseCode doDelete(@PathVariable int id) {

        if (sourceAppService.removeById(id)) {
            return ReponseCode.ok();
        }
        else {
            return ReponseCode.error();
        }
    }

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

