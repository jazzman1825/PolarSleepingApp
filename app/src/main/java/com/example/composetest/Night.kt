package com.example.composetest

import kotlinx.serialization.Serializable

@Serializable
data class Night(
    val polar_user: String?,
    val date: String?,
    val sleep_start_time: String?,
    val sleep_end_time: String?,
    val device_id: String?,
    val continuity: Double?,
    val continuity_class: Int?,
    val light_sleep: Int?,
    val deep_sleep: Int?,
    val rem_sleep: Int?,
    val unrecognized_sleep_stage: Int?,
    val sleep_score: Int?,
    val total_interruption_duration: Int?,
    val sleep_charge: Int?,
    val sleep_goal: Int?,
    val sleep_rating: Int?,
    val short_interruption_duration: Int?,
    val long_interruption_duration: Int?,
    val sleep_cycles: Int?,
    val group_duration_score: Int?,
    val group_solidity_score: Int?,
    val group_regeneration_score: Double?,
    //val hypnogram: Map<String, Int>,
    // val heart_rate_samples: Map<String, Int>
)

data class NightResponse(val nights: List<Night>)