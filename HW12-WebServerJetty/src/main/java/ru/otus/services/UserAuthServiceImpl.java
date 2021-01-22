package ru.otus.services;

import ru.otus.businessLayer.service.DBServiceUser;

public class UserAuthServiceImpl implements UserAuthService {

    private final DBServiceUser dbServiceUser;

    public UserAuthServiceImpl(DBServiceUser dbServiceUser) {
        this.dbServiceUser = dbServiceUser;
    }

    @Override
    public boolean authenticate(String name, String password) {
        boolean authenticate = false;
        Optional<User> user = dbServiceUser.getUser(name);
        if (user.isPresent()) {
            if ((password.hashCode() == user.get().getPasswordHash()) && (user.get().isAnAdministrator() == 'y')) {
                authenticate =true;
            }
        }
         return authenticate;
    }
}
