package org.jt.tech_trekker.dto;

import java.util.List;

import lombok.Data;

@Data
public class HomePageResponse {
    // private String blogId;
    // private String title;

    List<BasicBlogInfo> recentBlogs;
    List<BasicBlogInfo> backendBlogs;
    List<BasicBlogInfo> frontendBlogs;
    List<BasicBlogInfo> databaseBlogs;

}
