package com.vanguard.wealthmanagementservice.dto

data class WealthStrategyDTO(
    var customerId: Long? = null,
    var strategyId: Long? = null,
    var cashPercentage: Int? = 100,
    var stockPercentage: Int? = 0,
    var bondPercentage: Int? = 0,
)
