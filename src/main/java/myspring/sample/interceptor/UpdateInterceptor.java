package myspring.sample.interceptor;

import java.sql.Statement;
import java.util.Properties;
 
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

/*
 * @Signature은 인터셉트할 시점에 대한 설정인것 같다. 아래처럼 4종류가 있다.
 * Executor.class, ParameterHandler.class, ResultSetHandler.class, StatementHandler.class
 */
@Intercepts({@Signature(type=StatementHandler.class, method="update", args={Statement.class})})
public class UpdateInterceptor implements Interceptor{
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler handler = (StatementHandler)invocation.getTarget();
         
        // 쿼리
        String sql = handler.getBoundSql().getSql();
         
        // 쿼리실행시 맵핑되는 파라메터들
        String param = handler.getParameterHandler().getParameterObject()!=null ?
                             handler.getParameterHandler().getParameterObject().toString() : "";
         
        // DB에다 로그 insert
        /////////////////
        ////////////////
         
        return invocation.proceed();
    }
 
    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }
 
    @Override
    public void setProperties(Properties properties) {
    }
}
