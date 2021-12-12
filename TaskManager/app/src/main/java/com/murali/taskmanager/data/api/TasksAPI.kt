package com.murali.taskmanager.data.api

import com.murali.taskmanager.data.response.get.GetTasksRequestDTO
import com.murali.taskmanager.data.response.get.GetTasksResponseDTO
import com.murali.taskmanager.data.response.post.PostTasksRequestDTO
import com.murali.taskmanager.data.response.post.PostTasksResponseDTO
import retrofit2.http.*

interface TasksAPI {

    //posting tasks to api
    @POST("api/storeCalendarTask")
    suspend fun addTaskToAPI(
        @Body postTasksRequestDTO: PostTasksRequestDTO
    ): PostTasksResponseDTO

    //getting tasks from api
    @POST("api/getCalendarTaskList")
    suspend fun getTasksFromAPI(
        @Body getTasksRequestDTO: GetTasksRequestDTO
    ): GetTasksResponseDTO

    /*
        @POST("api/deleteCalendarTask")
        suspend fun signUp(
            @Body signupRequestModel: User
        ): SignupResponse
    */

}