package com.parkinguk.parkinguk.config;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity//to enable spring security features
public class ConfigFilter extends WebSecurityConfigurerAdapter {

    //this method run at runtime out of the box upon making a request to server
    // we authorize every request by disabling the security
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .anyRequest().permitAll()
                .and().httpBasic();// enable the request with basic check that the server can do
    }
    //cors identify different origins
    @Bean
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();//create basic URL configuration source in order to set the configuration of the allowed origins
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*");// url
        config.addAllowedHeader("*");// request method
        config.addAllowedMethod("*");// post get delete put
        source.registerCorsConfiguration("/**", config);//
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));//inizializasion the source
        bean.setOrder(0);
        return bean;
    }
}