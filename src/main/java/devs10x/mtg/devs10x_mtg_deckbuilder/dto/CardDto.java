package devs10x.mtg.devs10x_mtg_deckbuilder.dto;

import lombok.Data;
import java.util.List;

@Data
public class CardDto {

    // TODO: review if we need to use Long or String for id - check with the entity
    private String id;
    private String apiId;
    private String name;
    private String manaCost;
    private Integer cmc;
    private Integer quantity;
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