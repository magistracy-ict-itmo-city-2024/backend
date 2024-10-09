package ru.citycheck.core.application.service.issue.files

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import kotlin.io.path.*

@Service
class LocalFileStorageService(
    @Value("\${city.local.files.storage.root:media}") root: String,
) : FileStorageService {
    private val rootPath = Path(root)

    override fun saveFile(file: ByteArray, path: String) {
        val filePath = rootPath.resolve(path.trim('/'))

        if (filePath.exists()) {
            throw IllegalStateException("File already exists")
        }

        filePath.parent.createDirectories()
        filePath.writeBytes(file)
    }

    override fun deleteFile(path: String) {
        rootPath.resolve(path.trim('/')).deleteIfExists()
    }

    override fun getFile(path: String): ByteArray {
        return rootPath.resolve(path.trim('/')).readBytes()
    }
}
