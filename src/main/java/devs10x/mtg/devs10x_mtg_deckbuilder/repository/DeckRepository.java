package devs10x.mtg.devs10x_mtg_deckbuilder.repository;

import devs10x.mtg.devs10x_mtg_deckbuilder.entity.Deck;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeckRepository extends JpaRepository<Deck, Long> {

} 