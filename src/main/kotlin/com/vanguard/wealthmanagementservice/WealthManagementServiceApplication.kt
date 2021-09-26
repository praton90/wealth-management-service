package com.vanguard.wealthmanagementservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@EnableFeignClients
@SpringBootApplication
class WealthManagementServiceApplication

fun main(args: Array<String>) {
	runApplication<WealthManagementServiceApplication>(*args)
}
