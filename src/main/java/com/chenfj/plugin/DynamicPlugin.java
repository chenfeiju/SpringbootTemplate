package com.chenfj.plugin;

import com.chenfj.config.DynamicDataSourceGlobal;
import com.chenfj.config.DynamicDataSourceHolder;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.keygen.SelectKeyGenerator;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Mybatis插件，通过拦截Executor
 * 
 * @author allen
 */
@Intercepts({// mybatis 执行流程
	@Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }),
	@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
	@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class})
})
public class DynamicPlugin implements Interceptor {
	private Logger log = LoggerFactory.getLogger(DynamicPlugin.class);

	private static final Map<String, DynamicDataSourceGlobal> cacheMap = new ConcurrentHashMap<>();

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		Object[] objects = invocation.getArgs();
		MappedStatement ms = (MappedStatement) objects[0];

		DynamicDataSourceGlobal dynamicDataSourceGlobal = null;

		if ((dynamicDataSourceGlobal = cacheMap.get(ms.getId())) == null) {
			// 读方法
			if (ms.getSqlCommandType().equals(SqlCommandType.SELECT)) { // select * from user;    update insert
				// !selectKey 为自增id查询主键(SELECT LAST_INSERT_ID() )方法，使用主库
				if (ms.getId().contains(SelectKeyGenerator.SELECT_KEY_SUFFIX)) {
					dynamicDataSourceGlobal = DynamicDataSourceGlobal.WRITE;
				} else {
					// 负载均衡，针对多个读库
					dynamicDataSourceGlobal = DynamicDataSourceGlobal.READ;
				}
			} else {
				// 写方法
				dynamicDataSourceGlobal = DynamicDataSourceGlobal.WRITE;
			}
			log.info("---------------------------------------");
			log.info("方法[{"+ms.getId()+"}] 使用了 [{"+dynamicDataSourceGlobal.name()+"}] 数据源, SqlCommandType [{"+ms.getSqlCommandType().name()+"}]..");
			//log.warn("设置方法[{}] use [{}] Strategy, SqlCommandType [{}]..", ms.getId(), dynamicDataSourceGlobal.name(), ms.getSqlCommandType().name());

			// 把id(方法名)和数据源存入map，下次命中后就直接执行
			cacheMap.put(ms.getId(), dynamicDataSourceGlobal);
		}
		// 设置当前线程使用的数据源
		DynamicDataSourceHolder.putDataSource(dynamicDataSourceGlobal);

		return invocation.proceed();
	}

	@Override
	public Object plugin(Object target) {
		if (target instanceof Executor) {
			return Plugin.wrap(target, this);
		} else {
			return target;
		}
	}

	@Override
	public void setProperties(Properties properties) {
		//
	}
}
