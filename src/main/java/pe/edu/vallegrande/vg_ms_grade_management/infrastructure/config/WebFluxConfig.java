package pe.edu.vallegrande.vg_ms_grade_management.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

/**
 * Configuración WebFlux para el microservicio
 * CORS manejado por el Gateway
 */
@Configuration
@EnableWebFlux
public class WebFluxConfig implements WebFluxConfigurer {
    // CORS configurado en el Gateway - no se requiere aquí
}
