package com.murali.taskmanager.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.murali.taskmanager.R
import com.murali.taskmanager.data.response.get.ApiAndRoomDBCalendarTasksModel
import com.murali.taskmanager.databinding.TaskItemViewBinding
import com.murali.taskmanager.view.inter_face.onTaskDeleteClicked

class TaskAdapter(
    var apiAndRoomDBCalendarTasksList: ArrayList<ApiAndRoomDBCalendarTasksModel>,
    private val onTaskDeleteClicked: onTaskDeleteClicked
) :
    RecyclerView.Adapter<TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.task_item_view, parent, false
            ),
            onTaskDeleteClicked
        )
    }

    override fun onBindViewHolder(taskViewHolder: TaskViewHolder, position: Int) {
        val calendarTask = apiAndRoomDBCalendarTasksList[position]
        taskViewHolder.setTask(calendarTask)
    }

    override fun getItemCount(): Int {
        return apiAndRoomDBCalendarTasksList.size
    }

}


class TaskViewHolder(
    private val taskItemViewBinding: TaskItemViewBinding,
    private val onTaskDeleteClicked: onTaskDeleteClicked
) : RecyclerView.ViewHolder(taskItemViewBinding.root) {
    fun setTask(apiAndRoomDBCalendarTasksModel: ApiAndRoomDBCalendarTasksModel) {

        //passing data to view through calendarTaskModel data binding
        taskItemViewBinding.calendarTaskModel = apiAndRoomDBCalendarTasksModel

        //on click of delete task, it will get task_id of that task and pass to view model to delete it
        taskItemViewBinding.ivDeleteTask.setOnClickListener {
            onTaskDeleteClicked.deleteTaskThroughViewModelInApi(apiAndRoomDBCalendarTasksModel.task_id)
        }

    }

}