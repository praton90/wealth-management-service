package com.vanguard.wealthmanagementservice.dao

import com.vanguard.wealthmanagementservice.model.Customer
import com.vanguard.wealthmanagementservice.dto.WealthStrategyDTO
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class CustomerRepository(
    private val jdbcTemplate: NamedParameterJdbcTemplate,
    private val rowMapper: RowMapper<WealthStrategyDTO>
) {
    fun getCustomersWealthStrategy(): List<WealthStrategyDTO> {
        return jdbcTemplate.query(SELECT_CUSTOMER_STRATEGY, rowMapper)
    }

    fun upsertCustomer(customerCsvDTO: Customer) {
        jdbcTemplate.update(UPSERT_CUSTOMER, BeanPropertySqlParameterSource(customerCsvDTO))
    }

}
