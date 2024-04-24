package co.istad.mobilebankingcstad.features.accounts;

import co.istad.mobilebankingcstad.features.accounts.dto.AccountRequest;
import co.istad.mobilebankingcstad.features.accounts.dto.AccountResponse;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

public interface AccountService {
    List<AccountResponse>  getAllAccounts();
    AccountResponse createAccount(AccountRequest request);
    AccountResponse findAccountById(String id);
    AccountResponse deleteAccountById(String id);
    AccountResponse findAccountByAccountNumber(String accountNumber);
     List<AccountResponse> findAccountsByUserId(String userId);
     AccountResponse disableAccount(String id);
     AccountResponse enableAccount(String id);
     AccountResponse createCard(AccountRequest accountRequest);
}
