package com.chenfj.config;

/**
 * 使用ThreadLocal 技术来记录当前线程中的数据源的key
 * @Auther: feiju.chen
 * @Date: 2022/12/7 10:08
 * @Description:
 * @version: 1.0
 */
public final class DynamicDataSourceHolder {

    private static final ThreadLocal<DynamicDataSourceGlobal> holder = new ThreadLocal();

    private DynamicDataSourceHolder(){

    }

    public static void putDataSource(DynamicDataSourceGlobal dynamicDataSourceGlobal){
        holder.set(dynamicDataSourceGlobal);
    }

    public static DynamicDataSourceGlobal getDataSource(){
        return holder.get();
    }

    public static void clearDataSource(){
        holder.remove();
    }





}
