package com.vanguard.wealthmanagementservice.config

import com.vanguard.wealthmanagementservice.dto.WealthStrategyDTO
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.BeanPropertyRowMapper
import org.springframework.jdbc.core.RowMapper

@Configuration
class RowMapperConfig {

    @Bean
    fun wealthStrategyRowMapper(): RowMapper<WealthStrategyDTO> = BeanPropertyRowMapper(WealthStrategyDTO::class.java)
}
