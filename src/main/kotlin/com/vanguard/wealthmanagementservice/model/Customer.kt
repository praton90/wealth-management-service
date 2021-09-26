package com.vanguard.wealthmanagementservice.model

import java.time.LocalDate

data class Customer(
    val id: Long,
    val email: String,
    val dateOfBirth: LocalDate,
    val riskLevel: Int,
    val retirementAge: Int
)
