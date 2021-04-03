package com.demo.crossplatform.service.impl;

import com.demo.crossplatform.entity.BlogNews;
import com.demo.crossplatform.mapper.BlogNewsMapper;
import com.demo.crossplatform.service.BlogNewsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author administrator
 * @since 2021-04-02
 */
@Service
public class BlogNewsServiceImpl extends ServiceImpl<BlogNewsMapper, BlogNews> implements BlogNewsService {

}
