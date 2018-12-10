package com.carles.teamworktechtest.tasks.model

fun List<String>.toAddMultipleTasksRequest(): AddMultipleTasksRequest {
    var content = "";
    forEachIndexed { index, task -> if (index == lastIndex) content += task else content += task + "~|~" }
    return AddMultipleTasksRequest(content)
}