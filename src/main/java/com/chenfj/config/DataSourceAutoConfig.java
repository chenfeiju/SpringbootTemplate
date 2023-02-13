package com.chenfj.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.chenfj.config.properties.JdbcProperties;
import com.chenfj.plugin.DynamicPlugin;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: feiju.chen
 * @Date: 2022/12/2 21:29
 * @Description:
 * @version: 1.0
 */
@Configuration
public class DataSourceAutoConfig {

    @Autowired
    private JdbcProperties jdbcProperties;

    @Bean(name = "mapper/db_1")
    public DataSource dataSource(JdbcProperties jdbcProperties){
        //DruidDataSourceBuilder.create().build();
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(jdbcProperties.getDriverClass());
        dataSource.setUrl(jdbcProperties.getUrl());
        dataSource.setUsername(jdbcProperties.getUsername());
        dataSource.setPassword(jdbcProperties.getPassword());
        dataSource.setMaxActive(jdbcProperties.getMaxActive());
        dataSource.setInitialSize(jdbcProperties.getInitialSize());
        dataSource.setMinIdle(jdbcProperties.getMinIdle());
        dataSource.setMaxWait(jdbcProperties.getMaxWait());

        return dataSource;
    }

    @Bean(name = "db_2")
    public DataSource dataSource2(JdbcProperties jdbcProperties){
        //DruidDataSourceBuilder.create().build();
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(jdbcProperties.getDriverClass());
        dataSource.setUrl(jdbcProperties.getUrl());
        dataSource.setUsername(jdbcProperties.getUsername());
        dataSource.setPassword(jdbcProperties.getPassword());
        dataSource.setMaxActive(jdbcProperties.getMaxActive());
        dataSource.setInitialSize(jdbcProperties.getInitialSize());
        dataSource.setMinIdle(jdbcProperties.getMinIdle());
        dataSource.setMaxWait(jdbcProperties.getMaxWait());

        return dataSource;
    }

    @Primary
    @Bean(name="dynamicDataSource")
    public DynamicDataSource dynamicDataSource(JdbcProperties jdbcProperties){
        Map<Object,Object> targetDataSources = new HashMap<>(5);
        targetDataSources.put(DynamicDataSourceGlobal.READ.name(),dataSource(jdbcProperties));
        targetDataSources.put(DynamicDataSourceGlobal.WRITE.name(),dataSource2(jdbcProperties));
        return new DynamicDataSource(dataSource(jdbcProperties),targetDataSources);
    }


    @Bean
    public SqlSessionFactory sqlSessionFactory(DynamicDataSource dynamicDataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dynamicDataSource);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources("classpath:mapper/db_1/*.xml");
        sqlSessionFactoryBean.setMapperLocations(resources);
        sqlSessionFactoryBean.setTypeAliasesPackage("com.chenfj.model");

        // 分页插件 mybatis-spring-boot-starter 自动引入了
        // sqlSessionFactoryBean.setPlugins(new PageInterceptor());

        // 读写分离插件
        sqlSessionFactoryBean.setPlugins(new DynamicPlugin());

        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBean.getObject();
        return sqlSessionFactory;
    }



    /**
     * sqlSessionTemplate 线程安全代替 defaultSqlSession
     * @param sqlSessionFactory
     * @return
     */
    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }


    /**
     * mapper 和 sql 结合 生成代理
     * 注意，没有必要去指定SqlSessionFactory或SqlSessionTemplate，因为MapperScannerConfigurer将会创建MapperFactoryBean，之后自动装配。但是，如果你使用了一个以上的DataSource(因此，也是多个的SqlSessionFactory),那么自动装配可能会失效。这种情况下，你可以使用sqlSessionFactory或sqlSessionTemplate属性来设置正确的工厂/模板。
     * @return
     */
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer(){
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("com.chenfj.mapper");
        mapperScannerConfigurer.setSqlSessionTemplateBeanName("sqlSessionTemplate");
        return mapperScannerConfigurer;

    }
}
