package com.wsl.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.wsl.config.property.DruidDataSourceProperties;
import com.wsl.config.property.DruidMonitorProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.Filter;
import javax.servlet.Servlet;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

@Configuration
@EnableConfigurationProperties({DruidDataSourceProperties.class})
public class DruidDataSourceConfig {

    @Autowired
    private DruidDataSourceProperties properties;

    @Autowired
    private DruidMonitorProperties druidMonitorProperties;

    @Bean
    @ConditionalOnMissingBean
    public DataSource druidDataSource(){
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(properties.getDriverClassName());
        druidDataSource.setUrl(properties.getUrl());
        druidDataSource.setUsername(properties.getUsername());
        druidDataSource.setPassword(properties.getPassword());
        druidDataSource.setInitialSize(properties.getInitialSize());
        druidDataSource.setMinIdle(properties.getMinIdle());
        druidDataSource.setMaxActive(properties.getMaxActive());
        druidDataSource.setMaxWait(properties.getMaxWait());
        druidDataSource.setTimeBetweenEvictionRunsMillis(properties.getTimeBetweenEvictionRunsMillis());
        druidDataSource.setMinEvictableIdleTimeMillis(properties.getMinEvictableIdleTimeMillis());
        druidDataSource.setValidationQuery(properties.getValidationQuery());
        druidDataSource.setTestWhileIdle(properties.isTestWhileIdle());
        druidDataSource.setTestOnBorrow(properties.isTestOnBorrow());
        druidDataSource.setTestOnReturn(properties.isTestOnReturn());
        druidDataSource.setPoolPreparedStatements(properties.isPoolPreparedStatements());
        druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(properties.getMaxPoolPreparedStatementPerConnectionSize());

        try {
            druidDataSource.setFilters(properties.getFilters());
            druidDataSource.init();
        }catch (SQLException e){
            e.printStackTrace();
        }

        return druidDataSource;
    }

    /**
     *注册servlet信息，配置监控视图
     */
    @Bean
    @ConditionalOnMissingBean
    public ServletRegistrationBean<Servlet> druidServlet(){
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), druidMonitorProperties.getServletPath());

        List<String> denyIps = druidMonitorProperties.getDenyIps();
        if(!CollectionUtils.isEmpty(denyIps)){
            bean.addInitParameter("deny", StringUtils.collectionToDelimitedString(denyIps,","));
        }
        List<String> allowIps = druidMonitorProperties.getAllowIps();
        if(!CollectionUtils.isEmpty(allowIps)){
            bean.addInitParameter("allow",StringUtils.collectionToDelimitedString(allowIps,","));
        }
        bean.addInitParameter("loginUsername",druidMonitorProperties.getUsername());
        bean.addInitParameter("loginPassword",druidMonitorProperties.getPassword());
        bean.addInitParameter("resetEnable",String.valueOf(druidMonitorProperties.getResetEnable()));
        return bean;
    }

    /**
     * 注册Filter信息, 监控拦截器
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public FilterRegistrationBean<Filter> filterRegistrationBean() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<Filter>(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }


}
