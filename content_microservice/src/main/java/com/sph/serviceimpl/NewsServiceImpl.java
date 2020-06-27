package com.sph.serviceimpl;


import com.sph.bean.WebResponse;
import com.sph.entity.News;
import com.sph.service.NewsService;
import com.sph.svc.NewsSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NewsServiceImpl implements NewsSvc {

    @Autowired
    private NewsService newsService;

    @Override
    public WebResponse<List<News>> queryAllNews() {
        List<News> list = newsService.selectAll();
        return WebResponse.suc(list);
    }

    @Override
    public WebResponse<News> queryNews(@RequestParam("id") Integer id) {
        if(null == id){
            return WebResponse.fai("99998","id is null");
        }
        News news = newsService.selectByNewsId(id);
        return  WebResponse.suc(news);
    }

    @Override
    public WebResponse addNews(@RequestBody News news) {
        newsService.addNews(news);
        return WebResponse.create().success();
    }

    @Override
    public WebResponse updateNews(@RequestBody News news) {
        newsService.updateNewsByNewsId(news);
        return WebResponse.create().success();
    }

    @Override
    public WebResponse deleteNews(@RequestParam("id") Integer id) {
        if(null == id){
            return WebResponse.fai("99998","id is null");
        }
        newsService.deleteNewsByNewsId(id);
        return WebResponse.create().success();
    }
}
