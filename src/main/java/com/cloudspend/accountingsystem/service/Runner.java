package com.cloudspend.accountingsystem.service;

import com.cloudspend.accountingsystem.dao.VendorRepository;
import com.cloudspend.accountingsystem.model.Expense;
import com.cloudspend.accountingsystem.model.Vendor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;

@Component
@EnableScheduling
public class Runner {

    @Autowired
    ExpensifyService expensifyService;

    @Autowired
    VendorRepository vendorRepository;

    @Scheduled(cron = "0 0 0 * * ?")
    public void getVendorAndTransactionInfo(){
        // Get yesterday's date
        String yesterday = LocalDate.now().minusDays(1).toString();
        System.out.println("Generating report for "+yesterday);

        String transactionInfo = expensifyService.getTransaction(yesterday);

        // Ensure there's transaction info for the day
        if (! transactionInfo.equals(" ")) {
            Arrays.stream(transactionInfo.split("/n")).forEach(transaction -> {
                String[] transactionDetails = transaction.split(",");
                String vendorName = transactionDetails[0];
                String amount = transactionDetails[1];
                String transactionDate = transactionDetails[2];
                String category = transactionDetails[3];

                Expense expense = new Expense(amount, transactionDate, category);

                if (vendorRepository.existsByName(vendorName)) {
                    Vendor vendor = vendorRepository.getByName(vendorName);
                    vendor.addExpense(expense);
                    vendorRepository.save(vendor);
                } else {
                    Vendor vendor = new Vendor(vendorName);
                    vendor.getExpenses().add(expense);
                    vendorRepository.save(vendor);
                }
            });
        }
    }
}
