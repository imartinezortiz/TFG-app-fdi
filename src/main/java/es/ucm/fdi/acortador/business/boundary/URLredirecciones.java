package es.ucm.fdi.acortador.business.boundary;

import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.ucm.fdi.acortador.business.control.URLredireccionRepository;
import es.ucm.fdi.acortador.business.entity.URLredireccion;
import es.ucm.fdi.acortador.business.entity.URLredireccionBuilder;
import es.ucm.fdi.acortador.util.AcortadorURL;
import es.ucm.fdi.users.business.entity.User;

@Transactional
@Service
public class URLredirecciones {
	@Autowired
	URLredireccionRepository repository;
	
	private static final Logger logger = LoggerFactory.getLogger("es.ucm.fdi.acortador");
	
	public URLredireccion addURLredireccion(URLredireccion urlRedireccion){
		return repository.save(urlRedireccion);
	}
	
	public URLredireccion addURLredireccion(URLredireccionBuilder builder){
		URLredireccion redireccion = builder.build();
		redireccion.setInterna(AcortadorURL.esUrlInterna(builder.getUrl()));
		
		/*
		 * Si la URL no empieza por http (o https),
		 * spring la toma como interna y no funciona como debiera. 
		 */ 
		if (! builder.getUrl().startsWith("http")){
			redireccion.setUrlOriginal("http://"+ builder.getUrl());
		}
		else{
			redireccion.setUrlOriginal(builder.getUrl());
		}
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    User creador = (User)auth.getPrincipal();
	    
	    redireccion.setCreador(creador);		
		
		redireccion = repository.save(redireccion);
		
		Long id = redireccion.getId();
		redireccion.setSufijo(AcortadorURL.generarHashId(id));
		
		logger.debug(redireccion.getSufijo());

		repository.save(redireccion);
		
		return redireccion;
		
	}

	
	public URLredireccion obtenerRedireccion(String sufijo){
		URLredireccion urlRedir = repository.findBySufijo(sufijo);
		return urlRedir;
	}
	
	public List<URLredireccion> obtenerRedirecciones(){
		return (List<URLredireccion>) repository.findAll();
	}

}
