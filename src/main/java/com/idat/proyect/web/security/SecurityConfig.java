package com.idat.proyect.web.security;

import com.idat.proyect.domain.service.IdatUserDetailsService;
import com.idat.proyect.web.security.filter.JWTFilterRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//se le indica que se encargara de la seguridad
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
     // inyectamos nuestra configuracion

     // datos de los usuarios
     @Autowired
     private IdatUserDetailsService idatUserDetailsService;

     // nuestro filtro modificado para que use JWT

     @Autowired
     private JWTFilterRequest jwtFilterRequest;

     // le indicamos que usara nuestra configuracion de autenticacion
     @Override
     protected void configure(AuthenticationManagerBuilder auth) throws Exception {
          auth.userDetailsService(idatUserDetailsService);
     }

     // configuracion de http
     @Override
     protected void configure(HttpSecurity http) throws Exception {
          // desabilitaras la proteccion de enlaces cruzados en los siguientes url
          // ("/**/authenticate") y sera
          // permitido el acceso
          // http.authorizeRequests().antMatchers("/**/swagger-ui**").permitAll();

          http.csrf().disable().authorizeRequests().antMatchers("/**/authenticate", "/**/public/**").permitAll()
                    .anyRequest().authenticated().and().sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
          // y las demas peticiones si necesitaran autenticacion
          // y que las sesiones estaran desactivadas esto porque siempre se usara el token
          // para su autenticacion
          // se añade filtro antes de ejecutar una peticion que sera de tipo usuario y
          // contraseña
          http.addFilterBefore(jwtFilterRequest, UsernamePasswordAuthenticationFilter.class);
     }

     // rutas a ignorar filtros
     @Override
     public void configure(WebSecurity web) throws Exception {
          web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**",
                    "/configuration/security", "/swagger-ui.html", "/webjars/**");
     }

     // le indicamos que implicitamente las configuraciones que realizemos las use
     @Bean
     public AuthenticationManager authenticationManagerBean() throws Exception {
          return super.authenticationManagerBean();
     }
}
