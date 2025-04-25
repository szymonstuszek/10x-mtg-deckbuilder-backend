package devs10x.mtg.devs10x_mtg_deckbuilder.service;

import devs10x.mtg.devs10x_mtg_deckbuilder.dto.CardDto;
import devs10x.mtg.devs10x_mtg_deckbuilder.dto.CardListResponseDto;

import java.util.List;

public interface MtgApiService {
    CardListResponseDto fetchCards();
} 