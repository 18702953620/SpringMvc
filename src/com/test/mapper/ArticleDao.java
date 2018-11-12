package com.test.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.test.model.ArticleModel;

@Mapper
public interface ArticleDao {

	public List<ArticleModel> getArticle(@Param("articleId") String id);

	public int delete(String id);

	public int add(ArticleModel entity);

	public int update(ArticleModel entity);

}
