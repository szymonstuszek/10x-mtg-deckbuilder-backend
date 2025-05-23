package devs10x.mtg.devs10x_mtg_deckbuilder.controller;

import devs10x.mtg.devs10x_mtg_deckbuilder.dto.CardDto;
import devs10x.mtg.devs10x_mtg_deckbuilder.dto.CardListResponseDto;
import devs10x.mtg.devs10x_mtg_deckbuilder.dto.CardQueryParams;
import devs10x.mtg.devs10x_mtg_deckbuilder.service.MtgApiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import org.springframework.web.bind.annotation.RequestParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/cards")
public class CardController {

    private static final Logger logger = LoggerFactory.getLogger(CardController.class);
    private final MtgApiService mtgApiService;

    public CardController(MtgApiService mtgApiService) {
        this.mtgApiService = mtgApiService;
    }

    @GetMapping("")
    public ResponseEntity<CardListResponseDto> getCards(CardQueryParams queryParams) {
        logger.info("getCards endpoint called with parameters: {}", queryParams);
        CardListResponseDto response = mtgApiService.fetchCards(queryParams);
        return ResponseEntity.ok(response);
    }
}