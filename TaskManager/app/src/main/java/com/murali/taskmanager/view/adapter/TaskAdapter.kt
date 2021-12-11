package com.murali.taskmanager.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.murali.taskmanager.R
import com.murali.taskmanager.data.local.TaskModel
import com.murali.taskmanager.databinding.ItemTaskLayoutBinding
import com.murali.taskmanager.view.inter_face.TaskClickListener
import com.murali.taskmanager.view_model.ViewModelClass


class TaskAdapter(
    var list: ArrayList<TaskModel>,
    private val taskClickListener: TaskClickListener,
    private val itemViewModelClass: ViewModelClass,
    private val lifecycleOwner: LifecycleOwner,
) :
    RecyclerView.Adapter<TaskViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_task_layout, parent, false
            ),
            taskClickListener,
            itemViewModelClass,
            lifecycleOwner
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
    private val taskClickListener: TaskClickListener,
    private val itemViewModelClass: ViewModelClass,
    private val lifecycleOwner: LifecycleOwner
) : RecyclerView.ViewHolder(itemTaskLayoutBinding.root) {
    fun setTask(taskModel: TaskModel) {

        itemTaskLayoutBinding.task = taskModel

        /*
        itemTaskLayoutBinding.taskItemLayout.setOnClickListener {
            taskClickListener.taskItemClicked(taskModel)
        }
        */


    }
}