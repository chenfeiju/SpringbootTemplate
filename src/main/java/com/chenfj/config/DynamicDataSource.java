package com.chenfj.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.Map;

/**
 * @Auther: feiju.chen
 * @Date: 2022/12/6 17:50
 * @Description:
 * @version: 1.0
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    /**
     * 决定使用哪个数据源之前 需要把多个数据源的信息以及默认数据源的信息配置好
     * @param defaultTargetDataSource 默认数据源
     * @param targetDataSources 目标数据源
     */
    public DynamicDataSource(DataSource defaultTargetDataSource, Map<Object, Object> targetDataSources) {
        super.setDefaultTargetDataSource(defaultTargetDataSource);
        super.setTargetDataSources(targetDataSources);
        super.afterPropertiesSet();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        // 使用DynamicDataSourceHolder保证线程安全，并且得到当前线程中的数据源key
        DynamicDataSourceGlobal dataSourceKey = DynamicDataSourceHolder.getDataSource();
        if(dataSourceKey == null || dataSourceKey == DynamicDataSourceGlobal.WRITE){
            return DynamicDataSourceGlobal.WRITE.name();
        }

        return DynamicDataSourceGlobal.READ.name();
    }
}
