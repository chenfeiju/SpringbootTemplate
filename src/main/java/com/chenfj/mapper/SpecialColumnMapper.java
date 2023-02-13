package com.chenfj.mapper;

import com.chenfj.model.SpecialColumn;

public interface SpecialColumnMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SpecialColumn record);

    int insertSelective(SpecialColumn record);

    SpecialColumn selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SpecialColumn record);

    int updateByPrimaryKey(SpecialColumn record);
}