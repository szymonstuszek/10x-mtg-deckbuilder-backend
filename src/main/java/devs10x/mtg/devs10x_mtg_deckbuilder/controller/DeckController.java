package devs10x.mtg.devs10x_mtg_deckbuilder.controller;

import devs10x.mtg.devs10x_mtg_deckbuilder.dto.DeckDto;
import devs10x.mtg.devs10x_mtg_deckbuilder.dto.CreateDeckDto;
import devs10x.mtg.devs10x_mtg_deckbuilder.dto.UpdateDeckDto;
import devs10x.mtg.devs10x_mtg_deckbuilder.dto.DeckDetailsDto;
import devs10x.mtg.devs10x_mtg_deckbuilder.dto.DeckStatisticsDto;
import devs10x.mtg.devs10x_mtg_deckbuilder.dto.RandomCardResponseDto;
import devs10x.mtg.devs10x_mtg_deckbuilder.service.DeckService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/decks")
public class DeckController {

    private final DeckService deckService;

    public DeckController(DeckService deckService) {
        this.deckService = deckService;
    }

    @GetMapping("")
    public ResponseEntity<List<DeckDto>> getDecks() {
        return ResponseEntity.ok(deckService.getDecks());
    }

    @PostMapping("")
    public ResponseEntity<DeckDto> createDeck(@RequestBody CreateDeckDto createDeckDto) {
        if (createDeckDto.getDeckName() == null || createDeckDto.getDeckName().trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        DeckDto createdDeck = deckService.createDeck(createDeckDto);
        return ResponseEntity.status(201).body(createdDeck);
    }

    

    @GetMapping("/{deckId}")
    public ResponseEntity<DeckDetailsDto> getDeckDetails(@PathVariable Long deckId) {
        DeckDetailsDto details = deckService.getDeckDetails(deckId);
        return ResponseEntity.ok(details);
    }

    @PutMapping("/{deckId}")
    public ResponseEntity<DeckDto> updateDeck(@PathVariable Long deckId, @RequestBody UpdateDeckDto updateDeckDto) {
        if (updateDeckDto.getDeckName() == null || updateDeckDto.getDeckName().trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        DeckDto updatedDeck = deckService.updateDeck(deckId, updateDeckDto);
        return ResponseEntity.ok(updatedDeck);
    }

    @DeleteMapping("/{deckId}")
    public ResponseEntity<Void> deleteDeck(@PathVariable Long deckId) {
        deckService.deleteDeck(deckId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{deckId}/stats")
    public ResponseEntity<DeckStatisticsDto> getDeckStats(@PathVariable Long deckId) {
        DeckStatisticsDto stats = deckService.getDeckStats(deckId);
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/{deckId}/random")
    public ResponseEntity<RandomCardResponseDto> getRandomNonLandCard(@PathVariable Long deckId) {
        RandomCardResponseDto randomResponse = deckService.getRandomNonLandCard(deckId);
        return ResponseEntity.ok(randomResponse);
    }
}
