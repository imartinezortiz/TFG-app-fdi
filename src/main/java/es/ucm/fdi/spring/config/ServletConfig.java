package es.ucm.fdi.spring.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
@Import(WebConfig.class)
@ImportResource({
  "classpath:META-INF/spring/social-context.xml",
})
public class ServletConfig {

}