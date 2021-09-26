package com.vanguard.wealthmanagementservice.model

data class Strategy(
    val id: Long,
    val minRiskLevel: Int,
    val maxRiskLevel: Int,
    val minYearsToRetirement: Int,
    val maxYearsToRetirement: Int,
    val stocksPercentage: Int,
    val cashPercentage: Int,
    val bondsPercentage: Int
)
