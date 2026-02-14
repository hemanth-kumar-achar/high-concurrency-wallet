package com.hemanth.wallet.service;

import com.hemanth.wallet.model.Transaction;
import com.hemanth.wallet.model.Wallet;
import com.hemanth.wallet.dto.TransferRequest;
import com.hemanth.wallet.repository.TransactionRepository;
import com.hemanth.wallet.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Service
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;
    
    @Autowired
    private TransactionRepository transactionRepository;

    // @Transactional ensures that if money leaves User A but fails to reach User B,
    // the entire operation rolls back. Money is never lost.
    @Transactional
    public Transaction transferFunds(TransferRequest request) {
        // 1. Fetch Sender and Receiver
        Wallet sender = walletRepository.findByUserId(request.getFromUser())
                .orElseThrow(() -> new RuntimeException("Sender not found"));
        
        Wallet receiver = walletRepository.findByUserId(request.getToUser())
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        // 2. Check Balance
        if (sender.getBalance().compareTo(request.getAmount()) < 0) {
            throw new RuntimeException("Insufficient Balance");
        }

        // 3. Deduct and Add
        sender.setBalance(sender.getBalance().subtract(request.getAmount()));
        receiver.setBalance(receiver.getBalance().add(request.getAmount()));

        // 4. Save (The @Version field automatically checks for concurrent updates)
        walletRepository.save(sender);
        walletRepository.save(receiver);

        // 5. Audit Log
        Transaction txn = Transaction.builder()
                .fromUser(request.getFromUser())
                .toUser(request.getToUser())
                .amount(request.getAmount())
                .status("SUCCESS")
                .timestamp(LocalDateTime.now())
                .build();
        
        return transactionRepository.save(txn);
    }
    
    public Wallet createWallet(String userId, BigDecimal initialBalance) {
        Wallet wallet = new Wallet();
        wallet.setUserId(userId);
        wallet.setBalance(initialBalance);
        return walletRepository.save(wallet);
    }
}