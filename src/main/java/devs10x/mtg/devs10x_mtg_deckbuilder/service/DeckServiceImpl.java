package devs10x.mtg.devs10x_mtg_deckbuilder.service;

import devs10x.mtg.devs10x_mtg_deckbuilder.dto.DeckDto;
import devs10x.mtg.devs10x_mtg_deckbuilder.dto.CreateDeckDto;
import devs10x.mtg.devs10x_mtg_deckbuilder.dto.UpdateDeckDto;
import devs10x.mtg.devs10x_mtg_deckbuilder.dto.DeckDetailsDto;
import devs10x.mtg.devs10x_mtg_deckbuilder.dto.DeckStatisticsDto;
import devs10x.mtg.devs10x_mtg_deckbuilder.dto.RandomCardResponseDto;
import devs10x.mtg.devs10x_mtg_deckbuilder.dto.CardDto;
import devs10x.mtg.devs10x_mtg_deckbuilder.entity.Deck;
import devs10x.mtg.devs10x_mtg_deckbuilder.entity.DeckCard;
import devs10x.mtg.devs10x_mtg_deckbuilder.entity.User;
import devs10x.mtg.devs10x_mtg_deckbuilder.entity.Card;
import devs10x.mtg.devs10x_mtg_deckbuilder.repository.DeckRepository;
import devs10x.mtg.devs10x_mtg_deckbuilder.repository.UserRepository;
import devs10x.mtg.devs10x_mtg_deckbuilder.repository.CardRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
        // TODO review
        // dto.setCreatedAt(deck.getCreatedAt());
        // dto.setUpdatedAt(deck.getUpdatedAt());
        return dto;
    }

    private CardDto convertCardToDto(Card card) {
        CardDto cardDto = new CardDto();

        cardDto.setId(""+card.getApiId());
        cardDto.setApiId(card.getApiId());
        cardDto.setName(card.getName());
        cardDto.setManaCost(card.getManaCost());
        cardDto.setCmc(card.getCmc());
        cardDto.setColors(card.getColors());
        cardDto.setColorIdentity(card.getColorIdentity());
        cardDto.setType(card.getType());
        cardDto.setTypes(card.getTypes());
        cardDto.setSubtypes(card.getSubtypes());
        cardDto.setRarity(card.getRarity());
        cardDto.setSet(card.getSet());
        cardDto.setSetName(card.getSetName());
        cardDto.setCardText(card.getCardText());
        cardDto.setArtist(card.getArtist());
        cardDto.setNumber(card.getNumber());
        cardDto.setPower(card.getPower());
        cardDto.setToughness(card.getToughness());
        cardDto.setLayout(card.getLayout());
        cardDto.setMultiverseid(card.getMultiverseid());
        cardDto.setImageUrl(card.getImageUrl());
        cardDto.setOriginalText(card.getOriginalText());
        cardDto.setOriginalType(card.getOriginalType());
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

        // Persist deck to obtain an ID
        deck = deckRepository.save(deck);

        if (createDeckDto.getCards() != null && !createDeckDto.getCards().isEmpty()) {
            for (CardDto cardDto : createDeckDto.getCards()) {
                Card card;

                // review this logic
                int quantity = (cardDto.getQuantity() != null) ? cardDto.getQuantity() : 1;
                if (cardDto.getId() != null) {
                    Optional<Card> cardOpt = cardRepository.findByApiId(cardDto.getId());
                    if (cardOpt.isPresent()) {
                        card = cardOpt.get();
                    } else {
                        card = new Card();
                        card.setApiId(cardDto.getId());
                        card.setName(cardDto.getName());
                        card.setManaCost(cardDto.getManaCost());
                        card.setCmc(cardDto.getCmc());
                        card.setColors(cardDto.getColors());
                        card.setColorIdentity(cardDto.getColorIdentity());
                        card.setType(cardDto.getType());
                        card.setTypes(cardDto.getTypes());
                        card.setSubtypes(cardDto.getSubtypes());
                        card.setRarity(cardDto.getRarity());
                        card.setSet(cardDto.getSet());
                        card.setSetName(cardDto.getSetName());
                        card.setCardText(cardDto.getCardText());
                        card.setArtist(cardDto.getArtist());
                        card.setNumber(cardDto.getNumber());
                        card.setPower(cardDto.getPower());
                        card.setToughness(cardDto.getToughness());
                        card.setLayout(cardDto.getLayout());
                        card.setMultiverseid(cardDto.getMultiverseid());
                        card.setImageUrl(cardDto.getImageUrl());
                        card.setOriginalText(cardDto.getOriginalText());
                        card.setOriginalType(cardDto.getOriginalType());
                        card = cardRepository.save(card);
                    }

                    DeckCard deckCard = new DeckCard();
                    deckCard.setDeck(deck);
                    deckCard.setCard(card);
                    deckCard.setQuantity(quantity);
                    deckCard.getId().setDeckId(deck.getId());
                    deckCard.getId().setCardId(card.getInternalId());
                    deck.getDeckCards().add(deckCard);
                }
            }
            deck = deckRepository.save(deck);
        }

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
            .map(dc -> {
                CardDto cardDto = convertCardToDto(dc.getCard());
                cardDto.setQuantity(dc.getQuantity());
                return cardDto;
            })
            .collect(Collectors.toList()));
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
        Optional<Deck> optionalDeck = deckRepository.findById(deckId);
        RandomCardResponseDto response = new RandomCardResponseDto();

        if (optionalDeck.isPresent()) {
            Deck deck = optionalDeck.get();
            List<Card> nonLandCards = deck.getDeckCards().stream()
                    .map(DeckCard::getCard)
                    .filter(card -> card.getTypes() != null && !card.getTypes().contains("Land"))
                    .collect(Collectors.toList());

            if (!nonLandCards.isEmpty()) {
                Card selectedCard = nonLandCards.get(new Random().nextInt(nonLandCards.size()));
                CardDto cardDto = convertCardToDto(selectedCard); // Use existing conversion method
                response.setCard(cardDto);
            } else {
                response.setCard(null); // No non-land cards in the deck
            }
        } else {
            response.setCard(null); // Deck not found
        }
        return response;
    }
} 