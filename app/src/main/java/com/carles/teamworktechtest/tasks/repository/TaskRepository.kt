package com.carles.teamworktechtest.tasks.repository

import com.carles.teamworktechtest.tasks.datasource.TaskCloudDatasource
import com.carles.teamworktechtest.tasks.model.Task
import io.reactivex.Observable

class TaskRepository(val cloudDatasource: TaskCloudDatasource) {

    fun getTasksByProject(projectId: String): Observable<List<Task>> =
        cloudDatasource.getTasksByProject(projectId)

    fun addMultipleTasks(projectId: String, tasks: List<String>): Observable<Int?> =
        cloudDatasource.addMultipleTasks(projectId, tasks)
}