package com.vanguard.wealthmanagementservice.controller

import com.vanguard.wealthmanagementservice.service.WealthStrategyService
import com.vanguard.wealthmanagementservice.service.impl.CustomerFileProcessorService
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/customer")
class CustomerController(
    @Qualifier("customer") private val fileProcessorService: CustomerFileProcessorService,
    private val wealthStrategyService: WealthStrategyService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun uploadCsvFile(@RequestParam("customerCsv") file: MultipartFile) = fileProcessorService.parseFileContent(file)

    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping("/portfolio")
    fun updateCustomersPortfolio() = wealthStrategyService.rebalanceCustomersPortfolio()
}
