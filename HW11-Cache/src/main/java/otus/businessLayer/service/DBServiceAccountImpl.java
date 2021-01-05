package otus.businessLayer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.businessLayer.model.Account;
import ru.otus.daoLayer.core.dao.AccountDao;

import java.util.Optional;

public class DBServiceAccountImpl implements DBServiceAccount {
    private static final Logger logger = LoggerFactory.getLogger(DBServiceAccountImpl.class);

    private final AccountDao accountDao;

    public DBServiceAccountImpl(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public long saveAccount(Account account) {
        try (var sessionManager = accountDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                long accountId = accountDao.insertOrUpdate(account);
                sessionManager.commitSession();
                logger.info("save {}, id: {}", account.getClass().getSimpleName(), accountId);
                return accountId;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }

    @Override
    public Optional<Account> getAccount(long no) {
        try (var sessionManager = accountDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Optional<Account> accountOptional = accountDao.findById(no);
                logger.info("account: {}", accountOptional.orElse(null));
                return accountOptional;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return Optional.empty();
        }
    }
}