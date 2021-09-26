package com.vanguard.wealthmanagementservice.dao

import com.vanguard.wealthmanagementservice.model.Strategy
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class StrategyRepository(private val jdbcTemplate: NamedParameterJdbcTemplate) {

    fun upsertStrategy(strategyDTO: Strategy) {
        jdbcTemplate.update(UPSERT_STRATEGY, BeanPropertySqlParameterSource(strategyDTO))
    }

}
