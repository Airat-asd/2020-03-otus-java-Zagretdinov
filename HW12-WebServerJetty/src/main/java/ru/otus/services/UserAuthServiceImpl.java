package ru.otus.services;

import ru.otus.businessLayer.service.DBServiceUser;

public class UserAuthServiceImpl implements UserAuthService {

    private final DBServiceUser dbServiceUser;

    public UserAuthServiceImpl(DBServiceUser dbServiceUser) {
        this.dbServiceUser = dbServiceUser;
    }

    @Override
    public boolean authenticate(String login, String password) {
//        dbServiceUser.getUser(login)
//
//        return userDao.findByLogin(login)
//                .map(user -> user.getPassword().equals(password))
//                .orElse(false);
        return true;
    }

}
