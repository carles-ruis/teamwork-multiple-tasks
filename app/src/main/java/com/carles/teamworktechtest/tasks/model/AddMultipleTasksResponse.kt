package com.carles.teamworktechtest.tasks.model

import com.google.gson.annotations.SerializedName

data class AddMultipleTasksResponse(
    @SerializedName("tasklistId") var taskListId: String?,
    @SerializedName("success") var success: String?,
    @SerializedName("taskIds") var taskIds: String?,
    @SerializedName("STATUS") var status: String?,
    @SerializedName("failed") var failed: String?
)