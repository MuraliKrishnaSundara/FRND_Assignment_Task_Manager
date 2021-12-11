package com.murali.taskmanager.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.murali.taskmanager.repository.RepositoryClass

class ViewModelFactory(val repo: RepositoryClass) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ViewModelClass(repo) as T
    }

}