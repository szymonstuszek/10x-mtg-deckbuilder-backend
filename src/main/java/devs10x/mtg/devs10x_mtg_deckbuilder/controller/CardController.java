package devs10x.mtg.devs10x_mtg_deckbuilder.controller;

import devs10x.mtg.devs10x_mtg_deckbuilder.dto.CardDto;
import devs10x.mtg.devs10x_mtg_deckbuilder.dto.CardListResponseDto;
import devs10x.mtg.devs10x_mtg_deckbuilder.service.MtgApiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/cards")
public class CardController {

    private final MtgApiService mtgApiService;

    public CardController(MtgApiService mtgApiService) {
        this.mtgApiService = mtgApiService;
    }

    @GetMapping("")
    public ResponseEntity<CardListResponseDto> getCards() {
        // List<CardDto> cards = mtgApiService.fetchCards();
        CardListResponseDto response =  mtgApiService.fetchCards();
        // response.setCards(cards);
        return ResponseEntity.ok(response);
    }
}