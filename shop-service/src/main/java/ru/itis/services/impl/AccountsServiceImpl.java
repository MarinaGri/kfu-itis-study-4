package ru.itis.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.stereotype.Service;
import ru.itis.dto.AccountDto;
import ru.itis.models.Account;
import ru.itis.repositories.springdata.AccountsRepository;
import ru.itis.services.AccountsService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ru.itis.dto.AccountDto.from;

@Service
@RequiredArgsConstructor
public class AccountsServiceImpl implements AccountsService {

    private final AccountsRepository accountsRepository;

    @Override
    public AccountDto addAccount(AccountDto account) {
        Account newAccount = Account.builder()
                .id(account.getId())
                .firstName(account.getFirstName())
                .lastName(account.getLastName())
                .email(account.getEmail())
                .password(account.getPassword())
                .age(account.getAge())
                .build();
        return from(accountsRepository.save(newAccount));
    }

    @Override
    public List<AccountDto> searchByProductDiscountDayNumInCart(Integer dayNum) {
        Optional<List<Account>> optionalAccounts;
        try {
            optionalAccounts =
                    accountsRepository.findAllByProductDiscountDayNum(dayNum);
        } catch (InvalidDataAccessResourceUsageException ex) {
            return new ArrayList<>();
        }

        return optionalAccounts.map(AccountDto::from).orElse(new ArrayList<>());
    }
}
