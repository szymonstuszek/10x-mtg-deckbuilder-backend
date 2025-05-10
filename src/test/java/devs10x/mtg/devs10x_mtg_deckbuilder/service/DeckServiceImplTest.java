package devs10x.mtg.devs10x_mtg_deckbuilder.service;

import devs10x.mtg.devs10x_mtg_deckbuilder.dto.CreateDeckDto;
import devs10x.mtg.devs10x_mtg_deckbuilder.dto.DeckDto;
import devs10x.mtg.devs10x_mtg_deckbuilder.dto.DeckDetailsDto;
import devs10x.mtg.devs10x_mtg_deckbuilder.dto.CardDto;
import devs10x.mtg.devs10x_mtg_deckbuilder.entity.Deck;
import devs10x.mtg.devs10x_mtg_deckbuilder.entity.User;
import devs10x.mtg.devs10x_mtg_deckbuilder.repository.CardRepository;
import devs10x.mtg.devs10x_mtg_deckbuilder.repository.DeckRepository;
import devs10x.mtg.devs10x_mtg_deckbuilder.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import devs10x.mtg.devs10x_mtg_deckbuilder.entity.Card;
import devs10x.mtg.devs10x_mtg_deckbuilder.entity.DeckCard;
import devs10x.mtg.devs10x_mtg_deckbuilder.entity.DeckCardId;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import devs10x.mtg.devs10x_mtg_deckbuilder.dto.UpdateDeckDto;
import devs10x.mtg.devs10x_mtg_deckbuilder.dto.DeckStatisticsDto;
import devs10x.mtg.devs10x_mtg_deckbuilder.dto.RandomCardResponseDto;

@ExtendWith(MockitoExtension.class)
class DeckServiceImplTest {

    @Mock
    private DeckRepository deckRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CardRepository cardRepository;

    @InjectMocks
    private DeckServiceImpl deckService;

    private Deck deck1;
    private Deck deck2;

    @BeforeEach
    void setUp() {
        deck1 = new Deck();
        deck1.setId(1L);
        deck1.setDeckName("Test Deck 1");
        deck1.setDeckFormat("Standard");
        deck1.setDeckDescription("Description 1");
        deck1.setCreatedAt(Instant.now());
        deck1.setUpdatedAt(Instant.now());

        deck2 = new Deck();
        deck2.setId(2L);
        deck2.setDeckName("Test Deck 2");
        deck2.setDeckFormat("Modern");
        deck2.setDeckDescription("Description 2");
        deck2.setCreatedAt(Instant.now());
        deck2.setUpdatedAt(Instant.now());
    }

    @Test
    void getDecks_shouldReturnListOfDeckDtos_whenDecksExist() {
        List<Deck> decks = new ArrayList<>();
        decks.add(deck1);
        decks.add(deck2);

        when(deckRepository.findAll()).thenReturn(decks);

        List<DeckDto> result = deckService.getDecks();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Test Deck 1", result.get(0).getDeckName());
        assertEquals("Test Deck 2", result.get(1).getDeckName());
        verify(deckRepository, times(1)).findAll();
    }

    @Test
    void getDecks_shouldReturnEmptyList_whenNoDecksExist() {
        when(deckRepository.findAll()).thenReturn(Collections.emptyList());

        List<DeckDto> result = deckService.getDecks();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(deckRepository, times(1)).findAll();
    }

