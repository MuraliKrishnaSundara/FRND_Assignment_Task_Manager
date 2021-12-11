package com.murali.taskmanager.data.api

import com.murali.taskmanager.data.local.CalenderTaskModel
import com.murali.taskmanager.data.response.GetTasksResponseModel
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface TasksAPI {

    //@Headers("Accept: application/json")

    @POST("/api/storeCalendarTask")
    suspend fun login(
        @Body calenderTaskModel: CalenderTaskModel
    ): CalenderTaskModel
/*
    @POST("api/deleteCalendarTask")
    suspend fun signUp(
        @Body signupRequestModel: User
    ): SignupResponse
*/
    @GET("api/getCalendarTaskLists")
    suspend fun getTasksFromAPI(
        @Header("user_id") user_id: Int
    ): GetTasksResponseModel


    /*@POST("/v2/courses/new")
    suspend fun signUp(
        @Header("Authorization") authToken: String?,
        @Body createNewCourseRequest: CreateNewCourseRequest
    ): CreateNewCourseResponse

    @GET("app/android/version")
    suspend fun getAllTasks(
        @Header("Content-Type") contentType: String
    ): ForceUpdateAppResponseModel*/


    /*@POST("v2/data/{code}/register")
    suspend fun getPostPaymentDetails(
        @Header("Authorization") authToken: String?,
        @Path("code") code: String,
        @Body postPaymentRequestModel: PostPaymentRequestModel
    ): PostPaymentRequestModel*/


    /*@GET("/v2/videos/generate-live-token")
    suspend fun getLiveToken(
        @Header("Authorization") token: String,
        @Header("Content-Type") contentType: String,
        @Query("l_id") lessonId: String,
        @Query("user_id") userId: String,
        @Query("role") role: String
    ): GetLiveTokenResponseModel*/

}