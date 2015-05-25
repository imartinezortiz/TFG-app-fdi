package es.ucm.fdi.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;


@Configuration
@ComponentScan(basePackages = { "es.ucm.fdi.portal.web",
		"es.ucm.fdi.users.web", "es.ucm.fdi.avisos.web",
		"es.ucm.fdi.espacios.web", "es.ucm.fdi.storage.web",
		"es.ucm.fdi.users.web", "es.ucm.fdi.acortador.web",
		"es.ucm.fdi.social.web", "es.ucm.fdi.tutorias.web",
		"es.ucm.fdi.avisos.validation", "es.ucm.fdi.espacios.validation" })
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations(
				"/static/");
		registry.addResourceHandler("/webjars/**").addResourceLocations(
				"/webjars/");
	}

	@Bean
	public TilesConfigurer tilesConfigurer() {
		TilesConfigurer tilesConfigurer = new TilesConfigurer();
		tilesConfigurer
				.setDefinitions("/WEB-INF/tiles/portal/definitions/tile-definition.xml");
		return tilesConfigurer;
	}

	@Bean
	public ViewResolver tilesViewResolver() {
		TilesViewResolver viewResolver = new TilesViewResolver();
		viewResolver.setOrder(0);
		return viewResolver;
	}

	@Bean
	public ViewResolver internalViewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/jsp/portal/");
		viewResolver.setSuffix(".jsp");
		viewResolver.setOrder(1);
		return viewResolver;
	}
}
