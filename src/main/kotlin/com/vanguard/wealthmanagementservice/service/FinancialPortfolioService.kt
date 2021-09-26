package com.vanguard.wealthmanagementservice.service

import com.vanguard.wealthmanagementservice.dto.CustomerPortfolioDTO

interface FinancialPortfolioService {

    fun getCustomerPortfolio(customerId: Long): CustomerPortfolioDTO

    fun executeTrades(trades: List<CustomerPortfolioDTO>)
}
