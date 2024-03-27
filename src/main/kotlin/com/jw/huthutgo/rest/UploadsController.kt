package com.jw.huthutgo.rest

import com.jw.huthutgo.domain.UploadsService
import com.jw.huthutgo.rest.model.FileInput
import org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

@RestController
@RequestMapping(value = ["/api/uploads"])
class UploadsController(private val service: UploadsService) {
    // StorageService would be better probably.

    @GetMapping()
    fun listAllFiles(): ResponseEntity<List<FileInput>> {
        val uploadedFiles = service.listUploads().map {
            FileInput(it.id, it.fileName, it.url)
        }
//        val uploadedFiles = listOf<FileInput>()
        return ResponseEntity.ok(uploadedFiles)
        // TODO: List all files uploaded
    }

    @GetMapping("/{id}")
    fun getFileById(@PathVariable(required = true, name = "id") id: String): ResponseEntity<Any> {
//        service.download(id)
        val objectName = "example_attachment.txt"
        service.download(objectName)

        return ResponseEntity.ok().build()
        // TODO: Return the data of uploaded file of id. If not found 404.
    }

    @PostMapping(consumes = [MULTIPART_FORM_DATA_VALUE])
    fun uploadFile(@RequestPart("attachments") attachments: List<MultipartFile>): ResponseEntity<Any> {
        println("ATTACHMENTS -> $attachments")

        val first = attachments.first()
        val data = mapOf(
            "name" to first.name,
            "originalFilename" to first.originalFilename,
            "size" to first.size,
        )
        println(data)

        // Prolly should be a UUID overall
        val objectName = first.originalFilename ?: UUID.randomUUID().toString()

        if (first.originalFilename != null) {
            service.upload(objectName, first.bytes)
        }

        return ResponseEntity.ok(data)
    }
}
