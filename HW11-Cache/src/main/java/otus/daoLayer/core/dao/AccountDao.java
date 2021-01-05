package otus.daoLayer.core.dao;

import ru.otus.businessLayer.model.Account;
import ru.otus.daoLayer.core.sessionmanager.SessionManager;

import java.util.Optional;

public interface AccountDao {

    long insert(Account account);

    long insertOrUpdate(Account account);

    Optional<Account> findById(long no);

    SessionManager getSessionManager();
}
