package devs10x.mtg.devs10x_mtg_deckbuilder.repository;

import devs10x.mtg.devs10x_mtg_deckbuilder.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

} 