package com.example.dndcharactersheetmanager.apiCalls

data class ClassListResponse(
    val count: Int,
    val results: List<ClassSummary>
)

data class ClassSummary(
    val index: String,
    val name: String,
    val url: String
)