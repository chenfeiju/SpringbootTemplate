package com.chenfj.handler;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * mybatis-config 加 typeHandler
 * @Auther: feiju.chen
 * @Date: 2022/12/8 10:45
 * @Description:
 * @version: 1.0
 */
public class ChenfjTypeHandler implements TypeHandler {

    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, Object o, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i,"加密的");
    }

    @Override
    public Object getResult(ResultSet resultSet, String s) throws SQLException {
        String haha = resultSet.getString(s);
        return haha+"解密";
    }

    @Override
    public Object getResult(ResultSet resultSet, int i) throws SQLException {
        return null;
    }

    @Override
    public Object getResult(CallableStatement callableStatement, int i) throws SQLException {
        return null;
    }
}
