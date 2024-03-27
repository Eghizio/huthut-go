package com.jw.huthutgo.domain

import com.jw.huthutgo.domain.model.UploadedFile

interface UploadsService {
    fun listUploads(): List<UploadedFile>
    fun upload(objectName: String, contents: ByteArray)
    fun download(objectName: String)
}
