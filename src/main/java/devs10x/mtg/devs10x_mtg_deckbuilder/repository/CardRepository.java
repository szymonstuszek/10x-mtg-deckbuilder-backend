package devs10x.mtg.devs10x_mtg_deckbuilder.repository;

import devs10x.mtg.devs10x_mtg_deckbuilder.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {

} 