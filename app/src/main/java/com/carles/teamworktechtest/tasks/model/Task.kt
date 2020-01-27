package com.carles.teamworktechtest.tasks.model

import com.google.gson.annotations.SerializedName

data class Task(
    @SerializedName("id") val id: Int = 0,
    @SerializedName("description") val description: String?,
    @SerializedName("content") val content: String?,
    @SerializedName("project-id") val projectId: Int?,
    @SerializedName("project-name") val projectName: String?,
    @SerializedName("creator-id") val creatorId: String?,
    @SerializedName("creator-firstname") val creatorFirstname: String?,
    @SerializedName("creator-lastname") val creatorLastname: String?,
    @SerializedName("creator-avatar-url") val creatorAvatarUrl: String?,
    @SerializedName("order") val order: Int?,
    @SerializedName("status") val status: String?
)