package devs10x.mtg.devs10x_mtg_deckbuilder.service;

import devs10x.mtg.devs10x_mtg_deckbuilder.dto.DeckDto;
import devs10x.mtg.devs10x_mtg_deckbuilder.dto.CreateDeckDto;
import devs10x.mtg.devs10x_mtg_deckbuilder.dto.UpdateDeckDto;
import devs10x.mtg.devs10x_mtg_deckbuilder.dto.DeckDetailsDto;
import devs10x.mtg.devs10x_mtg_deckbuilder.dto.DeckStatisticsDto;
import devs10x.mtg.devs10x_mtg_deckbuilder.dto.RandomCardResponseDto;
import devs10x.mtg.devs10x_mtg_deckbuilder.dto.CardDto;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DeckServiceImpl implements DeckService {

    @Override
    public List<DeckDto> getDecks() {
        List<DeckDto> decks = new ArrayList<>();
        DeckDto deck = new DeckDto();
        deck.setId(1L);
        deck.setDeckName("Sample Deck");
        deck.setDeckFormat("Standard");
        deck.setDeckDescription("A sample deck for demonstration");
        deck.setCreatedAt(Instant.now());
        deck.setUpdatedAt(Instant.now());
        decks.add(deck);
        return decks;
    }

    @Override
    public DeckDto createDeck(CreateDeckDto createDeckDto) {
        DeckDto deck = new DeckDto();
        deck.setId(2L); // mock id
        deck.setDeckName(createDeckDto.getDeckName());
        deck.setDeckFormat(createDeckDto.getDeckFormat());
        deck.setDeckDescription(createDeckDto.getDeckDescription());
        deck.setCreatedAt(Instant.now());
        deck.setUpdatedAt(Instant.now());
        return deck;
    }

    @Override
    public DeckDetailsDto getDeckDetails(Long deckId) {
        DeckDetailsDto details = new DeckDetailsDto();

        DeckDto deck = new DeckDto();
        deck.setId(deckId);
        deck.setDeckName("Detailed Deck");
        deck.setDeckFormat("Modern");
        deck.setDeckDescription("Deck with detailed view");
        deck.setCreatedAt(Instant.now());
        deck.setUpdatedAt(Instant.now());
        details.setDeckInfo(deck);

        // Create a dummy card map (card -> quantity)
        Map<CardDto, Integer> cards = new HashMap<>();
        CardDto card = new CardDto();
        card.setId(1L);
        card.setName("Mock Card");
        cards.put(card, 2);
        details.setCards(cards);

        // Create dummy deck statistics
        DeckStatisticsDto statistics = new DeckStatisticsDto();
        statistics.setTotalCards(2);
        Map<String, Integer> types = new HashMap<>();
        types.put("Creature", 2);
        statistics.setTypes(types);
        Map<String, Integer> manaCurve = new HashMap<>();
        manaCurve.put("2", 1);
        manaCurve.put("3", 1);
        statistics.setManaCurve(manaCurve);
        Map<String, Integer> colors = new HashMap<>();
        colors.put("Blue", 1);
        colors.put("Red", 1);
        statistics.setColors(colors);
        details.setStatistics(statistics);

        return details;
    }

    @Override
    public DeckDto updateDeck(Long deckId, UpdateDeckDto updateDeckDto) {
        DeckDto deck = new DeckDto();
        deck.setId(deckId);
        deck.setDeckName(updateDeckDto.getDeckName());
        deck.setDeckFormat(updateDeckDto.getDeckFormat());
        deck.setDeckDescription(updateDeckDto.getDeckDescription());
        deck.setCreatedAt(Instant.now());
        deck.setUpdatedAt(Instant.now());
        return deck;
    }

    @Override
    public void deleteDeck(Long deckId) {
        // Mock delete: do nothing
    }

    @Override
    public DeckStatisticsDto getDeckStats(Long deckId) {
        DeckStatisticsDto stats = new DeckStatisticsDto();
        stats.setTotalCards(2);
        Map<String, Integer> types = new HashMap<>();
        types.put("Creature", 2);
        stats.setTypes(types);
        Map<String, Integer> manaCurve = new HashMap<>();
        manaCurve.put("2", 1);
        manaCurve.put("3", 1);
        stats.setManaCurve(manaCurve);
        Map<String, Integer> colors = new HashMap<>();
        colors.put("Blue", 1);
        colors.put("Red", 1);
        stats.setColors(colors);
        return stats;
    }

    @Override
    public RandomCardResponseDto getRandomNonLandCard(Long deckId) {
        RandomCardResponseDto response = new RandomCardResponseDto();
        CardDto card = new CardDto();
        card.setId(1L);
        card.setName("Random Non-Land Card");
        response.setCard(card);
        return response;
    }
} 