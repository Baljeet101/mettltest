package mettl.controller;

import mettl.model.Account;
import mettl.model.Transaction;
import mettl.model.User;
import mettl.repository.AccountRepository;
import mettl.repository.TransactionRepository;
import mettl.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@RestController
public class HomeController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @GetMapping("/fetchAccounts")
    String fetchAccountList(@RequestParam(value = "userid" , required = true)final Integer userId){
        try {
            System.out.println("In fetchAccountList method");
            if(userId != null ){
                if(userRepository.existsById(userId)) {
                    System.out.println("User exists for id :: "+userId);
                    List<Account> accList = (List) userRepository.getOne(userId)
                            .getAccList();
                    System.out.println("Account List for user id :: "+userId+" is \n"+accList.stream().map(Account::toString));
                    if(accList.isEmpty())return "No Accounts attached with this user";
                    StringBuilder sb = new StringBuilder();
                    for (Account account:
                            accList) {
                        sb.append(account.toString());
                    }
                    System.out.println("check "+sb.toString());
                    return sb.toString();
                }else {
                    return "user id "+userId+" does not exist";

                }
            }
        }catch (Exception e) {
            e.printStackTrace();
            return "fetchAccountList unsuccessful";
        }
        return null;
    }
    @GetMapping("/fetchAllUsers")
    String fetchAllUsers(){
        try {
            System.out.println("In fetchAllUsers method");
            List<User> list = (List) userRepository.findAll();
            System.out.println("List of all users :: \n"+list.stream().map(User::toString));
            if(list.isEmpty())return "No  user found";
            return list.toString();

        }catch (Exception e) {
            e.printStackTrace();
            return "fetchAllUsers unsuccessful";
        }
    }
    @GetMapping("/fetchAllTxsForAccount")
    String fetchAllTxsForAccount(@RequestParam(name = "accnum",required = true)final Integer accnum){
        try {
            System.out.println("In fetchAllTxsForAccount method");
            Account account =  accountRepository.findByAccNumber(accnum);
            if(account != null) {
                System.out.println("account :: \n"+account.toString());
                List<Transaction> list = (List<Transaction>) account.getTxList();
                if(list.isEmpty())return "No Transaction found for this account number : "+accnum;
                return list.toString();
            }else {
                return "Invalid Account Number !!";
            }

        }catch (Exception e) {
            e.printStackTrace();
            return "fetchAllTxsForAccount unsuccessful";
        }
    }

    @PostMapping("/addUser")
    String addUser(@RequestParam(value = "userid",required = true)final Integer userid,
                   @RequestParam(value = "username",required = true)final String username){
        try {
            System.out.println("In addUser method");
            System.out.println("username, userid :: "+username+" , "+userid);
            if(userid != null && username != null && !username.isEmpty()){
                User user = new User();
                user.setName(username);
                user.setId(userid);
                userRepository.save(user);
            }
            else {
                return "Please check user id and username !! ";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "user save unsuccessful";
        }
        return "user saved successful";
    }
    @PostMapping("/addAccount")
    String addAccount(@RequestBody(required = true)final Map<String, Object> accMap){
        System.out.println("In addAccount method");
        try {
            if(accMap != null){
                Account acc = new Account();
                int userId = (Integer)accMap.getOrDefault("userId",-1);
                if(userId != -1){
                    if(userRepository.existsById(userId)){
                        acc.setAccName(String.valueOf(accMap.get("accountName")));
                        acc.setAccNumber((Integer)accMap.get("accountNumber"));
                        acc.setAccType(String.valueOf(accMap.getOrDefault("accountType","Savings")));
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
                        Date date = formatter.parse(String.valueOf(accMap.getOrDefault("balDate","")));
                        acc.setBalDate(date);
                        acc.setCurrency(String.valueOf(accMap.get("currency")).length() > 3
                                ? String.valueOf(accMap.get("currency")).substring(0,3).toUpperCase()
                                : String.valueOf(accMap.get("currency")).toUpperCase());
                        acc.setOpenAvailBal((Double) (accMap.getOrDefault("openAvailBal",0.0)));
                        acc.setUser(userRepository.getOne(userId));
                        accountRepository.save(acc);
                        return "account added successful";

                    }else {
                        return "User Doesn't exist, Please create a user first!!";
                    }
                }else {
                    return "Please check user id field !!";
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "account add unsuccessful";
        }
        return null;
    }
    @PostMapping("/addTx")
    String addTrans(@RequestBody(required = true)final Map<String, Object> txMap){
        System.out.println("In addTrans method");
        try {
            if(txMap != null){
                int accNumber = (Integer)txMap.getOrDefault("accNumber",-1);
                System.out.println("account number :: "+accNumber);
                Account account = accountRepository.findByAccNumber(accNumber);

                if(accNumber != -1 && account != null){
                    Transaction tx = new Transaction();
                    tx.setAccount(account);
                    if(txMap.get("debitAmit") != null && txMap.get("debitAmit") instanceof Double)
                        tx.setDebitAmt((Double) txMap.get("debitAmt"));
                    tx.setType(String.valueOf(txMap.get("type")));
                    tx.setTxNarrat(String.valueOf(txMap.get("txNarrative")));
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
                    Date date = formatter.parse(String.valueOf(txMap.getOrDefault("valDate","")));
                    tx.setValDate(date);
                    transactionRepository.save(tx);

                    return "Trans added successfully";
                }else {
                    return "Please check account Number field !!";
                }
            }else {
                return "Please check input data !!";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Trans add unsuccessful";
        }
    }
}
