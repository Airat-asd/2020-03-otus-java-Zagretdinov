package ru.otus.businessLayer.service;

import ru.otus.businessLayer.model.Client;

import java.util.Optional;

public interface DBServiceClient {

    long saveClient(Client client);

    Optional<Client> getClient(long id);

}
