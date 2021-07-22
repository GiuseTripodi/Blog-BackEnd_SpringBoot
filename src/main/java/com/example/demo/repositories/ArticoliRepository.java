package com.example.demo.repositories;

import com.example.demo.entities.BlogArticles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticoliRepository extends JpaRepository<BlogArticles, Integer> {



    /**
     *
     * @param ID
     * @return The article with ID = ID.
     */
    BlogArticles findById(String ID);

    /**
     *
     * @return the article ordered by number of like
     */
    List<BlogArticles> findByOrderByNumLikeDesc();


    /**
     *
     * @return the article ordered by Date
     */
    List<BlogArticles> findByOrderByTimeDesc();


    boolean existsBlogArticlesByTitleAndAuthorAndText(String title, String author, String text);

    @Query("SELECT b FROM BlogArticles b inner join CategoryIndex c " +
            "on " +
            "c.a0=b.id or c.a1=b.id or c.a2=b.id or c.a3=b.id or " +
            "c.a4=b.id or c.a5=b.id or  c.a6=b.id or c.a7=b.id or  " +
            "c.a8=b.id or c.a9=b.id or  c.a10=b.id or c.a11=b.id  " +
            "where c.category=:categoria ")
    List<BlogArticles> getArticolidiCategoria(@Param("categoria") String categoria);

    //inserirsco un metodo di prova per fare un test
    List<BlogArticles> findAll();



    BlogArticles getBlogArticlesById(Long id);


    boolean existsById(Long id);


}
