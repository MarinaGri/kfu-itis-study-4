package ru.itis.services;

import ru.itis.dto.AccountDto;

import java.util.List;

public interface AccountsService {

    AccountDto addAccount(AccountDto account);

    List<AccountDto> searchByProductDiscountDayNumInCart(Integer dayNum);
}
