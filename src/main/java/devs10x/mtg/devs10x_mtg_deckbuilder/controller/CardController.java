package devs10x.mtg.devs10x_mtg_deckbuilder.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import com.example.mtgdeckbuilder.dto.CardDto;
import com.example.mtgdeckbuilder.dto.CardListResponseDto;
import com.example.mtgdeckbuilder.dto.PaginationDto;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/cards")
public class CardController {

    @GetMapping("")
    public ResponseEntity<CardListResponseDto> getCards(
            @RequestParam(required = false) String set,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "50") int pageSize,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String order,
            @RequestParam(required = false) String rarity,
            @RequestParam(required = false) String mana_cost,
            @RequestParam(required = false) String color,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String name
    ) {
        // Create a mock card
        CardDto card = new CardDto();
        card.setId(1L);
        card.setApiId("external-id-1");
        card.setName("Mock Card");
        // More fields can be set as needed

        List<CardDto> cards = Arrays.asList(card);

        // Create mock pagination details
        PaginationDto pagination = new PaginationDto();
        pagination.setPage(page);
        pagination.setPageSize(pageSize);
        pagination.setTotalPages(10);
        pagination.setTotalRecords(500);

        CardListResponseDto response = new CardListResponseDto();
        response.setCards(cards);
        response.setPagination(pagination);

        return ResponseEntity.ok(response);
    }
} 