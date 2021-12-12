package com.murali.taskmanager.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.murali.taskmanager.repository.CalendarTaskRepository

class ViewModelFactory(val calendarTaskRepository: CalendarTaskRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CalendarTaskViewModel(calendarTaskRepository) as T
    }

}