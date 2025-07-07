package com.example.dndcharactersheetmanager.apiCalls

data class RaceListResponse(
    val count: Int,
    val results: List<RaceSummary>
)

data class RaceSummary(
    val index: String,
    val name: String,
    val url: String
)