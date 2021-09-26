package com.vanguard.wealthmanagementservice.client

import com.vanguard.wealthmanagementservice.dto.CustomerPortfolioDTO
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@FeignClient(
    name = "portfolio-service",
    url = "\${portfolio-service.url}"
)
interface FinancialPortfolioServiceClient {

    @GetMapping("/customer/{customerId}")
    fun getCustomerPortfolio(@PathVariable customerId: Long): CustomerPortfolioDTO

    @PostMapping("/execute")
    fun executeTrades(@RequestBody trades: List<CustomerPortfolioDTO>)
}
