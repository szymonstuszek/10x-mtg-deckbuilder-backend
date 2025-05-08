package devs10x.mtg.devs10x_mtg_deckbuilder.entity;

import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

@Data
@Entity
@Table(name = "cards")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long internalId;

    @Column(name = "api_id", unique = true)
    private String apiId;

    @Column(nullable = false)
    private String name;

    @Column(name = "mana_cost")
    private String manaCost;

    @Column(name = "cmc")
    private Integer cmc; 

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = "colors")
    private List<String> colors;

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = "color_identity")
    private List<String> colorIdentity;

    @Column(name = "type")
    private String type; // Corresponds to "type" in db, often called type_line

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = "types")
    private List<String> types;

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = "subtypes")
    private List<String> subtypes;

    private String rarity;

    @Column(name = "\"set\"") // "set" is a reserved keyword in SQL, quoting if necessary, or ensure table column is also quoted or named differently
    private String set;

    @Column(name = "set_name")
    private String setName;

    @Column(name = "card_text", columnDefinition = "TEXT")
    private String cardText;

    private String artist;

    @Column(name = "number") // Corresponds to collector_number
    private String number;

    private String power;

    private String toughness;

    private String layout;

    @Column(name = "multiverseid")
    private String multiverseid; // Column name in db is all lowercase

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "original_text", columnDefinition = "TEXT")
    private String originalText;

    @Column(name = "original_type")
    private String originalType;
} 