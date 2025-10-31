package pe.edu.vallegrande.vg_ms_grade_management.infrastructure.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Configuración global para el manejo de fechas en la aplicación.
 * - LocalDate: formato dd-MM-yyyy (para calificaciones)
 * - ZonedDateTime: formato dd-MM-yyyy HH:mm:ss con zona horaria America/Lima (para notificaciones)
 */
@Configuration
public class DateTimeConfig {

    // Formato para fechas simples (calificaciones)
    private static final String DATE_FORMAT = "dd-MM-yyyy";
    
    // Formato para fechas con hora (notificaciones)
    private static final String DATETIME_FORMAT = "dd-MM-yyyy HH:mm:ss";

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        
        // Configurar serialización/deserialización de LocalDate
        javaTimeModule.addSerializer(LocalDate.class, 
            new LocalDateSerializer(DateTimeFormatter.ofPattern(DATE_FORMAT)));
        javaTimeModule.addDeserializer(LocalDate.class, 
            new LocalDateDeserializer(DateTimeFormatter.ofPattern(DATE_FORMAT)));
        
        // Configurar serialización de ZonedDateTime
        javaTimeModule.addSerializer(ZonedDateTime.class,
            new ZonedDateTimeSerializer(DateTimeFormatter.ofPattern(DATETIME_FORMAT)));
        
        mapper.registerModule(javaTimeModule);
        return mapper;
    }
}
