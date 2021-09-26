package com.vanguard.wealthmanagementservice.service

import org.springframework.web.multipart.MultipartFile

interface FileProcessorService {

    fun parseFileContent(file: MultipartFile)
}
