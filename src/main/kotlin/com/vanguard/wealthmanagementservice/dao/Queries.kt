package com.vanguard.wealthmanagementservice.dao

const val SELECT_CUSTOMER_STRATEGY = """
SELECT s.id as strategy_id, c.id as customer_id, c.email, stock_percentage, CASE WHEN cash_percentage IS NULL THEN 100 ELSE cash_percentage END, bond_percentage 
FROM customer c LEFT JOIN strategy s 
ON c.risk_level BETWEEN s.min_risk_level AND s.max_risk_level 
AND get_years_until_retirement(c.date_of_birth, c.retirement_age) BETWEEN s.min_years_to_retirement AND s.max_years_to_retirement;
"""

const val UPSERT_CUSTOMER = """
    INSERT INTO customer VALUES(:id, :email, :dateOfBirth, :riskLevel, :retirementAge) 
    ON CONFLICT ON CONSTRAINT customer_pk 
    DO UPDATE SET email = EXCLUDED.email, 
    date_of_birth = EXCLUDED.date_of_birth, 
    risk_level = EXCLUDED.risk_level, 
    retirement_age = EXCLUDED.retirement_age;
"""

const val UPSERT_STRATEGY = """
    INSERT INTO strategy VALUES(:id, :minRiskLevel, :maxRiskLevel, :minYearsToRetirement, :maxYearsToRetirement, :stocksPercentage, :cashPercentage, :bondsPercentage) 
    ON CONFLICT ON CONSTRAINT strategy_pk 
    DO UPDATE SET min_risk_level = EXCLUDED.min_risk_level, 
    max_risk_level = EXCLUDED.max_risk_level, 
    min_years_to_retirement = EXCLUDED.min_years_to_retirement, 
    max_years_to_retirement = EXCLUDED.max_years_to_retirement, 
    stock_percentage = EXCLUDED.stock_percentage,
    cash_percentage = EXCLUDED.cash_percentage,
    bond_percentage = EXCLUDED.bond_percentage;
"""
