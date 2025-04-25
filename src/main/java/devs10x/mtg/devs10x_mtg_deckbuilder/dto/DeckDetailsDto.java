package devs10x.mtg.devs10x_mtg_deckbuilder.dto;

import lombok.Data;
import java.util.Map;

@Data
public class DeckDetailsDto {
    private DeckDto deckInfo;
    private Map<CardDto, Integer> cards;
    private DeckStatisticsDto statistics;
} 