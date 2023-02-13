package com.chenfj.mapper;

import com.chenfj.model.ColumnDirectory;

public interface ColumnDirectoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ColumnDirectory record);

    int insertSelective(ColumnDirectory record);

    ColumnDirectory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ColumnDirectory record);

    int updateByPrimaryKey(ColumnDirectory record);
}