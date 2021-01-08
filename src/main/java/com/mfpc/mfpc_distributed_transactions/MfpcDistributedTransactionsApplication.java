package com.mfpc.mfpc_distributed_transactions;

import com.mfpc.mfpc_distributed_transactions.transaction.service.TransactionScheduler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MfpcDistributedTransactionsApplication {
    public static void main(String[] args) {
        TransactionScheduler transactionScheduler = new TransactionScheduler();
        transactionScheduler.start();

        SpringApplication.run(MfpcDistributedTransactionsApplication.class, args);
    }
}
