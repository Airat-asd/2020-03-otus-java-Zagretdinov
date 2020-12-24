package ru.otus.businessLayer.service;

import ru.otus.businessLayer.model.Account;

import java.util.Optional;

public interface DBServiceAccount {
    String saveAccount(Account account);

    Optional<Account> getAccount(String id);
}
