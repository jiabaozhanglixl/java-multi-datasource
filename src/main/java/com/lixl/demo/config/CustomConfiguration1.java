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
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.sql.SQLException;

//不同的dao包名，指到不同的数据源。自动切换。
@Configuration
@MapperScan(basePackages = {"com.lixl.demo.dao1"}, sqlSessionFactoryRef = "sqlSessionFactory1")
public class CustomConfiguration1 {

    @Autowired
    private DruidDataSourceProperties druidDataSourceProperties;

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "db1.datasource")
    public DataSourceProperties db1DataSourceProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean
    @ConfigurationProperties(prefix = "db1.datasource")
    public DataSource db1DataSource(@Qualifier("db1DataSourceProperties") DataSourceProperties dataSourceProperties) throws SQLException {
        DruidXADataSource druidXADataSource = new DruidXADataSource();
        InitDruidDataSource(druidXADataSource, dataSourceProperties);

        AtomikosDataSourceBean atomikosDataSource = new AtomikosDataSourceBean();
        atomikosDataSource.setUniqueResourceName("db1DataSource");
        atomikosDataSource.setXaDataSource(druidXADataSource);
        atomikosDataSource.setTestQuery("SELECT 1");
        return atomikosDataSource;
    }

    @Primary
    @Bean(name = "sqlSessionFactory1")
    public SqlSessionFactory sqlSessionFactory1(@Qualifier("db1DataSource") DataSource db1DataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(db1DataSource);
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
