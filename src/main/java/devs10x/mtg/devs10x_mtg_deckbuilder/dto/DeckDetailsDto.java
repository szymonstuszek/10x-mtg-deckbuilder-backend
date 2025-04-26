package devs10x.mtg.devs10x_mtg_deckbuilder.dto;

import lombok.Data;
import java.util.List;

@Data
public class DeckDetailsDto {
    private DeckDto deckInfo;
    private List<CardDto> cards;
    private DeckStatisticsDto statistics;
} 