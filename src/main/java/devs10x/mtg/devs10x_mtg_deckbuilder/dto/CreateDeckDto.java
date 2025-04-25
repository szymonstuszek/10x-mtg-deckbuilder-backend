package devs10x.mtg.devs10x_mtg_deckbuilder.dto;

import lombok.Data;
import java.util.List;

@Data
public class CreateDeckDto {
    private String deckName;
    private String deckFormat;
    private String deckDescription;
    // Optional: initial cards list
    private List<CardDto> cards;
} 