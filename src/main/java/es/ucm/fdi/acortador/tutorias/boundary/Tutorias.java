package es.ucm.fdi.acortador.tutorias.boundary;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.ucm.fdi.tutorias.business.control.TutoriaRepository;
import es.ucm.fdi.tutorias.business.entity.Tutoria;
import es.ucm.fdi.tutorias.business.entity.TutoriaBuilder;
import es.ucm.fdi.users.business.entity.User;


@Transactional("portalTransactionManager")
@Service
public class Tutorias {
	private static final Logger logger = LoggerFactory.getLogger("es.ucm.fdi.tutorias");
	
	@Autowired
	TutoriaRepository tutoriaRepository; 
	
	public Iterable<Tutoria> getTutorias() {
		logger.warn(tutoriaRepository.findAll().toString());
		return tutoriaRepository.findAll();
	}
	
	public Iterable<Tutoria> getTutoriasForUser(Long idUser) {
		logger.warn("Buscando tutorías para usuario con id " + idUser + ":" + tutoriaRepository.tutoriasDeUsuario(idUser).toString());
		return tutoriaRepository.tutoriasDeUsuario(idUser);
	}
	
	public Tutoria getTutoria(Long tutoriaId){
		return tutoriaRepository.findOne(tutoriaId);
	}
	
	public Tutoria addTutoria(TutoriaBuilder tutoriaBuilder){
		Tutoria tutoria = tutoriaBuilder.build();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    User emisor = (User)auth.getPrincipal();
	    
	    tutoria.setEmisor(emisor);
		return tutoriaRepository.save(tutoria);
	}

}