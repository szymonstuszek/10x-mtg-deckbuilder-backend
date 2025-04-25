package devs10x.mtg.devs10x_mtg_deckbuilder.entity;

import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.List;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.stream.Collectors;
import devs10x.mtg.devs10x_mtg_deckbuilder.entity.DeckCard;
import devs10x.mtg.devs10x_mtg_deckbuilder.entity.Card;

@Data
@Entity
@Table(name = "decks")
public class Deck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String deckName;
    private String deckFormat;
    private String deckDescription;
    private Instant createdAt;
    private Instant updatedAt;
    
    @ManyToOne
    @JoinColumn(name = "user_sub")
    private User user;
    
    @OneToMany(mappedBy = "deck")
    private List<DeckCard> deckCards;

    public List<Card> getCards() {
        if (deckCards == null) {
            return new ArrayList<>();
        }
        return deckCards.stream().map(DeckCard::getCard).collect(Collectors.toList());
    }
} 