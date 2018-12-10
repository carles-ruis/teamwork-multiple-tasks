package com.carles.teamworktechtest.tasks.datasource

import com.carles.teamworktechtest.common.network.BaseCloudDatasource
import com.carles.teamworktechtest.tasks.model.Task
import com.carles.teamworktechtest.tasks.model.toAddMultipleTasksRequest
import io.reactivex.Observable

class TaskCloudDatasource : BaseCloudDatasource() {

    private val service: TaskService

    init {
        service = buildRetrofit().create(TaskService::class.java)
    }

    fun getTasksByProject(projectId: String): Observable<List<Task>> =
        service.getTasksByProject(projectId).map { it.taskItemsList }

    fun addMultipleTasks(projectId: String, tasks: List<String>): Observable<Int?> =
        service.postMultipleTasks(projectId, tasks.toAddMultipleTasksRequest()).map { it.success?.toInt() }
}