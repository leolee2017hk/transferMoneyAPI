package net.leolee.transfermoneyapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
public class ApplicationSecurity extends WebSecurityConfigurerAdapter {
	/*
	@Override
    public void configure(WebSecurity web) throws Exception {
        web
           .ignoring()
               .antMatchers("/**");
    }
*/
	private static String REALM="MONEY_TRANSFER_REALM";
    
    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("system").password("{noop}abc123").roles("USER");
    }
     
    @Override
    protected void configure(HttpSecurity http) throws Exception {
  
      http.csrf().disable()
        .authorizeRequests()
        .antMatchers("/**").hasRole("USER")
        .and().httpBasic().realmName(REALM).authenticationEntryPoint(getBasicAuthEntryPoint())
        .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
     
    @Bean
    public CustomBasicAuthenticationEntryPoint getBasicAuthEntryPoint(){
        return new CustomBasicAuthenticationEntryPoint();
    }
     
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
    }
}
