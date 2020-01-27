package com.carles.teamworktechtest.tasks.datasource

import com.carles.teamworktechtest.tasks.model.Task
import com.google.gson.annotations.SerializedName

data class AddMultipleTasksRequest(
    @SerializedName("content") val content: String
)

data class AddMultipleTasksResponse(
    @SerializedName("tasklistId") val taskListId: String?,
    @SerializedName("success") val success: String?,
    @SerializedName("taskIds") val taskIds: String?,
    @SerializedName("STATUS") val status: String?,
    @SerializedName("failed") val failed: String?
)

data class GetTasksResponse(
    @SerializedName("STATUS") val status: String?,
    @SerializedName("todo-items") val taskItemsList: List<Task>?
)

fun List<String>.toAddMultipleTasksRequest(): AddMultipleTasksRequest {
    var content = "";
    forEachIndexed { index, task -> if (index == lastIndex) content += task else content += task + "~|~" }
    return AddMultipleTasksRequest(content)
}