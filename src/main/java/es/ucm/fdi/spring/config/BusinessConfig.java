package es.ucm.fdi.spring.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = { "es.ucm.fdi.users.business.boundary",
		"es.ucm.fdi.avisos.boundary", "es.ucm.fdi.storage.business.boundary",
		"es.ucm.fdi.espacios.business.boundary", "es.ucm.fdi.acortador.business.boundary",
		"es.ucm.fdi.tutorias.business.boundary"})
public class BusinessConfig {

}
