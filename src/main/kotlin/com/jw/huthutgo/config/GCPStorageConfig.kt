package com.jw.huthutgo.config

import org.springframework.context.annotation.Configuration

@Configuration
class GCPStorageConfig {
    val projectId = "delta-frame-403623" // The ID of your GCP project
    val bucketName = "huthutgo-bucket" // The ID of your GCS bucket
}
