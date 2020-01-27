package com.carles.teamworktechtest.tasks.datasource

import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface TaskService {

    @GET("/projects/{id}/tasks.json")
    fun getTasksByProject(@Path("id") projectId: String): Observable<GetTasksResponse>

    @POST("/projects/{projid}/tasks/quickadd.json")
    fun postMultipleTasks(@Path("projid") projectId: String, @Body request: AddMultipleTasksRequest)
            : Observable<AddMultipleTasksResponse>

}