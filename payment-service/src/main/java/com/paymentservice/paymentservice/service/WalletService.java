package com.paymentservice.paymentservice.service;

import com.paymentservice.paymentservice.entity.Wallet;

import java.util.Optional;

public interface WalletService {
    Optional<Wallet> findWalletByUserId(String userId);
    Wallet saveWallet(Wallet wallet);
}
