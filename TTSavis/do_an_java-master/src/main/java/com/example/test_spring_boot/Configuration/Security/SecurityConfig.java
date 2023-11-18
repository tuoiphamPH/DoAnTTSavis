package com.example.test_spring_boot.Configuration.Security;

import com.example.test_spring_boot.Configuration.Oauth2.CustomOauth2User;
import com.example.test_spring_boot.Entity.UserEntity;
import com.example.test_spring_boot.Repository.UserRepository;
import com.example.test_spring_boot.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @Bean
    public UserDetailsServiceCustom userDetailsServiceImpl(){
        return new UserDetailsServiceCustom() ;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http
                .authorizeRequests()
                .antMatchers("/login", "/add", "/product/index_user",
                        "/add_cart/**",
                        "/cart","/product_detail_user/**","/review/**",
                        "/search_report", "/search_report_by_page",
                        "/index_user","/pay", "/register", "/api/**", "/nganluong/**"
                        ).permitAll()
                .and()
                .formLogin().permitAll()
                .loginPage("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .successHandler((request, response, authentication) -> {
                    UserDetailCustom ud =(UserDetailCustom) authentication.getPrincipal();
                    String nameZ = ud.userEntity.getFullname();
                    if(nameZ == null)
                        nameZ = ud.userEntity.getUsername();
                    String finalNameZ = nameZ;
                    HttpSession session = request.getSession();
                    ud.getAuthorities().forEach(x->{
                        try{
                            if(x.getAuthority().equals("ROLE_ADMIN")){
                                session.setAttribute("nameUser", finalNameZ);
                                response.sendRedirect("/product/categoryProduct/index");
                            }
                            else if(x.getAuthority().equals("ROLE_USER")){
                                if (ud.userEntity.getActive() == 0){
                                    session.setAttribute("dangnhaperror","error");
                                    response.sendRedirect("/login");
                                }else {
                                    session.setAttribute("nameUser", finalNameZ);
                                    response.sendRedirect("/product/index_user");
                                }
                            }
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                })
                .failureHandler((request, response, authentication) -> {
                    HttpSession session = request.getSession();
                    session.setAttribute("dangnhaperror","error");
                    response.sendRedirect("/login");})
                .and()
                .oauth2Login()
                .loginPage("/login")
                .userInfoEndpoint()
                .and()
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                        CustomOauth2User oAuth2User = (CustomOauth2User) authentication.getPrincipal();
                        String clientName = oAuth2User.getClientName();
                        String email = oAuth2User.getAttribute("email");
                        HttpSession session = request.getSession();

                        UserEntity userEntity = userService.checkExistUserOauth(oAuth2User.getName(), clientName,email);
                        if(userEntity == null){
                            session.setAttribute("error","error");
                            response.sendRedirect("/login");
                        }else {
                            UserEntity user = userRepository.getByEmail(email);
                            session.setAttribute("nameUser", oAuth2User.getName());
                            response.sendRedirect("/product/index_user");
                        }
                    }
                })
                .and()
                .logout()
                .logoutSuccessUrl("/login").permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/403");
    }
}
