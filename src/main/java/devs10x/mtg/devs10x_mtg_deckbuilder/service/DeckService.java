package devs10x.mtg.devs10x_mtg_deckbuilder.service;

import devs10x.mtg.devs10x_mtg_deckbuilder.dto.DeckDto;
import devs10x.mtg.devs10x_mtg_deckbuilder.dto.CreateDeckDto;
import devs10x.mtg.devs10x_mtg_deckbuilder.dto.UpdateDeckDto;
import devs10x.mtg.devs10x_mtg_deckbuilder.dto.DeckDetailsDto;
import devs10x.mtg.devs10x_mtg_deckbuilder.dto.DeckStatisticsDto;
import devs10x.mtg.devs10x_mtg_deckbuilder.dto.RandomCardResponseDto;

import java.util.List;

public interface DeckService {

    List<DeckDto> getDecks();

    DeckDto createDeck(CreateDeckDto createDeckDto);

    DeckDetailsDto getDeckDetails(Long deckId);

    DeckDto updateDeck(Long deckId, UpdateDeckDto updateDeckDto);

    void deleteDeck(Long deckId);

    DeckStatisticsDto getDeckStats(Long deckId);

    RandomCardResponseDto getRandomNonLandCard(Long deckId);

} 