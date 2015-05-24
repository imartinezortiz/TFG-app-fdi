package es.ucm.fdi.avisos.boundary;

import java.io.IOException;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import es.ucm.fdi.avisos.business.control.AvisoRepository;
import es.ucm.fdi.avisos.business.entity.Aviso;
import es.ucm.fdi.avisos.business.entity.AvisoBuilder;
import es.ucm.fdi.storage.business.boundary.StorageManager;
import es.ucm.fdi.storage.business.entity.StorageObjectId;
import es.ucm.fdi.users.business.control.UserRepository;
import es.ucm.fdi.users.business.entity.User;

@Transactional
@Service
public class Avisos {
	
	@Autowired
	AvisoRepository avisoRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	StorageManager storageManager;
	
	@Value("#{avisosPrefs[bucket]}")
	private String bucket;
	
	AvisoRepository getAvisoRepository() {
		return avisoRepository;
	}

	void setAvisoRepository(AvisoRepository avisoRepository) {
		this.avisoRepository = avisoRepository;
	}

	StorageManager getStorageManager() {
		return storageManager;
	}

	void setStorageManager(StorageManager manager) {
		this.storageManager = manager;
	}

	String getBucke() {
		return bucket;
	}

	void setBucket(String bucket) {
		this.bucket = bucket;
	}

	public Iterable<Aviso> getAvisos() {
		return avisoRepository.findAll();
	}

	public Aviso getAviso(Long avisoID) {
		return avisoRepository.findOne(avisoID);
	}

	public List<Aviso> findByEtiqueta(String etiqueta) {
		return avisoRepository.findByEtiqueta(etiqueta);
	}

	public void eliminarAviso(Long avisoID) throws IOException {
		Aviso aviso = avisoRepository.findOne(avisoID);
		String adjunto = aviso.getAdjunto();
		if (adjunto != null) {
			StorageObjectId id = storageManager.getObjectId(adjunto);
			storageManager.removeObject(id);
		}
		avisoRepository.delete(aviso);
	}

	public Aviso addAviso(AvisoBuilder builder) throws IOException {
		if (builder.getFechaCreacion() ==  null) {
			builder.setFechaCreacion(DateTime.now());
		}
		Aviso aviso = builder.build();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    User autor = (User)auth.getPrincipal();
	    aviso.setAutor(autor);
		
		
		aviso = avisoRepository.save(aviso);
		MultipartFile file = builder.getAdjunto();
		if (file != null && !file.isEmpty()) {
			String key = getStorageKey(aviso.getId());
			String mimeType = file.getContentType();
			storageManager.putObject(bucket, key, mimeType, file.getInputStream());
			aviso.setAdjunto(storageManager.getUrl(bucket, key).toExternalForm());
			avisoRepository.save(aviso);
		}
		return aviso;
	}
	
	private String getStorageKey(Long id) {
		return "attachment/"+Long.toString(id);
	}

	public Aviso actualizaAviso(AvisoBuilder builder) throws IOException {
		Aviso newAviso = builder.build();
		Aviso aviso = avisoRepository.findOne(newAviso.getId());
		String adjunto = aviso.getAdjunto();
		if (adjunto != null) {
			StorageObjectId id = storageManager.getObjectId(adjunto);
			storageManager.removeObject(id);
			aviso.setAdjunto(null);
		}
		MultipartFile file = builder.getAdjunto();
		if (file != null && !file.isEmpty()) {
			String key = getStorageKey(newAviso.getId());
			String mimeType = file.getContentType();
			storageManager.putObject(bucket, key, mimeType, file.getInputStream());
			newAviso.setAdjunto(storageManager.getUrl(bucket, key).toExternalForm());
		}
		BeanUtils.copyProperties(newAviso, aviso);
		return avisoRepository.save(aviso);
	}
	
	public Aviso actualizaInfoTwitter(Long idAviso,Long idTweetAsociado, Long idAutorTwitterAsociado){
		Aviso aviso = avisoRepository.findOne(idAviso);
		aviso.setIdAutorTwitterAsociado(idAutorTwitterAsociado);
		aviso.setIdTweetAsociado(idTweetAsociado);
		return avisoRepository.save(aviso);
	}
}
