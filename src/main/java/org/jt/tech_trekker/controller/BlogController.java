package org.jt.tech_trekker.controller;

import static org.jt.tech_trekker.mapper.BlogMapper.*;

import java.util.List;

import org.jt.tech_trekker.constant.BlogCategory;
import org.jt.tech_trekker.dto.HomePageResponse;
import org.jt.tech_trekker.dto.ViewAllResponse;
import org.jt.tech_trekker.dto.WriterRequest;
import org.jt.tech_trekker.mapper.BlogMapper;
import org.jt.tech_trekker.mapper.WriterMapper;
import org.jt.tech_trekker.model.Blog;
import org.jt.tech_trekker.service.TechTrekkerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/tech-trekker")
public class BlogController {
    private final TechTrekkerService service;

    @GetMapping("/home")
    public String home(Model model) {

        // List<Blog> blogs = service.getBlogs();

        // // declarative approach
        // // List<HomePageResponse> details =
        // blogs.stream().map(BlogMapper::convertBlogToHome).toList();
        // // both methods are same.

        // List<HomePageResponse> details = new ArrayList<>();
        // for (Blog blog : blogs) {
        // details.add(BlogMapper.convertBlogToHome(blog));
        // }

        // model.addAttribute("blogs", details );

        var recentBlogs = convertBlogToBasic(service.getTop5Blogs());
        var frontendBlogs = convertBlogToBasic(service.limitedBlogOfCategory(BlogCategory.FRONTEND, 8));
        var backendBlogs = convertBlogToBasic(service.limitedBlogOfCategory(BlogCategory.BACKEND, 6));
        var databaseBlogs = convertBlogToBasic(service.limitedBlogOfCategory(BlogCategory.DATABASE, 6));

        var homePageResponse = new HomePageResponse();
        homePageResponse.setRecentBlogs(recentBlogs);
        homePageResponse.setFrontendBlogs(frontendBlogs);
        homePageResponse.setBackendBlogs(backendBlogs);
        homePageResponse.setDatabaseBlogs(databaseBlogs);

        model.addAttribute("response", homePageResponse);

        return "home";
    }

    @GetMapping("/blog-details")
    public String blogDetails(@RequestParam String id, Model model) {
        var blog = service.getBlogById(id);
        model.addAttribute("blog", BlogMapper.convertBlogToResponse(blog));
        return "blog-details";
    }

    @GetMapping("/view-all")
    public String viewAll(@RequestParam BlogCategory category,
            @RequestParam(required = false, defaultValue = "1") int pageNum,
            @RequestParam(required = false) String searchTerm,
            Model model) {

        final int limit = 10;
        List<Blog> blogs;
        int totalBlogs;

        if (searchTerm == null) {
            blogs = service.limitedBlogOfCategory(category, pageNum, limit);
            totalBlogs = service.countBlogs(category);
        } else {
            blogs = service.blogOfCategoryAndTitle(category, searchTerm, pageNum, limit);
            totalBlogs = service.countBlogsOfTitle(category, searchTerm);
        }

        var blogResponse = blogs.stream().map(BlogMapper::convertBlogToResponse).toList();

        // int totalBlogs = service.countBlogs(category);

        var viewAllResponse = new ViewAllResponse();
        viewAllResponse.setBlogs(blogResponse);
        viewAllResponse.setTotalPage(getTotalPage(totalBlogs, limit));
        viewAllResponse.setCurrentPage(pageNum);
        viewAllResponse.setSearchTerm(searchTerm);
        model.addAttribute("response", viewAllResponse);

        return "view-all";
    }

    @PostMapping("/view-all-search")
    public String viewAllSearch(@RequestParam BlogCategory category, @RequestParam String searchTerm) {
        if (searchTerm == null || searchTerm.isEmpty() || searchTerm.isBlank()) {
            return "redirect:/tech-trekker/view-all?category=" + category;
        }

        return "redirect:/tech-trekker/view-all?category=" + category + "&searchTerm=" + searchTerm;
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute WriterRequest request) {
        var writerInfo = WriterMapper.convertRequest(request);
        service.createWriter(writerInfo, request.profilePicture());
        return "redirect:/tech-trekker/home";
    }

    private int getTotalPage(int totalBlogs, int limit) {
        return (totalBlogs % limit == 0)
                ? (totalBlogs / limit)
                : (totalBlogs / limit) + 1;
    }

}
