package com.murali.taskmanager.view.inter_face

import com.murali.taskmanager.data.local.TaskModel

interface TaskClickListener {

    fun taskItemClicked(taskModel: TaskModel)

    fun taskCompletedClicked(taskModel: TaskModel)

    fun taskNotCompletedClicked(taskModel: TaskModel)

}