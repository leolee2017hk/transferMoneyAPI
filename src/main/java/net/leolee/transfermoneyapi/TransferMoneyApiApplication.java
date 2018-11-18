/*
 * Author: Leo Lee
 */
package net.leolee.transfermoneyapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class TransferMoneyApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransferMoneyApiApplication.class, args);
	}
}
