package co.istad.mobilebankingcstad.features.accounttype;

import co.istad.mobilebankingcstad.domain.AccountType;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountTypeRepository extends JpaRepository<AccountType,String> {
    boolean existsByName(String name);
    Optional<AccountType> findByName(String name);

}