package com.cloudspend.accountingsystem;

import com.cloudspend.accountingsystem.service.Runner;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AccountingSystemApplicationTests {

	@Autowired
	Runner runner;

	@Test
	void contextLoads() {
	}

	@Test
	void testExpensify(){
		runner.getVendorAndTransactionInfo();
		System.out.println("done");
	}

}
