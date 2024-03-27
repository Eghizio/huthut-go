package com.jw.huthutgo.domain

import com.google.cloud.storage.BlobId
import com.google.cloud.storage.BlobInfo
import com.google.cloud.storage.StorageOptions
import com.jw.huthutgo.config.GCPStorageConfig
import com.jw.huthutgo.domain.model.UploadedFile
import org.springframework.stereotype.Service
import java.io.ByteArrayInputStream
import java.nio.file.Paths
import kotlin.io.path.absolute
import kotlin.io.path.exists

@Service
class GoogleStorageUploadsService : UploadsService {
    // To configuration Bean
    val projectId = GCPStorageConfig().projectId
    val bucketName = GCPStorageConfig().bucketName

    override fun listUploads(): List<UploadedFile> {
//        TODO("Not yet implemented")

        // could be a single instance for the service
        val storage = StorageOptions.newBuilder().setProjectId(projectId).build().service
        val blobs = storage.list(bucketName)

        val uploads = blobs.iterateAll().toList()

        println("BLOBS -> $uploads")
        val f = uploads.first()

        println("Blob: $f")

        return uploads.map { UploadedFile(it.generatedId, it.name, it.mediaLink) }
    }

    // https://github.com/googleapis/java-storage/blob/332f70b2b66beb0297616834341641e2ba18332c/samples/snippets/src/main/java/com/example/storage/object/ListObjects.java
    override fun upload(objectName: String, contents: ByteArray) {
//        TODO("Not yet implemented")
        // add try catch error handling

//        val objectName = "your-object-name" // The ID of your GCS object
//        val contents = "Hello world!" // The string of contents you wish to upload

        val blobId = BlobId.of(bucketName, objectName)
        val blobInfo = BlobInfo.newBuilder(blobId).build()
//        val content = contents.toByteArray()

        val storage = StorageOptions.newBuilder()
            .setProjectId(projectId)
            .build()
            .service

        storage.createFrom(blobInfo, ByteArrayInputStream(contents))

        println("Object '$objectName' uploaded to bucket '$bucketName' with contents: $contents")
    }

    override fun download(objectName: String) {
        val storage = StorageOptions.newBuilder().setProjectId(projectId).build().service
        val blobId = BlobId.of(bucketName, objectName)
        println("BLOB ID: $blobId")

        val blob = storage.get(blobId)

        println("debug $bucketName $objectName $blob")
        // check for missing blob

        val destination = "downloads"
        val filePath = "$destination/${blobId.name}"
        val path = Paths.get(filePath)
        println("${path.absolute()} -> Exists?: ${path.exists()}")

        if (blob != null) {
            blob.downloadTo(path)

            println("Downloaded object '$objectName' from bucket name '$bucketName' to '$destination'")
        } else {
            println("Failed to download object '$objectName' from bucket name '$bucketName' to '$destination'")
        }
    }
}
