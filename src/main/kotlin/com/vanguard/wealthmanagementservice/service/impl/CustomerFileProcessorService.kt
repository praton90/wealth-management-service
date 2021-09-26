package com.vanguard.wealthmanagementservice.service.impl

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.vanguard.wealthmanagementservice.dao.CustomerRepository
import com.vanguard.wealthmanagementservice.exception.CsvParseException
import com.vanguard.wealthmanagementservice.model.Customer
import com.vanguard.wealthmanagementservice.service.FileProcessorService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDate

@Service
@Qualifier("customer")
class CustomerFileProcessorService(private val customerRepository: CustomerRepository) : FileProcessorService {

    companion object {
        val log: Logger = LoggerFactory.getLogger(CustomerFileProcessorService::class.java)
    }

    override fun parseFileContent(file: MultipartFile) {
        log.info("Parsing customer csv file")
        try {
            val rows: List<Map<String, String>> = csvReader().readAllWithHeader(file.inputStream)
            val customers = parseCustomers(rows)

            customers.forEach {
                customerRepository.upsertCustomer(it)
            }
            log.info("customer csv file parsed successfully")
        } catch (ex: Exception) {
            log.error("Error parsing customer csv file")
            throw CsvParseException("Error parsing customer csv file", ex)
        }
    }

    private fun parseCustomers(fileRows: List<Map<String, String>>) = fileRows.map {
        try {
            Customer(
                id = it["customerId"]!!.toLong(),
                email = it["email"]!!,
                dateOfBirth = LocalDate.parse(it["dateOfBirth"]!!),
                riskLevel = it["riskLevel"]!!.toInt(),
                retirementAge = it["retirementAge"]!!.toInt()
            )
        } catch (ex: Exception) {
            log.warn("No possible to parse customer entity with id ${it["customerId"]}")
            return@map null
        }
    }.filterNotNull()

}
