package com.demo.crossplatform.service.impl;

import com.alibaba.excel.EasyExcel;
import com.demo.crossplatform.entity.BlogNews;
import com.demo.crossplatform.entity.excel.BlogNewsExcel;
import com.demo.crossplatform.entity.excel.EventExcel;
import com.demo.crossplatform.listener.BlogNewsExcelListener;
import com.demo.crossplatform.listener.EventExcelListener;
import com.demo.crossplatform.mapper.BlogNewsMapper;
import com.demo.crossplatform.service.BlogNewsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

@Service
public class BlogNewsServiceImpl extends ServiceImpl<BlogNewsMapper, BlogNews> implements BlogNewsService {

    @Override
    public List<BlogNewsExcel> doBatchImport(MultipartFile file, BlogNewsService blogNewsService) {

        BlogNewsExcelListener excelListener = new BlogNewsExcelListener();

        try {
            InputStream in = file.getInputStream();
            EasyExcel.read(in, BlogNewsExcel.class, excelListener).sheet().doRead();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return excelListener.getBlogNewsList();
    }
}
