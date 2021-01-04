package ru.otus.businessLayer.service;

import ru.otus.businessLayer.model.Account;

import java.util.Optional;

public interface DBServiceAccount {
    long saveAccount(Account account);

    Optional<Account> getAccount(long no);
}
