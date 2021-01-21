package ru.otus.services;

import ru.otus.businessLayer.service.DBServiceUser;

public class UserAuthServiceImpl implements UserAuthService {

    private final DBServiceUser dbServiceUser;

    public UserAuthServiceImpl(DBServiceUser dbServiceUser) {
        this.dbServiceUser = dbServiceUser;
    }

    @Override
    public boolean authenticate(String name, String password) {
        dbServiceUser.getUser(name);
//
//        return userDao.findByLogin(login)
//                .map(user -> user.getPassword().equals(password))
//                .orElse(false);
        return true;
    }

}
