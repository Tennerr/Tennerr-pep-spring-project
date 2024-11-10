package com.example.service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;
    
    public AccountService(AccountRepository accountRepository)
    {
        this.accountRepository = accountRepository;
    }

    public List<Account> getAllAccounts(){
        return accountRepository.findAll();
    }

    public Account saveAccount(Account account)
    {
        return accountRepository.save(account);
    }

    public Account accountExists(String username)
    {
        return accountRepository.findAccountByUsername(username);
    }

    public Account succesfulLogin(String username, String password)
    {
        return accountRepository.findAccountByUsernameAndPassword(username,password);
    }
}
