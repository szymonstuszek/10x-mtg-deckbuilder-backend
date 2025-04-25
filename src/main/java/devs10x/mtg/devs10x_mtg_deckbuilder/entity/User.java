package devs10x.mtg.devs10x_mtg_deckbuilder.entity;

import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    private String sub;
    
    private String email;
} 