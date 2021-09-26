package com.vanguard.wealthmanagementservice.dto

data class CustomerPortfolioDTO(
    val customerId: Long,
    val stocks: Int,
    val bonds: Int,
    val cash: Int
)
