package com.youthcamp.starter.network

import com.squareup.moshi.Json
import retrofit2.Call
import retrofit2.http.GET

/**
 * Data class where it holds information on how to serialize / deserialize our data
 *
 * Try deserializing other fields!
 *
 * Note: @field is important!
 */
data class WorldTime(
    @field:Json(name = "unixtime") val unixtime: Long,
    @field:Json(name = "client_ip") val clientIPV4: String
)

interface WorldTimeAPI {
    @GET("api/ip")
    fun getWorldTimeBasedOnIP(): Call<WorldTime>
}