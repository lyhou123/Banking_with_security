package co.istad.mobilebankingcstad.features.auth;

import co.istad.mobilebankingcstad.features.auth.dto.AuthRequest;
import co.istad.mobilebankingcstad.features.auth.dto.AuthRespone;
import co.istad.mobilebankingcstad.features.auth.dto.RefresTokenRequest;
import co.istad.mobilebankingcstad.features.user.UserService;
import co.istad.mobilebankingcstad.features.user.dto.UserRequest;
import co.istad.mobilebankingcstad.features.user.dto.UserResponse;
import co.istad.mobilebankingcstad.utils.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController

@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
@SecurityRequirements(value = {})
public class AuthRestController {

    private final AuthServiceImpl authService;

    private final UserService userService;

    @PostMapping("/login")
    public BaseResponse<AuthRespone> login(@RequestBody AuthRequest authRequest)
    {
        return BaseResponse.<AuthRespone>ok()
                .setPayload(authService.login(authRequest));
    }

   @PostMapping("/signup")
    public String signup()
    {
        return "signup";
    }

    @PostMapping("/refresh")
    public BaseResponse<AuthRespone> refreshToken(@RequestBody RefresTokenRequest refresTokenRequest)
    {
        return BaseResponse.<AuthRespone>ok()
                .setPayload(authService.refreshToken(refresTokenRequest));
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Register new user"
            , requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(schema = @Schema(implementation = UserRequest.class),
                    examples = @ExampleObject(value = """
                            {
                              "username": "sokkhann",
                              "fullName": "string",
                              "gender": "male",
                              "pin": "898989",
                              "email": "sokkhann@gmail.com",
                              "password": "string",
                              "profileImage": "string",
                              "phoneNumber": "string",
                              "cityOrProvince": "string",
                              "khanOrDistrict": "string",
                              "sangkatOrCommune": "string",
                              "employeeType": "string",
                              "companyName": "string",
                              "mainSourceOfIncome": "string",
                              "monthlyIncomeRange": 0,
                              "studentCardId": "string",
                              "roles": [
                                "ADMIN","STUFF"
                              ]
                            }
                                                        
                                                        
                            """)

            )
    )
    )
    public BaseResponse<UserResponse> registerUser(
            @Valid @RequestBody UserRequest userRequest) {
        return BaseResponse.<UserResponse>createSuccess()
                .setPayload(userService.createUser(userRequest));
    }
}
