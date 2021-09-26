package com.vanguard.wealthmanagementservice.service.impl

import com.vanguard.wealthmanagementservice.client.FinancialPortfolioServiceClient
import com.vanguard.wealthmanagementservice.dto.CustomerPortfolioDTO
import com.vanguard.wealthmanagementservice.service.FinancialPortfolioService
import org.springframework.stereotype.Service

@Service
class FinancialPortfolioServiceImpl(private val financialPortfolioServiceClient: FinancialPortfolioServiceClient) :
    FinancialPortfolioService {

    override fun getCustomerPortfolio(customerId: Long): CustomerPortfolioDTO =
        financialPortfolioServiceClient.getCustomerPortfolio(customerId)

    override fun executeTrades(trades: List<CustomerPortfolioDTO>): Unit =
        financialPortfolioServiceClient.executeTrades(trades)
}
