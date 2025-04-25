package devs10x.mtg.devs10x_mtg_deckbuilder.dto;

import lombok.Data;
import java.util.List;

@Data
public class CardDto {
    private Long id;
    private String apiId;
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