package co.istad.mobilebankingcstad.features.useraccount;
import co.istad.mobilebankingcstad.domain.User;
import co.istad.mobilebankingcstad.domain.UserAccount;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount,String> {
    //find count user for checking if user has 5 accounts
    long countByUser(User user);
    Optional<UserAccount>findByAccount_Id(String id);
    Optional<UserAccount>  findByAccount_AccountNumber(String accountNumber);
    List<UserAccount> findByUser_Id(String id);
    @Modifying
    @Transactional
    @Query("UPDATE accounts_tbl u SET u.isDisabled = :status WHERE u.id = :userId")
    int updateBlockedStatusById(String userId, boolean status);

}
