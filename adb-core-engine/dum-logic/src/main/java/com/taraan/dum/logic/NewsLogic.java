package com.taraan.dum.logic;

import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import com.taraan.dum.common.DateUtils;
import com.taraan.dum.common.FileTypesUtils;
import com.taraan.dum.da.NewsDa;
import com.taraan.dum.dto.news.NewsDto;
import com.taraan.dum.dto.news.NewsPage;
import com.taraan.dum.exceptions.EntityDoesNotExistException;
import com.taraan.dum.mappers.NewsMapper;
import com.taraan.dum.model.hibernate.DateRange;
import com.taraan.dum.model.hibernate.News;
import org.apache.tika.Tika;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Component
public class NewsLogic {
    @Autowired
    private NewsDa newsDa;
    @Value("${newsUrl}")
    private String newsUrl;
    @Value("${newsPath}")
    private String newsPath;
    @Value("${newsRss}")
    private String newsRss;

    public NewsDto createNews(NewsDto newsDto) throws IOException {
        News news = new News();
        news.setDetailed(newsDto.getDetailed());
        news.setTitle(newsDto.getTitle());
        news.setRegisterDate(new Date());
        DateRange dateRange = new DateRange(DateUtils.getDate(newsDto.getFromDate()));
        news.setDateRange(dateRange);
        news = newsDa.save(news);
        if (news.getNewsImage() != null && !news.getNewsImage().isEmpty()) {
            byte[] decodedImg = Base64.getDecoder().decode(news.getNewsImage().getBytes(StandardCharsets.UTF_8));
            final String newsImage = news.getId() + "." + FileTypesUtils.checkType(decodedImg);
            Path destinationFile = Paths.get(newsPath, newsImage);
            Files.write(destinationFile, decodedImg);
            news.setNewsImage(newsImage);
            news = newsDa.update(news);
        }
        return NewsMapper.getDto(news, newsUrl);
    }

    public NewsDto getNews(Long id) {
        News news = newsDa.get(id);
        if (news == null)
            return null;
        return NewsMapper.getDto(news, newsUrl);
    }

    public void removeNews(Long id) {
        News news = newsDa.get(id);
        if (news == null)
            throw new EntityDoesNotExistException();
        news.getDateRange().setTo(new Date());
        newsDa.update(news);
    }

    public NewsPage getNewses(Long from, Long size) {
        String url = this.newsRss;
        SyndFeed feed = null;
        try {
            feed = new SyndFeedInput().build(new XmlReader(new URL(url)));
        } catch (Exception e) {
            return new NewsPage(Collections.emptyList(), 0L);
        }
        final List<SyndEntry> entries = feed.getEntries();
        Double count = Math.ceil(entries.size() / 10d);

        if (from >= entries.size())
            return new NewsPage(Collections.emptyList(), count.longValue());
        if (from + size > entries.size())
            size = (long) entries.size();
        List<NewsDto> newses = new ArrayList<>();
        for (SyndEntry entry : entries.subList(from.intValue(), size.intValue())) {
            final NewsDto e = new NewsDto();
            final List<SyndContent> contents = entry.getContents();
            e.setTitle(entry.getTitle());
            final Document parse = Jsoup.parse(entry.getDescription().getValue());
            final String img = parse.select("img").toString().replace("<img", "").replace("src=", "").replace(">", "").replace("\"", "");
            e.setImage(img.trim());
            e.setReference(entry.getLink());
            e.setDetailed(parse.select("p").toString().replace("<p>", "").replace("</p>", ""));
            newses.add(e);

        }
        return new NewsPage(newses, count.longValue());
        /*
        List<News> newses = newsDa.get(from, size);
        List<NewsDto> dtos = NewsMapper.getDtos(newses, newsUrl);
        Double count = Math.ceil(newsDa.getCount() / 10d);
        return new NewsPage(dtos, count.longValue());*/
    }

    public NewsPage getNewses(boolean all, Long from, Long size) {
        if (all)
            return getNewses(from, size);
        List<News> newses = newsDa.getActiveNews(from, size);
        List<NewsDto> dtos = NewsMapper.getDtos(newses, newsUrl);
        return new NewsPage(dtos, newsDa.getActiveNewsCount());
    }

}
