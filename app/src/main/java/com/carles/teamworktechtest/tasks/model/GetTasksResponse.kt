package com.carles.teamworktechtest.tasks.model

import com.google.gson.annotations.SerializedName

data class GetTasksResponse(
    @SerializedName("STATUS") var status: String?,
    @SerializedName("todo-items") var taskItemsList: List<Task>?
)
