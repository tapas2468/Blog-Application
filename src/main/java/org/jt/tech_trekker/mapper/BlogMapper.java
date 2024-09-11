package org.jt.tech_trekker.mapper;


import java.util.ArrayList;
import java.util.List;

import org.jt.tech_trekker.dto.BasicBlogInfo;
import org.jt.tech_trekker.dto.BlogRequest;
import org.jt.tech_trekker.dto.BlogResponse;
// import org.jt.tech_trekker.dto.HomePageResponse;
import org.jt.tech_trekker.model.Blog;
import org.springframework.beans.BeanUtils;

public class BlogMapper {
    public static Blog convertRequest(BlogRequest request) {
        var blog = new Blog();
        BeanUtils.copyProperties(request, blog);

        return blog;
    }

    public static BlogResponse convertBlogToResponse(Blog blog) {
        var response = new BlogResponse();
        BeanUtils.copyProperties(blog, response);
        return response;
    }

    public static List<BasicBlogInfo> convertBlogToBasic(List<Blog> blogs) {
        // var homePageResponse = new HomePageResponse();
        // BeanUtils.copyProperties(blog, homePageResponse);
        // return homePageResponse;

        var list = new ArrayList<BasicBlogInfo>();

        for ( Blog blog : blogs) {
            var basicBlogInfo = new BasicBlogInfo();
            BeanUtils.copyProperties(blog, basicBlogInfo);
            list.add(basicBlogInfo);
        }

        return list;
    }
}
