package com.lixl.demo.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.xa.DruidXADataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;

//不同的dao包名，指到不同的数据源。自动切换。
@Configuration
@MapperScan(basePackages = {"com.lixl.demo.dao2"}, sqlSessionFactoryRef = "sqlSessionFactory2")
public class CustomConfiguration2 {

    @Autowired
    private DruidDataSourceProperties druidDataSourceProperties;

    @Bean
    @ConfigurationProperties(prefix = "db2.datasource")
    public DataSourceProperties db2DataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "db2.datasource")
    public DataSource db2DataSource(@Qualifier("db2DataSourceProperties") DataSourceProperties dataSourceProperties) throws SQLException {
        DruidXADataSource druidXADataSource = new DruidXADataSource();
        InitDruidDataSource(druidXADataSource, dataSourceProperties);

        AtomikosDataSourceBean atomikosDataSource = new AtomikosDataSourceBean();
        atomikosDataSource.setUniqueResourceName("db2DataSource");
        atomikosDataSource.setXaDataSource(druidXADataSource);
        atomikosDataSource.setTestQuery("SELECT 1");
        return atomikosDataSource;
    }

    @Bean(name = "sqlSessionFactory2")
    public SqlSessionFactory sqlSessionFactory2(@Qualifier("db2DataSource") DataSource db2DataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(db2DataSource);
        factoryBean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
        return factoryBean.getObject();
    }
    
    private void InitDruidDataSource(DruidDataSource druidDataSource, DataSourceProperties properties) throws SQLException {
        druidDataSource.setUrl(properties.getUrl());
        druidDataSource.setUsername(properties.getUsername());
        druidDataSource.setPassword(properties.getPassword());
        druidDataSource.setDriverClassName(properties.getDriverClassName());
        druidDataSource.setFilters(druidDataSourceProperties.getFilters());
        druidDataSource.setMaxActive(druidDataSourceProperties.getMaxActive());
        druidDataSource.setInitialSize(druidDataSourceProperties.getInitialSize());
        druidDataSource.setMaxWait(druidDataSourceProperties.getMaxWait());
        druidDataSource.setMinIdle(druidDataSourceProperties.getMinIdle());
        druidDataSource.setTimeBetweenEvictionRunsMillis(druidDataSourceProperties.getTimeBetweenEvictionRunsMillis());
        druidDataSource.setMinEvictableIdleTimeMillis(druidDataSourceProperties.getMinEvictableIdleTimeMillis());
        druidDataSource.setValidationQuery(druidDataSourceProperties.getValidationQuery());
        druidDataSource.setTestWhileIdle(druidDataSourceProperties.isTestWhileIdle());
        druidDataSource.setTestOnBorrow(druidDataSourceProperties.isTestOnBorrow());
        druidDataSource.setTestOnReturn(druidDataSourceProperties.isTestOnReturn());
        druidDataSource.setRemoveAbandoned(druidDataSourceProperties.isRemoveAbandoned());
        druidDataSource.setRemoveAbandonedTimeout(druidDataSourceProperties.getRemoveAbandonedTimeout());
        druidDataSource.setLogAbandoned(druidDataSourceProperties.isLogAbandoned());
    }
}
