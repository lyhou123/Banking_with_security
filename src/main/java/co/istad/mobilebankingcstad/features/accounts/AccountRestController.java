package co.istad.mobilebankingcstad.features.accounts;

import co.istad.mobilebankingcstad.features.accounts.dto.AccountRequest;
import co.istad.mobilebankingcstad.features.accounts.dto.AccountResponse;
import co.istad.mobilebankingcstad.features.user.dto.UserResponse;
import co.istad.mobilebankingcstad.utils.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountRestController {

    private final AccountService accountService;

    @PostMapping
    @Operation(summary = "Create account !")
    public BaseResponse<AccountResponse> createAccount(@RequestBody AccountRequest request){
        return BaseResponse.<AccountResponse>createSuccess()
                .setPayload(accountService.createAccount(request));
    }

    @GetMapping
    @Operation(summary = "Get all accounts !")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<List<AccountResponse>> getAllAccounts(){
        return BaseResponse.<List<AccountResponse>>ok()
                .setPayload(accountService.getAllAccounts());
    }

    //get account by id
    @GetMapping("/{id}")
    @Operation(summary = "Get account by id !")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<AccountResponse> getAccountById(
            @Parameter(description = "Account id", required = true, example = "1")
            @PathVariable String id){
        return BaseResponse.<AccountResponse>ok()
                .setPayload(accountService.findAccountById(id));
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Delete account by id !")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<AccountResponse> deleteAccountById(@PathVariable String id)
    {
        return BaseResponse.<AccountResponse>ok()
                .setPayload(accountService.deleteAccountById(id));
    }


    @GetMapping("/accountNumber/{accountNumber}")
    @Operation(summary = "Get account by account number !")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<AccountResponse> getAccountByAccountNumber(
            @Parameter(description = "Account number", required = true, example = "123456789")
            @PathVariable String accountNumber){
        return BaseResponse.<AccountResponse>ok()
                .setPayload(accountService.findAccountByAccountNumber(accountNumber));
    }


    @GetMapping("/userId/{userId}")
    @Operation(summary = "Get accounts by user id !")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<List<AccountResponse>> getAccountsByUserId(
            @Parameter(description = "User id", required = true, example = "1")
            @PathVariable String userId){
        return BaseResponse.<List<AccountResponse>>ok()
                .setPayload(accountService.findAccountsByUserId(userId));
    }


    @PatchMapping("/{id}/disable")
    public BaseResponse<AccountResponse> disableUser(@PathVariable String id){
        return BaseResponse.<AccountResponse>ok()
                .setPayload(accountService.disableAccount(id));
            }


    @PatchMapping("/{id}/enable")
    public BaseResponse<AccountResponse> enableUser(@PathVariable String id){
        return BaseResponse.<AccountResponse>ok()
                .setPayload(accountService.enableAccount(id));
    }

}

