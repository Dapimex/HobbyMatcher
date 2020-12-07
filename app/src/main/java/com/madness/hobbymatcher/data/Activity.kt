package com.madness.hobbymatcher.data

data class Activity(
    val id: Long? = null,
    val name: String,
    val description: String,
    val startTime: String,
    val location: String
)