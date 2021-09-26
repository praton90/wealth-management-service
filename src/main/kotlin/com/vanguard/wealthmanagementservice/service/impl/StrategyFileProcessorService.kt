package com.vanguard.wealthmanagementservice.service.impl

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.vanguard.wealthmanagementservice.dao.StrategyRepository
import com.vanguard.wealthmanagementservice.exception.CsvParseException
import com.vanguard.wealthmanagementservice.model.Strategy
import com.vanguard.wealthmanagementservice.service.FileProcessorService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
@Qualifier("strategy")
class StrategyFileProcessorService(private val strategyRepository: StrategyRepository) : FileProcessorService {

    companion object {
        val log: Logger = LoggerFactory.getLogger(StrategyFileProcessorService::class.java)
    }

    override fun parseFileContent(file: MultipartFile) {
        log.info("Parsing strategy csv file")
        try {
            val rows = csvReader().readAllWithHeader(file.inputStream)
            val strategies = parseStrategies(rows)

            strategies.forEach {
                strategyRepository.upsertStrategy(it)
            }
            log.info("strategy csv file parsed successfully")
        } catch (ex: Exception) {
            log.error("Error parsing strategy csv file")
            throw CsvParseException("Error parsing strategy csv file", ex)
        }
    }

    private fun parseStrategies(fileRows: List<Map<String, String>>) = fileRows.map {
        try {
            Strategy(
                id = it["strategyId"]!!.toLong(),
                minRiskLevel = it["minRiskLevel"]!!.toInt(),
                maxRiskLevel = it["maxRiskLevel"]!!.toInt(),
                minYearsToRetirement = it["minYearsToRetirement"]!!.toInt(),
                maxYearsToRetirement = it["maxYearsToRetirement"]!!.toInt(),
                stocksPercentage = it["stocksPercentage"]!!.toInt(),
                cashPercentage = it["cashPercentage"]!!.toInt(),
                bondsPercentage = it["bondsPercentage"]!!.toInt()
            )
        } catch (ex: Exception) {
            log.warn("No possible to parse entity with id ${it["strategyId"]}")
            return@map null
        }
    }.filterNotNull()

}
