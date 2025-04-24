package devs10x.mtg.devs10x_mtg_deckbuilder.controller;

import devs10x.mtg.devs10x_mtg_deckbuilder.dto.DeckDto;
import devs10x.mtg.devs10x_mtg_deckbuilder.dto.CreateDeckDto;
import devs10x.mtg.devs10x_mtg_deckbuilder.dto.UpdateDeckDto;
import devs10x.mtg.devs10x_mtg_deckbuilder.dto.DeckDetailsDto;
import devs10x.mtg.devs10x_mtg_deckbuilder.dto.DeckStatisticsDto;
import devs10x.mtg.devs10x_mtg_deckbuilder.dto.RandomCardResponseDto;
import devs10x.mtg.devs10x_mtg_deckbuilder.dto.CardDto;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/decks")
public class DeckController {

    @GetMapping("")
    public ResponseEntity<List<DeckDto>> getDecks() {
        // Return a mock list of decks for the authenticated user
        List<DeckDto> decks = new ArrayList<>();
        DeckDto deck = new DeckDto();
        deck.setId(1L);
        deck.setDeckName("Sample Deck");
        deck.setDeckFormat("Standard");
        deck.setDeckDescription("A sample deck for demonstration");
        deck.setCreatedAt(Instant.now());
        deck.setUpdatedAt(Instant.now());
        decks.add(deck);
        return ResponseEntity.ok(decks);
    }

    @PostMapping("")
    public ResponseEntity<DeckDto> createDeck(@RequestBody CreateDeckDto createDeckDto) {
        // Basic validation for required fields
        if (createDeckDto.getDeckName() == null || createDeckDto.getDeckName().trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        // Create a new deck with mock data
        DeckDto createdDeck = new DeckDto();
        createdDeck.setId(2L); // mock id
        createdDeck.setDeckName(createDeckDto.getDeckName());
        createdDeck.setDeckFormat(createDeckDto.getDeckFormat());
        createdDeck.setDeckDescription(createDeckDto.getDeckDescription());
        createdDeck.setCreatedAt(Instant.now());
        createdDeck.setUpdatedAt(Instant.now());
        return ResponseEntity.status(201).body(createdDeck);
    }

    @GetMapping("/{deckId}")
    public ResponseEntity<DeckDetailsDto> getDeckDetails(@PathVariable Long deckId) {
        // Create a mock deck details response
        DeckDetailsDto details = new DeckDetailsDto();

        DeckDto deckInfo = new DeckDto();
        deckInfo.setId(deckId);
        deckInfo.setDeckName("Detailed Deck");
        deckInfo.setDeckFormat("Modern");
        deckInfo.setDeckDescription("Deck with detailed view");
        deckInfo.setCreatedAt(Instant.now());
        deckInfo.setUpdatedAt(Instant.now());
        details.setDeckInfo(deckInfo);

        // Create a mock card map (card -> quantity)
        Map<CardDto, Integer> cards = new HashMap<>();
        CardDto card = new CardDto();
        card.setId(1L);
        card.setName("Mock Card");
        cards.put(card, 2);
        details.setCards(cards);

        // Create mock statistics
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

        return ResponseEntity.ok(details);
    }

    @PutMapping("/{deckId}")
    public ResponseEntity<DeckDto> updateDeck(@PathVariable Long deckId, @RequestBody UpdateDeckDto updateDeckDto) {
        // Validate required fields
        if (updateDeckDto.getDeckName() == null || updateDeckDto.getDeckName().trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        // Mock update: return updated deck information
        DeckDto updatedDeck = new DeckDto();
        updatedDeck.setId(deckId);
        updatedDeck.setDeckName(updateDeckDto.getDeckName());
        updatedDeck.setDeckFormat(updateDeckDto.getDeckFormat());
        updatedDeck.setDeckDescription(updateDeckDto.getDeckDescription());
        updatedDeck.setCreatedAt(Instant.now());
        updatedDeck.setUpdatedAt(Instant.now());
        return ResponseEntity.ok(updatedDeck);
    }

    @DeleteMapping("/{deckId}")
    public ResponseEntity<Void> deleteDeck(@PathVariable Long deckId) {
        // For mock implementation, assume the deck is deleted successfully
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{deckId}/stats")
    public ResponseEntity<DeckStatisticsDto> getDeckStats(@PathVariable Long deckId) {
        // Return mock deck statistics
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
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/{deckId}/random")
    public ResponseEntity<RandomCardResponseDto> getRandomNonLandCard(@PathVariable Long deckId) {
        // Return a mock random non-land card
        RandomCardResponseDto randomResponse = new RandomCardResponseDto();
        CardDto card = new CardDto();
        card.setId(1L);
        card.setName("Random Non-Land Card");
        randomResponse.setCard(card);
        return ResponseEntity.ok(randomResponse);
    }
}
