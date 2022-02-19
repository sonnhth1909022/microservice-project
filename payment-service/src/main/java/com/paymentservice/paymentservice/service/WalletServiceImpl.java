package com.paymentservice.paymentservice.service;

import com.paymentservice.paymentservice.entity.Wallet;
import com.paymentservice.paymentservice.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WalletServiceImpl implements WalletService{

    @Autowired
    private WalletRepository walletRepository;

    @Override
    public Optional<Wallet> findWalletByUserId(String userId) {
        return walletRepository.findByUserId(userId);
    }

    @Override
    public Wallet saveWallet(Wallet wallet) {
        return walletRepository.save(wallet);
    }
}
