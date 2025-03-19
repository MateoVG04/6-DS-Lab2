package com.example.demo.service;
import com.example.demo.RestClient;
import com.example.demo.RestClient;
import com.example.demo.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.exceptions.*;

import java.util.List;
import java.util.Optional;
// changes on branch feature 1
@Service
public class BankService {
    @Autowired
    private Repository repository;

    public Optional<RestClient> getAccountId(Long id) {
        return repository.findById(id);
    }

    public RestClient createAccount(RestClient client) {
        return repository.save(client);
    }

    public RestClient addMoney(Long id, double money) {
        RestClient client = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client's account does not exist"));
        double amount = client.getBalance();
        client.setBalance(amount + money);
        return repository.save(client);
    }

    public void removeMoney(Long id, double money) throws BalanceException{
        RestClient client = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client's account does not exist"));
        double balance = client.getBalance();
        if (balance >= money) {
            client.setBalance(balance - money);
            repository.save(client);
        } else {
            throw new BalanceException("Not enough money on your balance");
        }
    }
    public void getMoney(Long id, double money) throws BalanceException{
        RestClient client = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client's account does not exist"));
        double balance = client.getBalance();
        if (balance >= money) {
            client.setBalance(balance - money);
        } else {
            throw new BalanceException("Not enough money on your balance");
        }
    }
}
