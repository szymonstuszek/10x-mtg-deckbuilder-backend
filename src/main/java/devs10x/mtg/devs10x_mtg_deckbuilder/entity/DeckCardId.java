package devs10x.mtg.devs10x_mtg_deckbuilder.entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.Data;

@Data
@Embeddable
public class DeckCardId implements Serializable {
    private Long deckId;
    private Long cardId;
} 