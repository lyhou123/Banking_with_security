package co.istad.mobilebankingcstad.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

@Entity
@Table(name="card_type_tbl")
@Data
@Accessors(chain = true)
public class CardType {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private Boolean is_enabled;
    private String description;
}
