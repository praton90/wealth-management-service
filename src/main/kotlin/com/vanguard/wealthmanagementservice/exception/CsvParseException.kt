package com.vanguard.wealthmanagementservice.exception

class CsvParseException(message: String, exception: Exception) : RuntimeException(message, exception)
