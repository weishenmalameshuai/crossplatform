package com.demo.crossplatform.service;

import com.demo.crossplatform.entity.BlogNews;
import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.crossplatform.entity.excel.BlogNewsExcel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author administrator
 * @since 2021-04-02
 */
public interface BlogNewsService extends IService<BlogNews> {

    List<BlogNewsExcel> doBatchImport(MultipartFile file, BlogNewsService blogNewsService);
}
