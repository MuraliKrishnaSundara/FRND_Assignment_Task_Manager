package com.murali.taskmanager.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.murali.taskmanager.repository.TaskRepository

class ViewModelFactory(val taskRepository: TaskRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TaskViewModel(taskRepository) as T
    }

}