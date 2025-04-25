package devs10x.mtg.devs10x_mtg_deckbuilder.dto;

import java.time.Instant;
import lombok.Data;

@Data
public class DeckDto {
    private Long id;
    private String deckName;
    private String deckFormat;
    private String deckDescription;
    private Instant createdAt;
    private Instant updatedAt;
} 