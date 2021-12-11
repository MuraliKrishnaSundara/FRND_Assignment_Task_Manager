package com.murali.taskmanager.repository

import androidx.lifecycle.LiveData
import com.murali.taskmanager.data.local.ClassDao
import com.murali.taskmanager.data.local.TaskModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RepositoryClass(val classDao: ClassDao) {

    fun addDataToTask(taskModel: TaskModel) {
        CoroutineScope(Dispatchers.IO).launch {
            classDao.insertDataInTask(taskModel)
        }
    }

    fun updateToTask(taskModel: TaskModel) {
        CoroutineScope(Dispatchers.IO).launch {
            classDao.updateDataInTask(taskModel)
        }
    }

    fun getAllTasks(): LiveData<List<TaskModel>> {
        return classDao.fetchDataFromTask()
    }

    fun getTasksByDate(date: String): LiveData<List<TaskModel>> {
        return classDao.getTaskByDate(date)
    }

}