package pe.edu.vallegrande.vg_ms_grade_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

/**
 * Clase principal de la aplicación de gestión de calificaciones
 */
@SpringBootApplication
@EnableWebFlux
public class VgMsGradeManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(VgMsGradeManagementApplication.class, args);
	}

	/**
	 * Configuración de CORS para permitir peticiones desde cualquier origen
	 */
	@Bean
	public CorsWebFilter corsWebFilter() {
		CorsConfiguration config = new CorsConfiguration();
		config.addAllowedOriginPattern("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		config.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);

		return new CorsWebFilter(source);
	}
}
