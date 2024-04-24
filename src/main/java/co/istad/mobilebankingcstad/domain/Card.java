package co.istad.mobilebankingcstad.domain;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
@Entity
@Table(name="card_tbl")
@Data
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String card_no;
    private String cardcvv;
    private String card_holder;
    private String card_expiration;
    private LocalDateTime card_issure_at;
    @ManyToOne
    @JoinColumn(name="cardtype_id")
    private CardType cardType;
    @ManyToOne
    @JoinColumn(name="account_id")
    private Account account;
}
