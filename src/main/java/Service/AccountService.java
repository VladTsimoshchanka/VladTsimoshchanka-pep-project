package Service;
import DAO.AccountDAO;
import Model.Account;

import java.util.List;
public class AccountService {
    AccountDAO accDAO;
    public AccountService(){
        accDAO = new AccountDAO();
    }
    public AccountService(AccountDAO accDAO){
        this.accDAO = accDAO;
    }
    public Account registerAccount(Account acc){
        return accDAO.insertAccount(acc);
    }
    public Account verifyAccount(Account acc){
        return accDAO.checkAccount(acc);
    }
}
