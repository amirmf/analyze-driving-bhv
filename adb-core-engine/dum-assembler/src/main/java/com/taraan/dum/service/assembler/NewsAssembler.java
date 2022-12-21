package com.taraan.dum.service.assembler;

import com.taraan.dum.dto.news.NewsDto;
import com.taraan.dum.dto.news.NewsPage;
import com.taraan.dum.logic.NewsLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.io.IOException;

@Component
@Transactional
public class NewsAssembler {

    @Autowired
    private NewsLogic newsLogic;

    public NewsDto createNews(NewsDto newsDto) throws IOException {
        return newsLogic.createNews(newsDto);
    }

    public NewsDto getNews(Long id) {
        return newsLogic.getNews(id);
    }

    public void removeNews(Long id) {
        newsLogic.removeNews(id);
    }

    public NewsPage getNewses(Long page) {
        long from = page * 10;
        long size = from + 10;
        return newsLogic.getNewses(from, size);
    }

    public NewsPage getNewses(boolean all, Long from, Long size) {
        return newsLogic.getNewses(all,from, size);

    }
}
