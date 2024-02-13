package org.puravidatgourmet.api.db.repository;

import java.util.Optional;
import org.puravidatgourmet.api.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<User, Long> {
	
	Optional<User> findByEmail(String email);
	
	Boolean existsByEmail(String email);

}
