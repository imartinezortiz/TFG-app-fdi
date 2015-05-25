package es.ucm.fdi.spring.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
@PropertySource({"classpath:db.properties", "classpath:social.properties", "classpath:security.properties", "classpath:gmail.properties"})
@Import({BusinessConfig.class, PersistenceJPAConfig.class, SecurityConfig.class})
public class AppConfig {
	
	@Autowired
	Environment env;
	
	@Bean
	public MultipartResolver filterMultipartResolver() {
		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
		resolver.setMaxUploadSize(10240000);
		return resolver;
	}
	
	@Bean
	public Properties avisosPrefs() {
	    Properties properties = new Properties();
	    properties.put("bucket", "avisos");
	    return properties;
	}	
	
	@Bean
	public Properties storagePrefs() {
	    Properties properties = new Properties();
	    properties.put("rootPath", "WEB-INF/storage");
//	    properties.put("serviceURL", "http://localhost:8088/portal/storage/");
	    properties.put("serviceURL", "http://localhost:8080/portal/storage/");
	    return properties;
	}
	
	@Bean
	public Properties gmailData() {
	    Properties properties = new Properties();
	    properties.put("username", env.getRequiredProperty("mail.username"));
	    properties.put("password", env.getRequiredProperty("mail.password"));
	    properties.put("auth", env.getRequiredProperty("mail.auth"));
	    properties.put("starttls", env.getRequiredProperty("mail.starttls.enable"));
	    properties.put("host", env.getRequiredProperty("mail.host"));
	    properties.put("port", env.getRequiredProperty("mail.port"));
	    return properties;
	}
	
	@Bean
	public Properties twitterData() {
	    Properties properties = new Properties();
	    properties.put("consumerKey", env.getRequiredProperty("twitter.consumerKey"));
	    properties.put("consumerSecret", env.getRequiredProperty("twitter.consumerSecret"));
	    properties.put("accessToken", env.getRequiredProperty("twitter.accessToken"));
	    properties.put("accessTokenSecret", env.getRequiredProperty("twitter.accessTokenSecret"));
	    return properties;
	}
	
	
	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messages = new ReloadableResourceBundleMessageSource();
		messages.setBasename("classpath:messages");
		return messages;
	}
}