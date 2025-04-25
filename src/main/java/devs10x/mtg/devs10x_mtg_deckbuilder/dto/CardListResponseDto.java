package devs10x.mtg.devs10x_mtg_deckbuilder.dto;

import lombok.Data;
import java.util.List;

@Data
public class CardListResponseDto {
    private List<CardDto> cards;
    // private PaginationDto pagination; // TODO: Consider using Spring's Pageable or adjust based on @mtg-api requirements
} 