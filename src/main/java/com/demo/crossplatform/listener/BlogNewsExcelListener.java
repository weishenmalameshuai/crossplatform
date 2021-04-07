package com.demo.crossplatform.listener;


import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.demo.crossplatform.entity.BlogNews;
import com.demo.crossplatform.entity.excel.BlogNewsExcel;

import java.util.ArrayList;
import java.util.List;


public class BlogNewsExcelListener extends AnalysisEventListener<BlogNewsExcel> {

    private List<BlogNewsExcel> blogNewsList = new ArrayList();

    public List<BlogNewsExcel> getBlogNewsList() {
        return blogNewsList;
    }

    @Override
    public void invoke(BlogNewsExcel blogNews, AnalysisContext analysisContext) {
        blogNewsList.add(blogNews);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
