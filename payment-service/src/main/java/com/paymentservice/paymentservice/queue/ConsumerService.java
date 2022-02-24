package com.paymentservice.paymentservice.queue;

import com.paymentservice.paymentservice.common.events.OrderEvent;
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
    void handlerPayment(OrderEvent orderEvent){
        orderEvent.setQueueName(QUEUE_PAYMENT);
        if (orderEvent.getPaymentStatus().equals(PaymentStatus.PENDING.name())){
            handlerPaymentPending(orderEvent);
        }
    }

    void handlerPaymentPending(OrderEvent orderEvent){
        Wallet wallet = checkWallet(orderEvent);
        if (wallet != null){
            double totalPrice = orderEvent.getTotalPrice();
            double balance = wallet.getBalance();

            if(balance < totalPrice){
                orderEvent.setMessage("Not Enough fund!");
                orderEvent.setPaymentStatus(PaymentStatus.NOT_ENOUGH_FUND.name());

                TransactionHistory failTransaction = new TransactionHistory();
                failTransaction.setSenderId(orderEvent.getUserId());
                failTransaction.setReceiverId("shop");
                failTransaction.setOrderId(orderEvent.getOrderId());
                failTransaction.setPaymentType(PaymentType.ORDER_TRANSACTION.name());
                failTransaction.setStatus(PaymentStatus.NOT_ENOUGH_FUND.name());
                failTransaction.setAmount(orderEvent.getTotalPrice());
                failTransaction.setMessage("Not Enough Fund to proceed!");
                transactionService.saveTransaction(failTransaction);

                rabbitTemplate.convertAndSend(TOPIC_EXCHANGE, ROUTING_KEY_PAYMENT, orderEvent);
            }
            else if (balance > totalPrice){
                orderEvent.setMessage("Payment Success!");
                orderEvent.setPaymentStatus(PaymentStatus.PAID.name());
                wallet.setBalance(wallet.getBalance() - orderEvent.getTotalPrice());
                walletService.saveWallet(wallet);


                TransactionHistory successTransaction = new TransactionHistory();
                successTransaction.setSenderId(orderEvent.getUserId());
                successTransaction.setReceiverId("shop");
                successTransaction.setOrderId(orderEvent.getOrderId());
                successTransaction.setPaymentType(PaymentType.ORDER_TRANSACTION.name());
                successTransaction.setStatus(PaymentStatus.PAID.name());
                successTransaction.setAmount(orderEvent.getTotalPrice());
                successTransaction.setMessage("Payment successful. Balance is subtracted!");
                transactionService.saveTransaction(successTransaction);

                System.out.println(orderEvent);
                rabbitTemplate.convertAndSend(TOPIC_EXCHANGE, ROUTING_KEY_PAYMENT, orderEvent);
            }
        }
    }

    private Wallet checkWallet(OrderEvent orderEvent){
        Optional<Wallet> wallet = walletService.findWalletByUserId(orderEvent.getUserId());
        if (wallet.isPresent()){
            return wallet.get();
        }
        orderEvent.setMessage("Wallet not found!");
        orderEvent.setPaymentStatus(PaymentStatus.WALLET_NOT_FOUND.name());
        rabbitTemplate.convertAndSend(TOPIC_EXCHANGE, ROUTING_KEY_PAYMENT, orderEvent);
        return null;
    }
}
