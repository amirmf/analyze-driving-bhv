package com.taraan.dum.mappers;

import com.taraan.dum.common.DateUtils;
import com.taraan.dum.dto.news.NewsDto;
import com.taraan.dum.model.hibernate.News;

import org.springframework.util.SerializationUtils;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class NewsMapper {
    public static NewsDto getDto(News news, String newsPath) {
        NewsDto newsDto = new NewsDto();
        newsDto.setId(news.getId());
        newsDto.setDetailed(news.getDetailed());
        if (news.getDateRange().getFrom() != null)
            newsDto.setFromDate(DateUtils.getDate(news.getDateRange().getFrom()));
        if (news.getDateRange().getTo() != null)
            newsDto.setToDate(DateUtils.getDate(news.getDateRange().getTo()));
        newsDto.setTitle(news.getTitle());
        newsDto.setImage(newsPath + news.getNewsImage());
        return newsDto;
    }

    public static List<NewsDto> getDtos(List<News> newses, String newsPath) {
        List<NewsDto> result = new ArrayList<>();
        for (News news : newses) {
            result.add(getDto(news, newsPath));
        }
        return result;
    }

}
