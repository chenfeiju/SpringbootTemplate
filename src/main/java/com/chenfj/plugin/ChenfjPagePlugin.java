package com.chenfj.plugin;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;

import java.sql.Connection;
import java.util.Properties;

/**
 * 最后在mybatis.xml 的 configuration·中 加plugins标签中加
 * @Auther: feiju.chen
 * @Date: 2022/12/8 10:12
 * @Description:
 * @version: 1.0
 */

@Intercepts(@Signature(type = StatementHandler.class,method = "prepare",args = {Connection.class,Integer.class}))
public class ChenfjPagePlugin implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        // 执行流程交给mybatis
        return invocation.proceed();
    }


    @Override
    public void setProperties(Properties properties) {
        // properties 是 xml 中设置的 property的值
        Interceptor.super.setProperties(properties);
    }
}
