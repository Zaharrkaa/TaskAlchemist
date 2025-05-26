package ru.taskalchemist.supportWebapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.taskalchemist.supportWebapp.models.NewsItem;
import ru.taskalchemist.supportWebapp.repo.NewsItemRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class NewsItemService {
    private final NewsItemRepository newsItemRepository;

    @Autowired
    public NewsItemService(NewsItemRepository newsItemRepository) {
        this.newsItemRepository = newsItemRepository;
    }

    public List<NewsItem> findAll(String sortField)
    {
        return newsItemRepository.findAll(Sort.by(sortField));
    }

    public NewsItem findById(int id) {
        Optional<NewsItem> newsItem = newsItemRepository.findById(id);
        return newsItem.orElse(null);
    }

    @Transactional
    public void save(NewsItem newsItem, String author) {
        newsItem.setDate(LocalDate.now());
        newsItem.setAuthor(author);
        newsItemRepository.save(newsItem);
    }

    @Transactional
    public void update(int id, NewsItem newsItem, String author, LocalDate date){
        newsItem.setId(id);
        newsItem.setAuthor(author);
        newsItem.setDate(date);
        newsItemRepository.save(newsItem);
    }

    @Transactional
    public void delete(int id) {
        newsItemRepository.deleteById(id);
    }

}
