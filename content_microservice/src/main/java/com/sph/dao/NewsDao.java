package com.sph.dao;

import com.sph.entity.News;
import com.sph.entity.NewsExample;
import com.sph.mapper.NewsMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class NewsDao {

    @Resource
    private NewsMapper newsMapper;

    public List<News> selectAll(){
        NewsExample example = new NewsExample();
        List<News> list = newsMapper.selectByExampleWithBLOBs(example);

        return list;
    }

    public News selectByNewsId(Integer id){
        NewsExample example = new NewsExample();
        News news = newsMapper.selectByPrimaryKey(id);

        return news;
    }

    public void addNews(News news){
        newsMapper.insert(news);
    }

    public void updateNewsByNewsId(News news){
        newsMapper.updateByPrimaryKeyWithBLOBs(news);
    }

    public void deleteNewsByNewsId(Integer id){
        newsMapper.deleteByPrimaryKey(id);
    }
}
