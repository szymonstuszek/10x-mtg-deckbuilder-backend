package devs10x.mtg.devs10x_mtg_deckbuilder.dto;

import lombok.Data;
import java.util.List;

@Data
public class UpdateDeckDto {
    private Long id;
    private String deckName;
    private String deckFormat;
    private String deckDescription;
    private List<CardDto> cards;
} 