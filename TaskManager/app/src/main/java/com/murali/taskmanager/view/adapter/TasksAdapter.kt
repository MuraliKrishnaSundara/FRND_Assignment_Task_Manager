package com.murali.taskmanager.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.murali.taskmanager.R
import com.murali.taskmanager.data.response.get.CalenderTaskModel
import com.murali.taskmanager.databinding.ItemTaskLayoutBinding
import com.murali.taskmanager.view.inter_face.onTaskDeleteClicked

class TaskAdapter(
    var list: ArrayList<CalenderTaskModel>,
    private val onTaskDeleteClicked: onTaskDeleteClicked
) :
    RecyclerView.Adapter<TaskViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_task_layout, parent, false
            ),
            onTaskDeleteClicked
        )
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = list[position]
        holder.setTask(task)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}

class TaskViewHolder(
    private val itemTaskLayoutBinding: ItemTaskLayoutBinding,
    private val onTaskDeleteClicked: onTaskDeleteClicked
) : RecyclerView.ViewHolder(itemTaskLayoutBinding.root) {
    fun setTask(calenderTaskModel: CalenderTaskModel) {

        itemTaskLayoutBinding.task = calenderTaskModel


        itemTaskLayoutBinding.ivDeleteTask.setOnClickListener {
            onTaskDeleteClicked.deleteTaskInViewModel(calenderTaskModel.task_id)
        }


    }
}