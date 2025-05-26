package ru.taskalchemist.supportWebapp.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.taskalchemist.supportWebapp.models.NewsItem;

import java.util.List;

@Repository
public interface NewsItemRepository extends JpaRepository<NewsItem, Integer> {

    List<NewsItem> findAll(Sort sort);
}
