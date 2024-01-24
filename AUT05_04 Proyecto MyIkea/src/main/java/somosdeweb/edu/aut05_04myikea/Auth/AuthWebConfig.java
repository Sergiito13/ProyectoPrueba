package somosdeweb.edu.aut05_04myikea.Auth;

import somosdeweb.edu.aut05_04myikea.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class AuthWebConfig {

    @Lazy
    @Autowired
    private UserService userService;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests((auth) -> auth
                .requestMatchers("/", "/register", "/login").permitAll()
                .requestMatchers("/Productos/", "/Productos/Details/").hasAnyRole("USER", "MANAGER", "ADMIN")
                .requestMatchers("/Pedidos/Finalizar","/Carrito/**", "/Pedidos/**").hasRole("USER")
                .requestMatchers("/Productos/Create").hasAnyRole("MANAGER", "ADMIN")
                .requestMatchers("/Usuarios/**").hasRole("ADMIN")
                .anyRequest().authenticated()
        );

        http.formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/")
                .permitAll()
        );

        http.logout(form -> form
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
        );

        http.userDetailsService(userService);

        return http.build();
    }

}
