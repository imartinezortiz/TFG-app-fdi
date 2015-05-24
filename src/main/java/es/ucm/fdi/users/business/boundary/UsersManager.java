package es.ucm.fdi.users.business.boundary;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.ucm.fdi.users.business.control.UserRepository;
import es.ucm.fdi.users.business.entity.User;
import es.ucm.fdi.users.business.entity.UserBuilder;
import es.ucm.fdi.users.business.entity.UserRole;
import es.ucm.fdi.users.util.PasswordHash;

@Service
@Transactional
public class UsersManager {

	@Autowired
	private UserRepository repository;
	
	public User getUser(String username)
			throws UsernameNotFoundException {
		
		User user = repository.findByEmail(username);
		if (user == null)  {
			user = repository.findByUsername(username);
		}
		
		if (user == null) {
			throw new UsernameNotFoundException(String.format("User %s not found", username));
		}
		
		return user;
	}
	
	public User saveUser(UserBuilder builder){
		User user = builder.build();
		try {
			user.setPassword(PasswordHash.createHash(user.getPassword()));
		} catch (NoSuchAlgorithmException e) {			
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
		
		Collection<UserRole> roles = new ArrayList<UserRole>();
		roles.add(new UserRole("ROLE_USER"));
		
		user.setRoles(roles);
		
		user = repository.save(user);
		return user;
	}

	public Iterable<User> listUsers() {
		return repository.findAll();
	}
	
}
