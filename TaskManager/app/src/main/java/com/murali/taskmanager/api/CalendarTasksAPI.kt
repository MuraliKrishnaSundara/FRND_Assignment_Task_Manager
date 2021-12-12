package com.murali.taskmanager.api

import com.murali.taskmanager.data.response.delete.DeleteTaskRequestDTO
import com.murali.taskmanager.data.response.get.GetTasksRequestDTO
import com.murali.taskmanager.data.response.get.GetTasksResponseDTO
import com.murali.taskmanager.data.response.post.PostTasksRequestDTO
import com.murali.taskmanager.data.response.StatusResponseDTO
import retrofit2.http.*

interface CalendarTasksAPI {

    //posting task to api
    @POST("api/storeCalendarTask")
    suspend fun addTaskToAPI(
        @Body postTasksRequestDTO: PostTasksRequestDTO
    ): StatusResponseDTO

    //getting tasks from api
    @POST("api/getCalendarTaskList")
    suspend fun getTasksFromAPI(
        @Body getTasksRequestDTO: GetTasksRequestDTO
    ): GetTasksResponseDTO

    //deleting task in api
    @POST("api/deleteCalendarTask")
    suspend fun deleteTaskInApi(
        @Body deleteTaskRequestDTO: DeleteTaskRequestDTO
    ): StatusResponseDTO

}