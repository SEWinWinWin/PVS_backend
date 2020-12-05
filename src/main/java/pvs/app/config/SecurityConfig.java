//package rvs.demo.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.cors()
//                .and().authorizeRequests()
//                .anyRequest().permitAll()
//                .and().csrf().disable();
//    }
//
////    @Bean
////    PasswordEncoder passwordEncoder() {
////        return NoOpPasswordEncoder.getInstance();
////    }
////
////    @Override
////    public void configure(AuthenticationManagerBuilder auth) throws Exception {
////        auth.inMemoryAuthentication()
////                .withUser("admin").password("123").roles("ADMIN")
////                .and()
////                .withUser("user").password("123").roles("USER");
////    }
//
////    @Override
////    protected void configure(HttpSecurity http) throws Exception {
////        http.exceptionHandling()
////                .authenticationEntryPoint(new RestAuthenticationEntryPoint())
////                .accessDeniedHandler(new AccessDeniedHandlerImpl())
////                .and()
////                .addFilterAt(loginAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
////                .authorizeRequests()
////                .antMatchers("/admin/**").hasRole("ADMIN")
//////                .antMatchers("/**").hasRole("USER")
////                .and()
////                .logout()
////                .logoutUrl("/logout")
////                .invalidateHttpSession(true)
////                .logoutSuccessHandler((req, resp, auth) -> {
////                    resp.setContentType("application/json;charset=UTF-8");
////                    PrintWriter out = resp.getWriter();
////                    resp.setStatus(200);
////                    Map<String, String> result = Map.of("message", "登出成功");
////                    ObjectMapper om = new ObjectMapper();
////                    out.write(om.writeValueAsString(result));
////                    out.flush();
////                    out.close();
////                })
////                .and()
////                .csrf()
////                .disable();
////    }
//
////    @Bean
////    LoginAuthenticationFilter loginAuthenticationFilter() throws Exception {
////        LoginAuthenticationFilter filter = new LoginAuthenticationFilter();
////        filter.setAuthenticationManager(authenticationManagerBean());
////        filter.setAuthenticationSuccessHandler(new AuthenticationSuccessHandlerImpl());
////        filter.setAuthenticationFailureHandler(new AuthenticationFailureHandlerImpl());
////        filter.setFilterProcessesUrl("/login");
////        return filter;
////    }
////
////    @Override
////    @Bean
////    public AuthenticationManager authenticationManagerBean() throws Exception {
////        return super.authenticationManagerBean();
////    }
//
//}
