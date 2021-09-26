package com.vanguard.wealthmanagementservice.controller

import com.vanguard.wealthmanagementservice.service.impl.StrategyFileProcessorService
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/strategy")
class StrategyController(@Qualifier("strategy") private val fileProcessorService: StrategyFileProcessorService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun uploadStrategies(@RequestParam("strategyCsv") file: MultipartFile) = fileProcessorService.parseFileContent(file)
}
