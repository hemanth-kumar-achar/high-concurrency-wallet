package com.hemanth.wallet.controller;

import com.hemanth.wallet.dto.TransferRequest;
import com.hemanth.wallet.model.Transaction;
import com.hemanth.wallet.model.Wallet;
import com.hemanth.wallet.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @PostMapping("/transfer")
    public ResponseEntity<?> transfer(@RequestBody TransferRequest request) {
        try {
            Transaction txn = walletService.transferFunds(request);
            return ResponseEntity.ok(txn);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Wallet> createWallet(@RequestParam String userId, @RequestParam BigDecimal amount) {
        return ResponseEntity.ok(walletService.createWallet(userId, amount));
    }
}