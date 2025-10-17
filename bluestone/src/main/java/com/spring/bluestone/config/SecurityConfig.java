package com.spring.bluestone.config;


import com.spring.bluestone.config.filter.JwtFilter;
import com.spring.bluestone.service.auth.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;
    private final JwtFilter jwtFilter;

    public SecurityConfig(UserDetailsServiceImpl userDetailsService, JwtFilter jwtFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(
                        request -> request
                                .requestMatchers("/api/v1/private/admin/**").hasAuthority("admin")
                                .requestMatchers("/api/v1/public/**").permitAll()
                                .anyRequest().authenticated()
                ).csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration auth) throws Exception {
        return auth.getAuthenticationManager();
    }

    /*
    - Configure HTTP security to define access rules for different endpoints.

    🧱 @Bean
        Tells Spring that this method will create and return a bean (a managed object).
        The bean here is a SecurityFilterChain — which defines how security filters work in your app.

    ⚙️ public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
        Defines a method named securityFilterChain.
        It takes HttpSecurity (a helper from Spring Security) as input.
        It can throw Exception because Spring internally throws checked exceptions when building security.

     🔐 http.authorizeHttpRequests(request -> request ... )
        This part is where you define access rules for your endpoints (URLs).
        You tell Spring which URLs are public, which require authentication, and which need specific roles.

           🟢 .requestMatchers("/api/v1/public/**").permitAll()
                Any request that matches /api/v1/public/... will be allowed for everyone — no login needed.
                Example:
                    ✅ /api/v1/public/hello → works for all users (even without authentication).
                    🔵 .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
                    Any request that starts with /api/v1/admin/ requires the user to have the ADMIN role.
                    Example:
                    🔒 /api/v1/admin/dashboard → only users with role ADMIN can access.
            🟠 .anyRequest().authenticated()
                Means: for all other endpoints, the user must be logged in (authenticated).
                Even if they’re not admin, they must at least be valid users.

      🔑 .httpBasic(Customizer.withDefaults())
            Enables Basic Authentication.
            Basic Auth = Username + Password sent in every request header (Base64 encoded).
            Example header:
                Authorization: Basic YmlsYXdhbDoxMjM0
                When the browser sees this, it usually shows a login popup automatically.
                🚫 .csrf(AbstractHttpConfigurer::disable)
                    Disables CSRF protection (Cross-Site Request Forgery).
                    CSRF is mainly for web forms — not needed for REST APIs.
                    So we turn it off to make API requests simpler.

        🏁 .build()
                Final step: builds the SecurityFilterChain object and returns it.
                This tells Spring Security:
                Here’s the final set of rules for handling security.”



           🧩 Second Method — configureGlobal(AuthenticationManagerBuilder auth)
                🔹 Purpose:
                This method tells Spring Security how to authenticate users —
                i.e., where to find user information (username, password, roles)
                and which password encoder to use.
               ------------------NOte:------------------

                        In older Spring versions (≤ Spring Boot 2), we had to manually configure authentication like this:
                        @Autowired
                        public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
                            auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
                        }
                        Because WebSecurityConfigurerAdapter required it.

                        In new Spring Boot 3 (Spring Security 6),
                        the security architecture changed — there’s no WebSecurityConfigurerAdapter anymore.
                        Instead, Spring automatically:

                        Detects your UserDetailsService bean
                        Uses your PasswordEncoder bean
                        And wires them into the authentication manager automatically.
                        So, defining configureGlobal() is not only unnecessary — it actually breaks things (causing circular dependency).


            🔐 Third Method — passwordEncoder()
                    🔹 Purpose:
                    This defines how passwords are stored and verified — using encryption (hashing).
     */
}
