package es.ucm.fdi.users.spring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.ucm.fdi.users.business.boundary.UsersManager;

@Service
@Transactional
public class UsersDetailsManager implements UserDetailsService {
	
	@Autowired
	private UsersManager usersManager;
	
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		
		UserDetails user = usersManager.getUser(username);
		
		if (user == null) {
			throw new UsernameNotFoundException(String.format("User %s not found", username));
		}
		
		return user;
	}
}
