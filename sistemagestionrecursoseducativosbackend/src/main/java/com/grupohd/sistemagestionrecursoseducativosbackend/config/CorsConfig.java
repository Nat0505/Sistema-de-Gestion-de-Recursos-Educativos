package com.grupohd.sistemagestionrecursoseducativosbackend.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Esta clase permite que el frontend pueda comunicarse con este backend sin errores de CORS
 */
@Configuration
public class CorsConfig {
    /**
     * Esto configura CORS para que acepte peticiones de cualquier origen
     * y permita métodos como GET, POST, PUT y DELETE.
     */
    @Bean
    public WebMvcConfigurer webMvcConfigurer(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedOrigins("*");
            }
        };
    }
}
