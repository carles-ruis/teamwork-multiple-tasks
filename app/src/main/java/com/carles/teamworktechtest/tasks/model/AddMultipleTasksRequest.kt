package com.carles.teamworktechtest.tasks.model

import com.google.gson.annotations.SerializedName

data class AddMultipleTasksRequest(
    @SerializedName("content") var content: String
)