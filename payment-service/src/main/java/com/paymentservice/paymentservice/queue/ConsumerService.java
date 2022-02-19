package com.paymentservice.paymentservice.queue;

import com.paymentservice.paymentservice.common.events.PaymentEvent;
import com.paymentservice.paymentservice.entity.TransactionHistory;
import com.paymentservice.paymentservice.entity.Wallet;
import com.paymentservice.paymentservice.enums.PaymentStatus;
import com.paymentservice.paymentservice.enums.PaymentType;
import com.paymentservice.paymentservice.service.TransactionService;
import com.paymentservice.paymentservice.service.WalletService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.paymentservice.paymentservice.queue.Config.*;

@Service
public class ConsumerService {

    @Autowired
    private WalletService walletService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private TransactionService transactionService;

    @Transactional
    void handlerPayment(PaymentEvent paymentEvent){
        paymentEvent.setQueueName(QUEUE_PAYMENT);
        if (paymentEvent.getPaymentStatus().equals(PaymentStatus.PENDING.name())){
            handlerPaymentPending(paymentEvent);
        }
    }

    void handlerPaymentPending(PaymentEvent paymentEvent){
        Wallet wallet = checkWallet(paymentEvent);
        if (wallet != null){
            double totalPrice = paymentEvent.getTotalPrice();
            double balance = wallet.getBalance();

            if(balance < totalPrice){
                paymentEvent.setMessage("Not Enough fund!");
                paymentEvent.setPaymentStatus(PaymentStatus.NOT_ENOUGH_FUND.name());

                TransactionHistory failTransaction = new TransactionHistory();
                failTransaction.setSenderId(paymentEvent.getUserId());
                failTransaction.setReceiverId("shop");
                failTransaction.setOrderId(paymentEvent.getOrderId());
                failTransaction.setPaymentType(PaymentType.ORDER_TRANSACTION.name());
                failTransaction.setStatus(PaymentStatus.NOT_ENOUGH_FUND.name());
                failTransaction.setAmount(paymentEvent.getTotalPrice());
                failTransaction.setMessage("Not Enough Fund to proceed!");
                transactionService.saveTransaction(failTransaction);

                rabbitTemplate.convertAndSend(TOPIC_EXCHANGE, ROUTING_KEY_PAYMENT, paymentEvent);
            }
            else if (balance > totalPrice){
                paymentEvent.setMessage("Payment Success!");
                paymentEvent.setPaymentStatus(PaymentStatus.PAID.name());
                wallet.setBalance(wallet.getBalance() - paymentEvent.getTotalPrice());
                walletService.saveWallet(wallet);


                TransactionHistory successTransaction = new TransactionHistory();
                successTransaction.setSenderId(paymentEvent.getUserId());
                successTransaction.setReceiverId("shop");
                successTransaction.setOrderId(paymentEvent.getOrderId());
                successTransaction.setPaymentType(PaymentType.ORDER_TRANSACTION.name());
                successTransaction.setStatus(PaymentStatus.PAID.name());
                successTransaction.setAmount(paymentEvent.getTotalPrice());
                successTransaction.setMessage("Payment successful. Balance is subtracted!");
                transactionService.saveTransaction(successTransaction);

                System.out.println(paymentEvent);
                rabbitTemplate.convertAndSend(TOPIC_EXCHANGE, ROUTING_KEY_PAYMENT, paymentEvent);
            }
        }
    }

    private Wallet checkWallet(PaymentEvent paymentEvent){
        Optional<Wallet> wallet = walletService.findWalletByUserId(paymentEvent.getUserId());
        if (wallet.isPresent()){
            return wallet.get();
        }
        paymentEvent.setMessage("Wallet not found!");
        paymentEvent.setPaymentStatus(PaymentStatus.WALLET_NOT_FOUND.name());
        rabbitTemplate.convertAndSend(TOPIC_EXCHANGE, ROUTING_KEY_PAYMENT, paymentEvent);
        return null;
    }
}
