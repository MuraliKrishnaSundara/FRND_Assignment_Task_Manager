package com.murali.taskmanager.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.murali.taskmanager.data.local.TaskModel
import com.murali.taskmanager.repository.RepositoryClass

class ViewModelClass(val repo: RepositoryClass) : ViewModel() {

    fun insertDataInTaskTable(taskModel: TaskModel) {
        repo.addDataToTask(taskModel)
    }

    fun updateDataInTaskTable(taskModel: TaskModel) {
        repo.updateToTask(taskModel)
    }

    fun getDataFromTask(): LiveData<List<TaskModel>> {
        return repo.getAllTasks()
    }

    fun getTasksByDate(date: String): LiveData<List<TaskModel>> {
        return repo.getTasksByDate(date)
    }

}