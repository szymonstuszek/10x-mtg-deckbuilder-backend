/*
 * DTO and Command Model Type Definitions
 * 
 * These classes represent the data structures for transferring data between the API and the service layer.
 * They are derived from the underlying database models defined in the db-plan and align with the API plan requirements.
 * Lombok annotations (like @Data) are used here for boilerplate code reduction.
 * 
 * Note: In a real application, you might use validation annotations (e.g., @NotNull, @Size) and mapping frameworks (e.g., MapStruct) for converting between entity and DTO/command types.
 */

package com.example.mtgdeckbuilder.dto;

import java.time.Instant;
import java.util.List;
import java.util.Map;

// Lombok annotations for automatic getters, setters, toString, equals, and hashCode
import lombok.Data;


// 1. User DTOs

@Data
public class UserDto {
    // Represents a user record from the Users table
    private String sub;
    private String email;
}

// 2. Card DTOs

@Data
public class CardDto {
    // DTO representing a Magic: The Gathering card (read-only data from external API or our DB)
    private Long id; // internal id, if applicable
    private String apiId; // external API id
    private String name;
    private String manaCost;
    private Double cmc;
    private List<String> colors;
    private List<String> colorIdentity;
    private String type;
    private List<String> types;
    private List<String> subtypes;
    private String rarity;
    private String set;
    private String setName;
    private String cardText;
    private String artist;
    private String number;
    private String power;
    private String toughness;
    private String layout;
    private String multiverseid;
    private String imageUrl;
    private String originalText;
    private String originalType;
}

// 3. Pagination and Card List Response DTOs

@Data
public class PaginationDto {
    private int page;
    private int pageSize;
    private int totalPages;
    private long totalRecords;
}

@Data
public class CardListResponseDto {
    // Response model for card listing endpoint (/cards)
    private List<CardDto> cards;
    private PaginationDto pagination;
}

// 4. Deck DTOs

@Data
public class DeckDto {
    // Basic deck information for listing decks
    private Long id;
    private String deckName;
    private String deckFormat;
    private String deckDescription;
    private Instant createdAt;
    private Instant updatedAt;
}

@Data
public class DeckDetailsDto {
    // Detailed deck view including basic deck info, cards and statistics
    private DeckDto deckInfo;
    private Map<CardDto, Integer> cards; // Map of card to quantity
    private DeckStatisticsDto statistics;
}

@Data
public class DeckStatisticsDto {
    // DTO for deck statistics such as card count, mana curve, types, etc.
    private int totalCards;
    private Map<String, Integer> types;
    private Map<String, Integer> manaCurve;
    private Map<String, Integer> colors;
}

@Data
public class CreateDeckDto {
    // DTO for creating a new deck
    private String deckName;
    private String deckFormat;
    private String deckDescription;
    // Optional initial cards for the deck - each includes card details and quantity
    private List<CardDto> cards;
}

@Data
public class UpdateDeckDto {
    // DTO for updating deck properties and card list
    private Long id;
    private String deckName;
    private String deckFormat;
    private String deckDescription;
    // Full list of deck cards to update (this replaces the current deck card list)
    private List<CardDto> cards;
}

// 5. Random Card Response

@Data
public class RandomCardResponseDto {
    // Response for retrieving a random non-land card from a deck
    private CardDto card;
} 