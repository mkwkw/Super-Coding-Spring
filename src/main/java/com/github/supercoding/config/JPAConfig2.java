package com.github.supercoding.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(
        basePackages = {"com.github.supercoding.repository.airline_ticket", "com.github.supercoding.repository.flight", "com.github.supercoding.repository.passenger", "com.github.supercoding.repository.reservations", "com.github.supercoding.repository.users", "com.github.supercoding.repository.userPrincipal", "com.github.supercoding.repository.roles"},
        //여러 개의 데이터소스를 사용하기 때문에 아래의 코드 작성 필요
        //em 이름, tm 이름
        entityManagerFactoryRef = "entityManagerFactoryBean2",
        transactionManagerRef = "tmJpa2"
)
public class JPAConfig2 {

    //Entity Manager
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean2(@Qualifier("dataSource2") DataSource dataSource){
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.github.supercoding.repository.airline_ticket", "com.github.supercoding.repository.flight", "com.github.supercoding.repository.passenger", "com.github.supercoding.repository.reservations", "com.github.supercoding.repository.users", "com.github.supercoding.repository.userPrincipal", "com.github.supercoding.repository.roles");

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        properties.put("hibernate.format_sql", "true");
        properties.put("hibernate.use_sql_comment", "true");

        em.setJpaPropertyMap(properties);
        return em;
    }

    //Transaction Manager
    @Bean(name = "tmJpa2")
    public PlatformTransactionManager transactionManager2(@Qualifier("dataSource2") DataSource dataSource){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactoryBean2(dataSource).getObject());
        return transactionManager;
    }
}
