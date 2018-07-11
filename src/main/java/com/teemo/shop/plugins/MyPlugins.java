package com.teemo.shop.plugins;

import com.teemo.shop.util.MyThreadLocal;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.*;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.Properties;


@Intercepts( {
        @Signature(method = "prepare", type = StatementHandler.class, args = {Connection.class,Integer.class}) })
public class MyPlugins implements Interceptor {
    private  final Logger LOGGER = LoggerFactory.getLogger(MyPlugins.class);


    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        //是通过Interceptor的plugin方法进行包裹的，所以我们这里拦截到的目标对象肯定是RoutingStatementHandler对象。
        RoutingStatementHandler handler = (RoutingStatementHandler) invocation.getTarget();
        //通过反射获取到当前RoutingStatementHandler对象的delegate属性
        StatementHandler delegate = (StatementHandler) ReflectUtil.getFieldValue(handler, "delegate");
        //获取到当前StatementHandler的 boundSql，这里不管是调用handler.getBoundSql()还是直接调用delegate.getBoundSql()结果是一样的，因为之前已经说过了
        //RoutingStatementHandler实现的所有StatementHandler接口方法里面都是调用的delegate对应的方法。
        BoundSql boundSql = delegate.getBoundSql();
        //拿到当前绑定Sql的参数对象，就是我们在调用对应的Mapper映射语句时所传入的参数对象
        Object obj = boundSql.getParameterObject();
        //这里通过一些规则来判断 是否需要执行 数据权限划分。
       // LOGGER.info("obj class {}",obj.getClass().getName());
   /*     if (obj instanceof Page<?>) {
            Page<?> page = (Page<?>) obj;
            //通过反射获取delegate父类BaseStatementHandler的mappedStatement属性
            MappedStatement mappedStatement = (MappedStatement)ReflectUtil.getFieldValue(delegate, "mappedStatement");
            //拦截到的prepare方法参数是一个Connection对象
            Connection connection = (Connection)invocation.getArgs()[0];
            //获取当前要执行的Sql语句，也就是我们直接在Mapper映射语句中写的Sql语句
            String sql = boundSql.getSql();
            //给当前的page参数对象设置总记录数
            this.setTotalRecord(page,
                    mappedStatement, connection);
            //获取分页Sql语句
            String pageSql = this.getPageSql(page, sql);
            //利用反射设置当前BoundSql对应的sql属性为我们建立好的分页Sql语句
            ReflectUtil.setFieldValue(boundSql, "sql", pageSql);
        }*/
        SecurityUtils.getSubject();
       /* System.out.println(MyThreadLocal.get()+"========");*/
        String sql = boundSql.getSql();
      /*  String permSql =sql+" WHERE username=2";
        ReflectUtil.setFieldValue(boundSql, "sql", permSql);*/
        return invocation.proceed();

    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target,this);
    }

    @Override
    public void setProperties(Properties properties) {
        LOGGER.info("setProperties has run");
    }

    /**
     * 利用反射进行操作的一个工具类
     *
     */
    private static class ReflectUtil {
        /**
         * 利用反射获取指定对象的指定属性
         * @param obj 目标对象
         * @param fieldName 目标属性
         * @return 目标属性的值
         */
        public static Object getFieldValue(Object obj, String fieldName) {
            Object result = null;
            Field field = ReflectUtil.getField(obj, fieldName);
            if (field != null) {
                field.setAccessible(true);
                try {
                    result = field.get(obj);
                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            return result;
        }

        /**
         * 利用反射获取指定对象里面的指定属性
         * @param obj 目标对象
         * @param fieldName 目标属性
         * @return 目标字段
         */
        private static Field getField(Object obj, String fieldName) {
            Field field = null;
            for (Class<?> clazz=obj.getClass(); clazz != Object.class; clazz=clazz.getSuperclass()) {
                try {
                    field = clazz.getDeclaredField(fieldName);
                    break;
                } catch (NoSuchFieldException e) {
                    //这里不用做处理，子类没有该字段可能对应的父类有，都没有就返回null。
                }
            }
            return field;
        }

        /**
         * 利用反射设置指定对象的指定属性为指定的值
         * @param obj 目标对象
         * @param fieldName 目标属性
         * @param fieldValue 目标值
         */
        public static void setFieldValue(Object obj, String fieldName,
                                         String fieldValue) {
            Field field = ReflectUtil.getField(obj, fieldName);
            if (field != null) {
                try {
                    field.setAccessible(true);
                    field.set(obj, fieldValue);
                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
}
