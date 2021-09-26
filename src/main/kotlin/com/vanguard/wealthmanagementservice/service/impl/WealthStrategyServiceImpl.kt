package com.vanguard.wealthmanagementservice.service.impl

import com.vanguard.wealthmanagementservice.dao.CustomerRepository
import com.vanguard.wealthmanagementservice.dto.CustomerPortfolioDTO
import com.vanguard.wealthmanagementservice.dto.WealthStrategyDTO
import com.vanguard.wealthmanagementservice.service.FinancialPortfolioService
import com.vanguard.wealthmanagementservice.service.WealthStrategyService
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class WealthStrategyServiceImpl(
    private val financialPortfolioService: FinancialPortfolioService,
    private val customerRepository: CustomerRepository,
    @Value("\${task-executor.thread-pool-size}") private val batchSize: Int
) : WealthStrategyService {

    override fun rebalanceCustomersPortfolio() {
        val customerWealthStrategies = customerRepository.getCustomersWealthStrategy()
        val customerWealthStrategyBatches = customerWealthStrategies.chunked(batchSize)

        customerWealthStrategyBatches.map { customerWealthStrategyBatch ->
            val portfolios = customerWealthStrategyBatch.map { customerWealthStrategy ->
                val customerPortfolio =
                    financialPortfolioService.getCustomerPortfolio(customerWealthStrategy.customerId!!)
                buildUpdatedPortfolio(customerPortfolio, customerWealthStrategy)
            }
            financialPortfolioService.executeTrades(portfolios)
        }
    }

    private fun getAmountInvested(customerPortfolioDTO: CustomerPortfolioDTO): Int =
        customerPortfolioDTO.cash + customerPortfolioDTO.bonds + customerPortfolioDTO.stocks

    private fun buildUpdatedPortfolio(
        customerPortfolioDTO: CustomerPortfolioDTO,
        wealthStrategyDTO: WealthStrategyDTO
    ): CustomerPortfolioDTO {
        val amountInvested = getAmountInvested(customerPortfolioDTO)
        return customerPortfolioDTO.copy(
            cash = calculateAdjustedInvestmentValue(
                currentInvestmentValue = customerPortfolioDTO.cash,
                totalAmountInvested = amountInvested,
                percentage = wealthStrategyDTO.cashPercentage
            ),
            bonds = calculateAdjustedInvestmentValue(
                currentInvestmentValue = customerPortfolioDTO.bonds,
                totalAmountInvested = amountInvested,
                percentage = wealthStrategyDTO.bondPercentage
            ),
            stocks = calculateAdjustedInvestmentValue(
                currentInvestmentValue = customerPortfolioDTO.stocks,
                totalAmountInvested = amountInvested,
                percentage = wealthStrategyDTO.stockPercentage
            )
        )
    }

    private fun calculateAdjustedInvestmentValue(
        currentInvestmentValue: Int,
        totalAmountInvested: Int,
        percentage: Int? = 0
    ): Int {
        return when (percentage) {
            0 -> 0
            100 -> totalAmountInvested
            else -> {
                val newInvestmentValue = totalAmountInvested.times(percentage!!).div(100)
                return newInvestmentValue - currentInvestmentValue
            }
        }
    }
}
