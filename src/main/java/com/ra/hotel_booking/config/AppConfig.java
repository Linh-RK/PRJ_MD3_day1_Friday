package com.ra.hotel_booking.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableWebMvc
@EnableScheduling
@ComponentScan(basePackages = "com.ra.hotel_booking")
public class AppConfig implements WebMvcConfigurer, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Bean
    public SpringResourceTemplateResolver templateResolver(){
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setPrefix("/WEB-INF/views");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding("UTF-8");
        return templateResolver;
    }
    @Bean
    public SpringTemplateEngine templateEngine(){
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setEnableSpringELCompiler(true);
        templateEngine.setTemplateResolver(templateResolver());
        return templateEngine;
    }
    @Bean
    public ThymeleafViewResolver thymeleafViewResolver (){
        ThymeleafViewResolver thymeleafViewResolver = new ThymeleafViewResolver();
        thymeleafViewResolver.setTemplateEngine(templateEngine());
        thymeleafViewResolver.setCharacterEncoding("UTF-8");
        return thymeleafViewResolver;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext){
        this.applicationContext = applicationContext;
    }
    // cấu hình database
    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/HotelBooking?createDatabaseIfNotExist=true");
        dataSource.setUsername("root");
        dataSource.setPassword("linh240370");
        return dataSource;
    }
    @Bean
    public LocalSessionFactoryBean sessionFactoryBean(){
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource());
        sessionFactoryBean.setPackagesToScan("com.ra.hotel_booking.model.entity");
        Properties properties = new Properties();
        properties.setProperty("hibernate.show_sql","true");
        properties.setProperty("hibernate.dialect","org.hibernate.dialect.MySQL8Dialect");
        properties.setProperty("hibernate.hbm2ddl.auto","update");
//        properties.setProperty("javax.persistence.validation.mode", "none");///---------
        sessionFactoryBean.setHibernateProperties(properties);
        return sessionFactoryBean;
    }

    @Bean
    CommonsMultipartResolver multipartResolver(){
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(52428800);
        return multipartResolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/assets/admin/**")
                .addResourceLocations("classpath:/assets/admin/");
        registry.addResourceHandler("/assets2/user/**")
                .addResourceLocations("classpath:/assets2/user/");
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:/Users/phamlinh/Desktop/HOTEL_BOOKING/src/main/webapp/uploads/");
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
//        registry.addInterceptor(new InterceptorsAdmin())
//                .addPathPatterns("/admin/**")
//                .excludePathPatterns("/login","/register","/forgot-password");
        registry.addInterceptor(new InterceptorCustomer())
                .addPathPatterns("/user/**")
                .excludePathPatterns("/user/login","/user/register","/user/forgot-password","/user/index", "/user/events"
                ,"/user/events-detail","/user/about", "/user/contact");
    }


}
