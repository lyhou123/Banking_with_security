package co.istad.mobilebankingcstad.domain;


import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

@Entity
@Table(name = "roles_tbl")
@Data
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    // stuff , admin , ...
    @Column(nullable = false, length = 25)
    private String name;

    @Override
    public String getAuthority() {
        return "ROLE_"+this.name;
    }

    @ManyToMany
    Set<Authority> authorities;
}