    @Test
    void createDeck_shouldCreateAndReturnDeckDto_whenValidInput() {
        CreateDeckDto createDeckDto = new CreateDeckDto();
        createDeckDto.setDeckName("New Deck");
        createDeckDto.setDeckFormat("Commander");
        createDeckDto.setDeckDescription("A new commander deck.");
        createDeckDto.setCards(Collections.emptyList()); // No cards for simplicity in this test

        User dummyUser = new User();
        dummyUser.setSub("dummy");
        dummyUser.setEmail("dummy@example.com");

        Deck deckToSave = new Deck();
        deckToSave.setDeckName(createDeckDto.getDeckName());
        deckToSave.setDeckFormat(createDeckDto.getDeckFormat());
        deckToSave.setDeckDescription(createDeckDto.getDeckDescription());
        deckToSave.setUser(dummyUser);
        // Timestamps are set within the service method

        Deck savedDeck = new Deck();
        savedDeck.setId(3L);
        savedDeck.setDeckName(createDeckDto.getDeckName());
        savedDeck.setDeckFormat(createDeckDto.getDeckFormat());
        savedDeck.setDeckDescription(createDeckDto.getDeckDescription());
        savedDeck.setUser(dummyUser);
        savedDeck.setCreatedAt(Instant.now());
        savedDeck.setUpdatedAt(Instant.now());

        when(userRepository.findById("dummy")).thenReturn(Optional.of(dummyUser));
        // We need to mock two save calls for deckRepository
        // First save (before adding cards, to get an ID)
        when(deckRepository.save(any(Deck.class))).thenAnswer(invocation -> {
            Deck deckBeingSaved = invocation.getArgument(0);
            // Simulate ID generation only if ID is not already set (it won't be on the first save)
            if (deckBeingSaved.getId() == null) {
                deckBeingSaved.setId(3L); // Assign a dummy ID for the first save
            }
            return deckBeingSaved;
        });

        DeckDto result = deckService.createDeck(createDeckDto);

        assertNotNull(result);
        assertEquals(3L, result.getId());
        assertEquals("New Deck", result.getDeckName());
        assertEquals("Commander", result.getDeckFormat());
        assertEquals("A new commander deck.", result.getDeckDescription());

        verify(userRepository, times(1)).findById("dummy");
        verify(userRepository, never()).save(any(User.class)); // User already exists
        verify(deckRepository, times(1)).save(any(Deck.class)); // Saved once before cards, once after (even if no cards)
    }

    @Test
    void createDeck_shouldCreateUserAndDeck_whenUserDoesNotExist() {
        CreateDeckDto createDeckDto = new CreateDeckDto();
        createDeckDto.setDeckName("Another New Deck");
        createDeckDto.setDeckFormat("Pauper");
        createDeckDto.setDeckDescription("A new pauper deck.");

        User newUser = new User();
        newUser.setSub("dummy");
        newUser.setEmail("dummy@example.com");

        Deck savedDeck = new Deck();
        savedDeck.setId(4L);
        savedDeck.setDeckName(createDeckDto.getDeckName());
        // ... (set other properties as needed for the DTO conversion)

        when(userRepository.findById("dummy")).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(newUser);
        when(deckRepository.save(any(Deck.class))).thenAnswer(invocation -> {
            Deck deckBeingSaved = invocation.getArgument(0);
            if (deckBeingSaved.getId() == null) {
                deckBeingSaved.setId(4L); 
            }
            return deckBeingSaved;
        });

        DeckDto result = deckService.createDeck(createDeckDto);

        assertNotNull(result);
        assertEquals(4L, result.getId());
        assertEquals("Another New Deck", result.getDeckName());

        verify(userRepository, times(1)).findById("dummy");
        verify(userRepository, times(1)).save(any(User.class));
        verify(deckRepository, times(1)).save(any(Deck.class));
    }

    // TODO: Add tests for createDeck with cards

    @Test
    void getDeckDetails_shouldReturnDeckDetails_whenDeckExists() {
        Card cardEntity1 = new Card();
        cardEntity1.setInternalId(101L);
        cardEntity1.setApiId("scryfall-1");
        cardEntity1.setName("Test Card 1");

        DeckCard deckCard1 = new DeckCard();
        DeckCardId deckCardId1 = new DeckCardId();
        deckCardId1.setDeckId(deck1.getId());
        deckCardId1.setCardId(cardEntity1.getInternalId());
        deckCard1.setId(deckCardId1);
        deckCard1.setDeck(deck1);
        deckCard1.setCard(cardEntity1);
        deckCard1.setQuantity(2);
        deck1.getDeckCards().add(deckCard1);

        when(deckRepository.findById(1L)).thenReturn(Optional.of(deck1));

        DeckDetailsDto result = deckService.getDeckDetails(1L);

        assertNotNull(result);
        assertNotNull(result.getDeckInfo());
        assertEquals(1L, result.getDeckInfo().getId());
        assertEquals("Test Deck 1", result.getDeckInfo().getDeckName());
        assertNotNull(result.getCards());
        assertEquals(1, result.getCards().size());
        assertEquals("Test Card 1", result.getCards().get(0).getName());
        assertEquals(2, result.getCards().get(0).getQuantity());

        verify(deckRepository, times(1)).findById(1L);
    }

    @Test
    void getDeckDetails_shouldReturnEmptyDto_whenDeckNotFound() {
        when(deckRepository.findById(99L)).thenReturn(Optional.empty());

        DeckDetailsDto result = deckService.getDeckDetails(99L);

        assertNotNull(result);
        assertNull(result.getDeckInfo());
        assertNull(result.getCards());
        assertNull(result.getStatistics());

        verify(deckRepository, times(1)).findById(99L);
    }

