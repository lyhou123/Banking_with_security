package co.istad.mobilebankingcstad.init;

import co.istad.mobilebankingcstad.domain.AccountType;
import co.istad.mobilebankingcstad.domain.Authority;
import co.istad.mobilebankingcstad.domain.Role;
import co.istad.mobilebankingcstad.features.accounttype.AccountTypeRepository;
import co.istad.mobilebankingcstad.features.authoriy.AuthorityRepository;
import co.istad.mobilebankingcstad.features.roles.RoleRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;


// populate database ( role with some data )
@Component
@RequiredArgsConstructor
public class DataInitializer {
    private final RoleRepository roleRepository;
    private final AccountTypeRepository accountTypeRepository;
    private final AuthorityRepository authorityRepository;

    @PostConstruct
    void dataInit()
    {
        authorityInit();
        accountTypesInit();
        roleInit();

    }

    @PostConstruct
    void authorityInit(){
        List<String> authorities = List.of("READ","WRITE","DELETE");
        if(authorityRepository.findAll().isEmpty()){
            for(var authority : authorities){
                var authorityObj = new Authority();
                authorityObj.setName(authority);
                authorityRepository.save(authorityObj);
            }
        }

    }

    @PostConstruct
    void roleInit(){
        List<String> roles = List.of("ADMIN","STUFF","USER");
        if(roleRepository.findAll().isEmpty()){
            for(var role : roles){
                var roleObj = new Role();
                if(role.equals("ADMIN")){
                    roleObj.setAuthorities(new HashSet<>(authorityRepository.findAll()));

                }else if(role.equals("USER")){
                    roleObj.setAuthorities(new HashSet<>(authorityRepository.findAll()).stream().filter(
                            authority -> authority.getName().equals("READ")
                    ).collect(Collectors.toSet()));
                }
                roleObj.setName(role);
                roleRepository.save(roleObj);
            }

        }

    }

    @PostConstruct
    void accountTypesInit(){
        List<AccountType> accountTypes = new ArrayList<>(){{
            add(new AccountType()
                    .setName("SAVINGS")
                    .setDescription("This is the type of account that you gain interest when you save your money in the bank"));

            add(new AccountType()
                    .setName("PAYROLLS")
                    .setDescription("This is account to get paid by company monthly."));
            add(new AccountType()
                    .setName("Card")
                    .setDescription("Allow you to create different card for personal uses!"));
        }};

        if(accountTypeRepository.findAll().isEmpty())
        {
            accountTypeRepository.saveAll(accountTypes);

        }
    }
}
