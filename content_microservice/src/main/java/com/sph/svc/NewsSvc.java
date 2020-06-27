package com.sph.svc;

import com.sph.bean.WebResponse;
import com.sph.entity.News;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface NewsSvc {

    @RequestMapping(value = "/content/queryAllNews", method = RequestMethod.POST)
    public WebResponse<List<News>> queryAllNews();

    @RequestMapping(value = "/content/queryNews", method = RequestMethod.POST)
    public WebResponse<News> queryNews(@RequestParam("id") Integer id);

    @RequestMapping(value = "/content/addNews", method = RequestMethod.POST)
    public WebResponse addNews(@RequestBody News news);

    @RequestMapping(value = "/content/updateNews", method = RequestMethod.POST)
    public WebResponse updateNews(@RequestBody News news);

    @RequestMapping(value = "/content/deleteNews", method = RequestMethod.POST)
    public WebResponse deleteNews(@RequestParam("id") Integer id);
}
