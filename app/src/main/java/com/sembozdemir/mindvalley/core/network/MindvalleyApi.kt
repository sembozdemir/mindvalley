package com.sembozdemir.mindvalley.core.network

import com.sembozdemir.mindvalley.core.network.model.CategoriesResponse
import com.sembozdemir.mindvalley.core.network.model.ChannelsResponse
import com.sembozdemir.mindvalley.core.network.model.NewEpisodesResponse
import retrofit2.http.GET

interface MindvalleyApi {

    @GET("/z5AExTtw")
    suspend fun getNewEpisodes(): NewEpisodesResponse

    @GET("/Xt12uVhM")
    suspend fun getChannels(): ChannelsResponse

    @GET("/A0CgArX3")
    suspend fun getCategories(): CategoriesResponse
}