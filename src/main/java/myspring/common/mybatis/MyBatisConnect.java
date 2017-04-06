package myspring.common.mybatis;

import javax.sql.DataSource;

import org.apache.ibatis.session.ExecutorType;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
@MapperScan(basePackages = "com.mybatis.mapper", sqlSessionTemplateRef = "sqlSessionTemplate")
public class MyBatisConnect {
   @Autowired
   private DataSource dataSource;

   @Bean(name = "sqlSessionFactoryBean")
   public SqlSessionFactoryBean sqlSessionFactoryBean() throws Exception {
      SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
      sessionFactory.setDataSource(dataSource);

      sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/**/*.xml"));
      return sessionFactory;
   }

   @Bean(name = "defaultSqlSessionTemplate")
   @Primary
   public SqlSessionTemplate defaultSqlSessionTemplate() throws Exception {
      return new SqlSessionTemplate(sqlSessionFactoryBean().getObject());
   }

   @Bean(name = "batchSqlSessionTemplate")
   public SqlSessionTemplate batchSqlSessionTemplate() throws Exception {
      return new SqlSessionTemplate(sqlSessionFactoryBean().getObject(), ExecutorType.BATCH);  // ExecuteType 을 Batch 로 지정
   }
}