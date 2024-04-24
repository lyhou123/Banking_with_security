package co.istad.mobilebankingcstad.features.accounts;
import co.istad.mobilebankingcstad.domain.UserAccount;
import co.istad.mobilebankingcstad.features.accounts.dto.AccountRequest;
import co.istad.mobilebankingcstad.features.accounts.dto.AccountResponse;
import co.istad.mobilebankingcstad.features.accounttype.AccountTypeRepository;
import co.istad.mobilebankingcstad.features.user.UserRepository;
import co.istad.mobilebankingcstad.features.useraccount.UserAccountRepository;
import co.istad.mobilebankingcstad.mapper.AccountMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountMapper accountMapper;
    private final AccountTypeRepository accountTypeRepository;
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final UserAccountRepository userAccountRepository;

    @Override
    public List<AccountResponse> getAllAccounts() {
        return userAccountRepository.findAll().stream()
                .map(accountMapper::mapUserAccountToAccountResponse)
                .toList();
    }

    @Override
    public AccountResponse createAccount(AccountRequest request) {
        var accountTypeCheck=request.accountType().
                equalsIgnoreCase("card");
        System.out.println("check if account type is equals to card "+accountTypeCheck);
        // check account type
        var accountType = accountTypeRepository
                .findByName(request.accountType())
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.BAD_REQUEST,
                                "Account Type : " + request.accountType() + " is not valid! "));
        var owner = userRepository.findById(request.userId())
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.BAD_REQUEST,
                                "User ID = " + request.userId() + " is not a valid user"
                        )
                );
        //check if the user already has 5 account already
        var numberAccount = userAccountRepository.countByUser(owner);
        System.out.println(numberAccount);
        if (numberAccount >= 5) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "User ID = " + request.userId() + " already has 5 accounts"
            );
        }
        
        var account = accountMapper.mapRequestToEntity(request);
        account.setAccountType(accountType);
        // account info from the request
        UserAccount userAccount = new UserAccount()
                .setAccount(account)
                .setUser(owner)
                .setIsDisabled(false);
        userAccountRepository.save(userAccount);
        var accountResponse = accountMapper.mapUserAccountToAccountResponse(userAccount);
//   accountMapper.setUserForAccountResponse(accountResponse,owner);
        return accountResponse;
    }

    @Override
    public AccountResponse findAccountById(String id) {
        var accountId = userAccountRepository.findByAccount_Id(id)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.BAD_REQUEST,
                                "Account ID = " + id + " is not a valid account"
                        )
                );
        return accountMapper.mapUserAccountToAccountResponse(accountId);
    }

    @Override
    public AccountResponse deleteAccountById(String id) {
        var account = userAccountRepository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.BAD_REQUEST,
                                "Account ID = " + id + " is not a valid account"
                        )
                );
        userAccountRepository.delete(account);
        return accountMapper.mapUserAccountToAccountResponse(account);
    }

    @Override
    public AccountResponse findAccountByAccountNumber(String accountNumber) {
        var userAccount = userAccountRepository.findByAccount_AccountNumber(accountNumber)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.BAD_REQUEST,
                                "Account Number = " + accountNumber + " is not a valid account"
                        )
                );
        return accountMapper.mapUserAccountToAccountResponse(userAccount);
    }

    @Override
    public List<AccountResponse> findAccountsByUserId(String userId) {
        var user = userAccountRepository.findByUser_Id(userId);
        return user.stream()
                .map(accountMapper::mapUserAccountToAccountResponse)
                .toList();
    }

    @Override
    public AccountResponse disableAccount(String id) {
        int affectedRow =userAccountRepository.updateBlockedStatusById(id,true);
        if (affectedRow > 0) {
            return accountMapper.mapUserAccountToAccountResponse(
                    userAccountRepository.findByAccount_Id(id)
                            .orElseThrow(
                                    () -> new ResponseStatusException(
                                            HttpStatus.BAD_REQUEST,
                                            "Account ID = " + id + " is not a valid account"
                                    )
                            )
            );
        }else{
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Account ID = " + id + " is not a valid account"
            );
        }
    }

    @Override
    public AccountResponse enableAccount(String id) {
        int affectedRow =userAccountRepository.updateBlockedStatusById(id,false);
        if (affectedRow > 0) {
            return accountMapper.mapUserAccountToAccountResponse(
                    userAccountRepository.findByAccount_Id(id)
                            .orElseThrow(
                                    () -> new ResponseStatusException(
                                            HttpStatus.BAD_REQUEST,
                                            "Account ID = " + id + " is not a valid account"
                                    )
                            )
            );
        }else{
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Account ID = " + id + " is not a valid account"
            );
        }
    }

    @Override
    public AccountResponse createCard(AccountRequest accountRequest) {

        return null;
    }

}
