package devs10x.mtg.devs10x_mtg_deckbuilder.service;

import devs10x.mtg.devs10x_mtg_deckbuilder.dto.CardDto;
import devs10x.mtg.devs10x_mtg_deckbuilder.dto.CardListResponseDto;
import devs10x.mtg.devs10x_mtg_deckbuilder.dto.CardQueryParams;

import java.util.List;

public interface MtgApiService {
    /**
     * Fetch all cards without any filtering
     */
    CardListResponseDto fetchCards();
    
    /**
     * Fetch cards with optional filtering parameters
     * @param queryParams Object containing all query parameters
     */
    CardListResponseDto fetchCards(CardQueryParams queryParams);
} 