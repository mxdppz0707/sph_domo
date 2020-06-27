package com.sph.controller;

import com.sph.bean.WebResponse;
import com.sph.fegin.NewsFegin;
import com.sph.fegin.bean.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NewsController {

    @Autowired
    private NewsFegin newsFegin;

    /**
     * 添加新闻
     * @return
     */
    @PostMapping("/addNews")
    public WebResponse readNews(@RequestBody News news){
        WebResponse webResponse = newsFegin.addNews(news);
       if("10000".equals(webResponse.getCode())){
            return WebResponse.create().success();
        }else {
            return WebResponse.fai(webResponse.getCode(),webResponse.getDesc());
        }
    }

    /**
     * 编辑新闻
     * @return
     */
    @PostMapping("/updateNews")
    public WebResponse updateNews(@RequestBody News news){
        WebResponse webResponse = newsFegin.updateNews(news);
        if("10000".equals(webResponse.getCode())){
            return WebResponse.create().success();
        }else {
            return WebResponse.fai(webResponse.getCode(),webResponse.getDesc());
        }
    }

    /**
     * 搜索新闻列表
     * @return
     */
    @PostMapping("/searchNews")
    public WebResponse<List<News>> searchNews(){

        WebResponse<List<News>> webResponse = newsFegin.queryAllNews();
        if("10000".equals(webResponse.getCode())){
            return WebResponse.suc(webResponse.getData());
        }else {
            return WebResponse.fai(webResponse.getCode(),webResponse.getDesc());
        }

    }

    /**
     * 查看单条新闻
     * @param id
     * @return
     */
    @PostMapping("/readNews")
    public WebResponse<News> readNews(@RequestParam("id") Integer id){
        WebResponse<News> webResponse = newsFegin.queryNews(id);
        if("10000".equals(webResponse.getCode())){
            return WebResponse.suc(webResponse.getData());
        }else {
            return WebResponse.fai(webResponse.getCode(),webResponse.getDesc());
        }
    }
}
