package devs10x.mtg.devs10x_mtg_deckbuilder.dto;

import lombok.Data;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@Data
public class CreateDeckDto {
    private String deckName;
    private String deckFormat;
    private String deckDescription;
    // Optional: initial cards map with quantities
    @JsonDeserialize(keyUsing = CardDtoKeyDeserializer.class)
    private Map<CardDto, Integer> cards;
} 