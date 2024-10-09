package ru.citycheck.core.application.service.issue.files

interface FileStorageService {
    fun saveFile(file: ByteArray, path: String)

    fun deleteFile(path: String)

    fun getFile(path: String): ByteArray
}