package com.proyecto.egginder.configuraciones;

import com.proyecto.egginder.servicios.AlumnoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//  Indica a Spring que es una clase de configuracion
@Configuration
// Habilita la seguridad web
@EnableWebSecurity
// Son los metodos de seguridad, (PrePostEnabled = true) -> negar acceso a ciertos usuarios
@EnableGlobalMethodSecurity(prePostEnabled = true)
// extends WebSecurityConfigurerAdapter -> Va a detallar cual es el servicio que contiene al usuario para autenticarlo, cargarlo, encriptarlo, etc.
public class ConfiguracionSeguridad extends WebSecurityConfigurerAdapter{
    
    // Indicamos a spring que se encargue de la creacion de la clase de servicio
    @Autowired
    // esta clase va a contener la creacion del usuario e implementar un metodo abstracto de carga.
    private AlumnoServicio alumnoServicio;
    
    // Indicamos a Spring cuando crear este metodo
    @Autowired
    // Se va a encargar de autenticar a los usuarios
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        
        // Desencriptado de la clave dentro del servicio
        auth.userDetailsService(alumnoServicio).passwordEncoder(new BCryptPasswordEncoder());
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        // Hacemos que aunque no esten logueados, les demos acceso a los archivos estaticos y los templates
        http.authorizeRequests().antMatchers("/css/*", "/js/*", "/img/*", "/**").permitAll()
                
                //Configuraciones LOGIN / INGRESAR
                .and().formLogin()
                        // Ruta para ingresar [login]
                        .loginPage("/login") 
                        // Ruta del postmapping en el thymeleaf (th:action="@{/logincheck}")
                        .loginProcessingUrl("/logincheck")
                        // Atributo que usamos para el nombre de usuario en el formulario
                        .usernameParameter("email")
                        // Atributo que usamos para la contraseña en el formulario
                        .passwordParameter("clave")
                        // Ruta a la que vamos a acceder despues de loguearnos con exito
                        // + atributo de login
                        .defaultSuccessUrl("/inicio?login").permitAll()
                
                //Configuraciones LOGOUT / DESCONECTARSE
                .and().logout()
                        // Ruta para desconectarse [logout]
                        .logoutUrl("/logout")
                        // Cuando nos desconectemos, nos envia a esta ruta (formulario login)
                        .logoutSuccessUrl("/login?logout").permitAll()
                
                // Deshabilitamos el token de seguridad
                // para evitar problemas, ya que estamos usando simplemente localhost
                .and().csrf().disable();
    }
}
