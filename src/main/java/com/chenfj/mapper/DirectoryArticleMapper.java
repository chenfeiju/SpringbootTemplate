package com.chenfj.mapper;

import com.chenfj.model.DirectoryArticle;

public interface DirectoryArticleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DirectoryArticle record);

    int insertSelective(DirectoryArticle record);

    DirectoryArticle selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DirectoryArticle record);

    int updateByPrimaryKeyWithBLOBs(DirectoryArticle record);

    int updateByPrimaryKey(DirectoryArticle record);
}