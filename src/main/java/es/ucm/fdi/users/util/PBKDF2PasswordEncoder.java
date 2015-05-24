package es.ucm.fdi.users.util;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 
 * @author <michal.klempa@gmail.com>
 * 
 * @see http://www.michalklempa.com/2015/02/08/how-to-add-pbkdf2-password-hashing-to-a-spring-security-based-project/
 */
public class PBKDF2PasswordEncoder implements PasswordEncoder {

	@Override
	public String encode(CharSequence cs) {
		try {
			return PasswordHash.createHash(cs.toString());
		} catch (NoSuchAlgorithmException ex) {
			throw new RuntimeException(ex);
		} catch (InvalidKeySpecException ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	public boolean matches(CharSequence cs, String string) {
		try {
			return PasswordHash.validatePassword(cs.toString(), string);
		} catch (NoSuchAlgorithmException ex) {
			throw new RuntimeException(ex);
		} catch (InvalidKeySpecException ex) {
			throw new RuntimeException(ex);
		}
	}

}
