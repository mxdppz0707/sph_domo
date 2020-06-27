package com.sph.service;

import com.sph.dao.NewsDao;
import com.sph.entity.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsService {

    @Autowired
    private NewsDao newsDao;

    public List<News> selectAll(){
        return newsDao.selectAll();
    }

    public News selectByNewsId(Integer id){
        return newsDao.selectByNewsId(id);
    }

    public void addNews(News news){
        newsDao.addNews(news);
    }

    public void updateNewsByNewsId(News news){
        newsDao.updateNewsByNewsId(news);
    }

    public void deleteNewsByNewsId(Integer id){
        newsDao.deleteNewsByNewsId(id);
    }
}
