package ru.taskalchemist.supportWebapp.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.taskalchemist.supportWebapp.models.NewsItem;
import ru.taskalchemist.supportWebapp.security.MyUserDetails;
import ru.taskalchemist.supportWebapp.services.NewsItemService;

@Controller
@RequestMapping("/news")
public class NewsController {

    private final NewsItemService newsItemService;

    @Autowired
    public NewsController(NewsItemService newsItemService) {
        this.newsItemService = newsItemService;
    }

    @GetMapping()
    public String allNews(@AuthenticationPrincipal MyUserDetails userDetails, Model model) {
        model.addAttribute("user", userDetails);
        model.addAttribute("isAdmin", userDetails.getAuthorities().stream().iterator().next().getAuthority().equals("ROLE_ADMIN"));
        model.addAttribute("news", newsItemService.findAll("date"));
        return "news/news";
    }

    @GetMapping("/{id}")
    public String showNewsItem(@PathVariable int id, Model model, HttpServletRequest request) {
        model.addAttribute("user", request.getUserPrincipal());
        model.addAttribute("newsItem", newsItemService.findById(id));
        return "news/showNewsContent";
    }

    @GetMapping("/{id}/edit")
    public String editNewsItem(@PathVariable int id, Model model, HttpServletRequest request) {
        model.addAttribute("newsItem", newsItemService.findById(id));
        model.addAttribute("user", request.getUserPrincipal());
        return "news/editNewsItem";
    }

    @GetMapping("/new")
    public String showNewsItemForm(Model model, HttpServletRequest request) {
        model.addAttribute("newsItem", new NewsItem());
        model.addAttribute("user", request.getUserPrincipal());
        return "news/addNewsItem";
    }

    @PostMapping("/new")
    public String addNewsItem(@ModelAttribute("newsItem") @Valid NewsItem newsItem, BindingResult bindingResult, HttpServletRequest request, Model model) {
        if (bindingResult.hasErrors()) {
            return "news/addNewsItem";
        }
        newsItemService.save(newsItem, request.getUserPrincipal().getName());
        return "redirect:/news";
    }

    @PatchMapping("/{id}/edit")
    public String editNewsItem(@PathVariable int id, @ModelAttribute("newsItem") @Valid NewsItem newsItem, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "news/editNewsItem";
        }
        NewsItem itemToEdit = newsItemService.findById(id);
        newsItemService.update(id, newsItem, itemToEdit.getAuthor(), itemToEdit.getDate());
        return "redirect:/news";
    }

    @DeleteMapping("/{id}/delete")
    public String deleteNewsItem(@PathVariable int id) {
        newsItemService.delete(id);
        return "redirect:/news";
    }
}
