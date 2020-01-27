package com.carles.teamworktechtest.tasks.datasource

import com.carles.teamworktechtest.common.network.BaseCloudDatasource

class TaskCloudDatasource : BaseCloudDatasource() {

    private val service: TaskService

    init {
        service = buildRetrofit().create(TaskService::class.java)
    }

    fun getTasksByProject(projectId: String) =
        service.getTasksByProject(projectId).map { it.taskItemsList ?: emptyList()}

    fun addMultipleTasks(projectId: String, tasks: List<String>) =
        service.postMultipleTasks(projectId, tasks.toAddMultipleTasksRequest())
            .map { it.success?.toInt() }
}