package org.jt.tech_trekker.repository;

import java.util.List;

import org.jt.tech_trekker.model.Blog;
import org.springframework.data.domain.Pageable;
// import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.repository.query.Param;
import org.jt.tech_trekker.constant.BlogCategory;

public interface BlogRepository extends JpaRepository<Blog, String> {
    List<Blog> findTop5ByOrderByTitle();

    List<Blog> findByCategory(BlogCategory category);

    List<Blog> findByCategory(BlogCategory category, Pageable pageable);

    long countByCategory(BlogCategory category);

    List<Blog> findByCategoryAndTitleContaining(BlogCategory category, String title ,Pageable pageable);

    long countByCategoryAndTitleContaining(BlogCategory category, String title);

    // @Query(nativeQuery = true,
    // value = "SELECT * FROM blog_tab WHERE CATEGORY = ? LIMIT ?") //for sql query

    // @Query("SELECT FROM Blog B WHERE B.category=? LIMIT ? ") //jpql query

    // @Query("SELECT B FROM Blog B WHERE B.category=:ct LIMIT :lm ")
    // List<Blog> getBlogsByCategoryWithLimit( @Param("ct") BlogCategory category,
    // @Param("lm") int limit);

}
