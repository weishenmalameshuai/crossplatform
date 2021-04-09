package com.demo.crossplatform.service;

import com.demo.crossplatform.entity.BlogNews;
import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.crossplatform.entity.excel.BlogNewsExcel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BlogNewsService extends IService<BlogNews> {

    List<BlogNewsExcel> doBatchImport(MultipartFile file, BlogNewsService blogNewsService);
}
