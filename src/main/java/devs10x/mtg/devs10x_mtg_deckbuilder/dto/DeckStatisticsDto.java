package devs10x.mtg.devs10x_mtg_deckbuilder.dto;

import lombok.Data;
import java.util.Map;

@Data
public class DeckStatisticsDto {
    private int totalCards;
    private Map<String, Integer> types;
    private Map<String, Integer> manaCurve;
    private Map<String, Integer> colors;
} 