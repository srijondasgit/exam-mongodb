package org.gitanjali.exam.Config;


import org.gitanjali.exam.Filter.JwtFilter;
import org.gitanjali.exam.Service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

//        http.authorizeRequests()
//                .antMatchers("/tests/addQuestion/**").hasRole("TEACHER")
//                .antMatchers("/tests/upsertAnswers/**").hasRole("STUDENT");



//            http.csrf().disable().authorizeRequests()
//                    .antMatchers("tests/addQuestion/**").hasRole("TEACHER")
//                    //.antMatchers("/tests/upsertAnswers/**").hasRole("STUDENT")
//                    //.antMatchers("/hellouser").hasAnyRole("USER","ADMIN")
//                    .antMatchers("/authenticate").permitAll()
//                    .anyRequest().authenticated()
//                    .and().exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).
//                    and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

//        http
//                // remove csrf and state in session because in jwt we do not need them
//                .csrf().disable()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                // add jwt filters (1. authentication, 2. authorization)
//                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
//                .addFilter(new JwtAuthorizationFilter(authenticationManager(),  this.userRepository))
//                .authorizeRequests()
//                // configure access rules
//                .antMatchers(HttpMethod.POST, "/login").permitAll()
//                .antMatchers("/api/public/management/*").hasRole("MANAGER")
//                .antMatchers("/api/public/admin/*").hasRole("ADMIN")
//                .anyRequest().authenticated();


        http.csrf().disable().authorizeRequests()
                .antMatchers("/authenticate").permitAll()
                //.antMatchers("/tests/addQuestion/**").access("hasRole('ROLE_TEACHER')") //  hasRole("TEACHER")
                //.antMatchers("/profile").authenticated()
                .antMatchers("/tests/**").hasRole("TEACHER")
                .antMatchers("/student/**").hasRole("STUDENT")
                .anyRequest().authenticated()
                .and().exceptionHandling().accessDeniedPage("/error/403").and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

//        http.authorizeRequests()
//                //.antMatchers("/authenticate").permitAll()
//                .antMatchers("/tests/addQuestion/**").hasRole("TEACHER")
//                .and()
//                .httpBasic();

//        http.csrf().and().authorizeRequests()
//                .antMatchers("/tests/addQuestion/**").hasRole("TEACHER");

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);;
    }

    @Override
    public void configure(WebSecurity web){
        web.ignoring().antMatchers("/tests/all","/authenticate");
    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception{
//        http.authorizeRequests()
//                .antMatchers("/authenticate").permitAll()
//                .antMatchers("/profile").authenticated()
//                .antMatchers("/admin").hasRole("ADMIN")
//                .antMatchers("/management").hasAnyRole("ADMIN","MANAGER")
//                .and()
//                .httpBasic();
//
//
//    }
}