    @Test
    void updateDeck_shouldUpdateAndReturnDeckDto_whenDeckExists() {
        UpdateDeckDto updateDeckDto = new UpdateDeckDto();
        updateDeckDto.setDeckName("Updated Deck Name");
        updateDeckDto.setDeckFormat("Updated Format");
        updateDeckDto.setDeckDescription("Updated Description");

        Deck existingDeck = new Deck();
        existingDeck.setId(1L);
        existingDeck.setDeckName("Old Name");
        existingDeck.setDeckFormat("Old Format");
        existingDeck.setDeckDescription("Old Description");
        existingDeck.setCreatedAt(Instant.now().minusSeconds(3600)); // an hour ago
        // User is not strictly needed for this specific test logic but good for completeness
        User dummyUser = new User();
        dummyUser.setSub("dummy");
        existingDeck.setUser(dummyUser);


        Deck updatedDeckEntity = new Deck();
        updatedDeckEntity.setId(1L);
        updatedDeckEntity.setDeckName(updateDeckDto.getDeckName());
        updatedDeckEntity.setDeckFormat(updateDeckDto.getDeckFormat());
        updatedDeckEntity.setDeckDescription(updateDeckDto.getDeckDescription());
        updatedDeckEntity.setUser(dummyUser);
        updatedDeckEntity.setCreatedAt(existingDeck.getCreatedAt()); // CreatedAt should not change
        // updatedAt will be set by the service

        when(deckRepository.findById(1L)).thenReturn(Optional.of(existingDeck));
        when(deckRepository.save(any(Deck.class))).thenAnswer(invocation -> {
            Deck saved = invocation.getArgument(0);
            // Ensure the ID is preserved and other fields are updated
            assertEquals(1L, saved.getId());
            assertEquals(updateDeckDto.getDeckName(), saved.getDeckName());
            assertNotNull(saved.getUpdatedAt()); // Check that updatedAt is set
            assertTrue(saved.getUpdatedAt().isAfter(existingDeck.getCreatedAt()));
            return saved; // Return the argument itself as it's modified in place by the service before save
        });

        DeckDto result = deckService.updateDeck(1L, updateDeckDto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Updated Deck Name", result.getDeckName());
        assertEquals("Updated Format", result.getDeckFormat());
        assertEquals("Updated Description", result.getDeckDescription());

        verify(deckRepository, times(1)).findById(1L);
        verify(deckRepository, times(1)).save(any(Deck.class));
    }

    @Test
    void updateDeck_shouldReturnEmptyDto_whenDeckNotFound() {
        UpdateDeckDto updateDeckDto = new UpdateDeckDto();
        updateDeckDto.setDeckName("NonExistent Update");

        when(deckRepository.findById(99L)).thenReturn(Optional.empty());

        DeckDto result = deckService.updateDeck(99L, updateDeckDto);

        assertNotNull(result); // Service returns new DeckDto()
        assertNull(result.getId()); // Default DTO will have null fields
        assertNull(result.getDeckName());

        verify(deckRepository, times(1)).findById(99L);
        verify(deckRepository, never()).save(any(Deck.class));
    }

    @Test
    void deleteDeck_shouldCallRepositoryDelete() {
        Long deckIdToDelete = 1L;

        // Mocking deckRepository.deleteById to do nothing as it's a void method
        doNothing().when(deckRepository).deleteById(deckIdToDelete);

        deckService.deleteDeck(deckIdToDelete);

        // Verify that deleteById was called exactly once with the correct ID
        verify(deckRepository, times(1)).deleteById(deckIdToDelete);
    }

    @Test
    void deleteDeck_shouldHandleNonExistentDeckGracefully() {
        Long nonExistentDeckId = 99L;

        // Spring Data JPA deleteById doesn't throw an exception if the ID doesn't exist.
        // It simply does nothing. So, we just verify it's called.
        doNothing().when(deckRepository).deleteById(nonExistentDeckId);

        deckService.deleteDeck(nonExistentDeckId);

        verify(deckRepository, times(1)).deleteById(nonExistentDeckId);
    }

    @Test
    void getDeckStats_shouldReturnEmptyStatsForNow() {
        // The current implementation of getDeckStats returns a new DeckStatisticsDto
        // with default/empty values, regardless of the deckId.
        // This test verifies that behavior.

        Long deckId = 1L;
        // No mocking needed for deckRepository for this method as it's not used by the current implementation

        DeckStatisticsDto result = deckService.getDeckStats(deckId);

        assertNotNull(result);
        assertEquals(0, result.getTotalCards());
        assertNull(result.getTypes());
        assertNull(result.getManaCurve());
        assertNull(result.getColors());

        // If the implementation changes to fetch deck and calculate stats,
        // then deckRepository.findById would need to be mocked and verified.
        // For now, verify no interaction with deckRepository.
        verifyNoInteractions(deckRepository); // Or more specifically, verify(deckRepository, never()).findById(anyLong());
    }

    private Card createTestCard(Long internalId, String apiId, String name, List<String> types) {
        Card card = new Card();
        card.setInternalId(internalId);
        card.setApiId(apiId);
        card.setName(name);
        card.setTypes(types);
        // Set other necessary fields if convertCardToDto uses them
        card.setManaCost("{R}");
        card.setCmc(1);
        card.setColorIdentity(Collections.singletonList("R"));
        card.setColors(Collections.singletonList("Red"));
        card.setRarity("Common");
        card.setSet("XYZ");
        card.setSetName("Test Set");
        card.setImageUrl("http://example.com/image.png");
        return card;
    }

    private DeckCard createDeckCard(Deck deck, Card card, int quantity) {
        DeckCard deckCard = new DeckCard();
        DeckCardId id = new DeckCardId();
        id.setDeckId(deck.getId());
        id.setCardId(card.getInternalId());
        deckCard.setId(id);
        deckCard.setDeck(deck);
        deckCard.setCard(card);
        deckCard.setQuantity(quantity);
        return deckCard;
    }

    @Test
    void getRandomNonLandCard_shouldReturnRandomNonLandCard_whenDeckHasNonLandCards() {
        Long deckId = 1L;
        Deck testDeck = new Deck();
        testDeck.setId(deckId);

        Card landCard = createTestCard(101L, "land-1", "Mountain", Collections.singletonList("Land"));
        Card creatureCard = createTestCard(102L, "creature-1", "Goblin Guide", Collections.singletonList("Creature"));
        Card spellCard = createTestCard(103L, "spell-1", "Lightning Bolt", Collections.singletonList("Instant"));

        testDeck.getDeckCards().add(createDeckCard(testDeck, landCard, 10));
        testDeck.getDeckCards().add(createDeckCard(testDeck, creatureCard, 4));
        testDeck.getDeckCards().add(createDeckCard(testDeck, spellCard, 4));

        when(deckRepository.findById(deckId)).thenReturn(Optional.of(testDeck));

        RandomCardResponseDto result = deckService.getRandomNonLandCard(deckId);

        assertNotNull(result);
        assertNotNull(result.getCard());
        assertFalse(result.getCard().getTypes().contains("Land"));
        assertTrue(result.getCard().getName().equals("Goblin Guide") || result.getCard().getName().equals("Lightning Bolt"));

        verify(deckRepository, times(1)).findById(deckId);
    }

    @Test
    void getRandomNonLandCard_shouldReturnNullCard_whenDeckHasOnlyLandCards() {
        Long deckId = 2L;
        Deck testDeck = new Deck();
        testDeck.setId(deckId);

        Card landCard1 = createTestCard(201L, "land-2", "Island", Collections.singletonList("Land"));
        Card landCard2 = createTestCard(202L, "land-3", "Forest", Collections.singletonList("Land"));

        testDeck.getDeckCards().add(createDeckCard(testDeck, landCard1, 10));
        testDeck.getDeckCards().add(createDeckCard(testDeck, landCard2, 10));

        when(deckRepository.findById(deckId)).thenReturn(Optional.of(testDeck));

        RandomCardResponseDto result = deckService.getRandomNonLandCard(deckId);

        assertNotNull(result);
        assertNull(result.getCard());

        verify(deckRepository, times(1)).findById(deckId);
    }

    @Test
    void getRandomNonLandCard_shouldReturnNullCard_whenDeckIsEmpty() {
        Long deckId = 3L;
        Deck testDeck = new Deck();
        testDeck.setId(deckId);
        // Deck has no cards

        when(deckRepository.findById(deckId)).thenReturn(Optional.of(testDeck));

        RandomCardResponseDto result = deckService.getRandomNonLandCard(deckId);

        assertNotNull(result);
        assertNull(result.getCard());

        verify(deckRepository, times(1)).findById(deckId);
    }

    @Test
    void getRandomNonLandCard_shouldReturnNullCard_whenDeckNotFound() {
        Long deckId = 99L;
        when(deckRepository.findById(deckId)).thenReturn(Optional.empty());

        RandomCardResponseDto result = deckService.getRandomNonLandCard(deckId);

        assertNotNull(result);
        assertNull(result.getCard());

        verify(deckRepository, times(1)).findById(deckId);
    }
} 