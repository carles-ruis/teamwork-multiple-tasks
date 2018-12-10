package com.carles.teamworktechtest.tasks.model

import com.google.gson.annotations.SerializedName

data class Task(
    @SerializedName("id") var id: Int = 0,
    @SerializedName("description") var description: String?,
    @SerializedName("content") var content: String?,
    @SerializedName("project-id") var projectId: Int?,
    @SerializedName("project-name") var projectName: String?,
    @SerializedName("creator-id") var creatorId: String?,
    @SerializedName("creator-firstname") var creatorFirstname: String?,
    @SerializedName("creator-lastname") var creatorLastname: String?,
    @SerializedName("creator-avatar-url") var creatorAvatarUrl: String?,
    @SerializedName("order") var order: Int?,
    @SerializedName("status") var status: String?
)