package devs10x.mtg.devs10x_mtg_deckbuilder.service;

import devs10x.mtg.devs10x_mtg_deckbuilder.dto.DeckDto;
import devs10x.mtg.devs10x_mtg_deckbuilder.dto.CreateDeckDto;
import devs10x.mtg.devs10x_mtg_deckbuilder.dto.UpdateDeckDto;
import devs10x.mtg.devs10x_mtg_deckbuilder.dto.DeckDetailsDto;
import devs10x.mtg.devs10x_mtg_deckbuilder.dto.DeckStatisticsDto;
import devs10x.mtg.devs10x_mtg_deckbuilder.dto.RandomCardResponseDto;
import devs10x.mtg.devs10x_mtg_deckbuilder.dto.CardDto;
import devs10x.mtg.devs10x_mtg_deckbuilder.entity.Deck;
import devs10x.mtg.devs10x_mtg_deckbuilder.entity.User;
import devs10x.mtg.devs10x_mtg_deckbuilder.entity.Card;
import devs10x.mtg.devs10x_mtg_deckbuilder.repository.DeckRepository;
import devs10x.mtg.devs10x_mtg_deckbuilder.repository.UserRepository;
import devs10x.mtg.devs10x_mtg_deckbuilder.repository.CardRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class DeckServiceImpl implements DeckService {

    private final DeckRepository deckRepository;
    private final UserRepository userRepository;
    private final CardRepository cardRepository;

    public DeckServiceImpl(DeckRepository deckRepository, UserRepository userRepository, CardRepository cardRepository) {
        this.deckRepository = deckRepository;
        this.userRepository = userRepository;
        this.cardRepository = cardRepository;
    }

    private DeckDto convertToDto(Deck deck) {
        DeckDto dto = new DeckDto();
        dto.setId(deck.getId());
        dto.setDeckName(deck.getDeckName());
        dto.setDeckFormat(deck.getDeckFormat());
        dto.setDeckDescription(deck.getDeckDescription());
        dto.setCreatedAt(deck.getCreatedAt());
        dto.setUpdatedAt(deck.getUpdatedAt());
        return dto;
    }

    private CardDto convertCardToDto(Card card) {
        CardDto cardDto = new CardDto();
        cardDto.setId(card.getId());
        cardDto.setName(card.getName());
        cardDto.setApiId(card.getApiId());
        cardDto.setManaCost(card.getManaCost());
        cardDto.setCmc(card.getCmc());
        return cardDto;
    }

    @Override
    public List<DeckDto> getDecks() {
        List<Deck> decks = deckRepository.findAll();
        return decks.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public DeckDto createDeck(CreateDeckDto createDeckDto) {
        Deck deck = new Deck();
        deck.setDeckName(createDeckDto.getDeckName());
        deck.setDeckFormat(createDeckDto.getDeckFormat());
        deck.setDeckDescription(createDeckDto.getDeckDescription());
        deck.setCreatedAt(Instant.now());
        deck.setUpdatedAt(Instant.now());

        // Use a dummy user for demonstration
        String dummyUserSub = "dummy";
        Optional<User> userOpt = userRepository.findById(dummyUserSub);
        User user;
        if (userOpt.isPresent()) {
            user = userOpt.get();
        } else {
            user = new User();
            user.setSub(dummyUserSub);
            user.setEmail("dummy@example.com");
            user = userRepository.save(user);
        }
        deck.setUser(user);

        deck = deckRepository.save(deck);
        return convertToDto(deck);
    }

    @Override
    public DeckDetailsDto getDeckDetails(Long deckId) {
        Optional<Deck> optionalDeck = deckRepository.findById(deckId);
        if (!optionalDeck.isPresent()) {
            return new DeckDetailsDto(); // Return an empty object instead of null
        }
        
        Deck deck = optionalDeck.get();
        DeckDetailsDto details = new DeckDetailsDto();
        details.setDeckInfo(convertToDto(deck));

        details.setCards(deck.getDeckCards()
            .stream()
            .collect(Collectors.toMap(dc -> convertCardToDto(dc.getCard()), dc -> dc.getQuantity())));
        details.setStatistics(null);
        return details;
    }

    @Override
    public DeckDto updateDeck(Long deckId, UpdateDeckDto updateDeckDto) {
        Optional<Deck> optionalDeck = deckRepository.findById(deckId);
        if (!optionalDeck.isPresent()) {
            return new DeckDto(); // Return an empty object instead of null
        }
        Deck deck = optionalDeck.get();
        deck.setDeckName(updateDeckDto.getDeckName());
        deck.setDeckFormat(updateDeckDto.getDeckFormat());
        deck.setDeckDescription(updateDeckDto.getDeckDescription());
        deck.setUpdatedAt(Instant.now());
        deck = deckRepository.save(deck);
        return convertToDto(deck);
    }

    @Override
    public void deleteDeck(Long deckId) {
        deckRepository.deleteById(deckId);
    }

    @Override
    public DeckStatisticsDto getDeckStats(Long deckId) {
        // For demonstration, return an empty statistics object
        DeckStatisticsDto stats = new DeckStatisticsDto();
        stats.setTotalCards(0);
        stats.setTypes(null);
        stats.setManaCurve(null);
        stats.setColors(null);
        return stats;
    }

    @Override
    public RandomCardResponseDto getRandomNonLandCard(Long deckId) {
        List<Card> cards = cardRepository.findAll(); // In a real app, filter based on deck and type
        RandomCardResponseDto response = new RandomCardResponseDto();
        if (!cards.isEmpty()) {
            Card card = cards.get(new Random().nextInt(cards.size()));
            CardDto cardDto = new CardDto();
            cardDto.setId(card.getId());
            cardDto.setName(card.getName());
            cardDto.setApiId(card.getApiId());
            cardDto.setManaCost(card.getManaCost());
            cardDto.setCmc(card.getCmc());
            response.setCard(cardDto);
        } else {
            response.setCard(null);
        }
        return response;
    }
} 