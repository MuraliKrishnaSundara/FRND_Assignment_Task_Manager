package com.murali.taskmanager.view.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.murali.taskmanager.R
import com.murali.taskmanager.data.local.CalenderTaskRoomDataBase
import com.murali.taskmanager.data.local.CalenderTaskModel
import com.murali.taskmanager.databinding.FragmentTaskBinding
import com.murali.taskmanager.repository.TaskRepository
import com.murali.taskmanager.view.adapter.TaskAdapter
import com.murali.taskmanager.view.inter_face.onTaskDeleteClicked
import com.murali.taskmanager.view_model.TaskViewModel
import com.murali.taskmanager.view_model.ViewModelFactory

class TaskDeleteFragment : Fragment(), onTaskDeleteClicked {

    private lateinit var binding: FragmentTaskBinding
    private lateinit var itemTaskViewModel: TaskViewModel
    private lateinit var adapter: TaskAdapter
    private var list = arrayListOf<CalenderTaskModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentTaskBinding.bind(view)

        val roomDatabase = CalenderTaskRoomDataBase.getRoomDataBaseObject(requireContext())
        val dao = roomDatabase.getTaskDao()
        val repo = TaskRepository(dao)
        val viewModelFactory = ViewModelFactory(repo)

        itemTaskViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(TaskViewModel::class.java)

        itemTaskViewModel.getAllTasksFromRepository().observe(viewLifecycleOwner, Observer {
            list.clear()
            list.addAll(it)
            adapter.notifyDataSetChanged()
        })
        setRecyclerView()

    }

    private fun setRecyclerView() {
        adapter = TaskAdapter(list, this, itemTaskViewModel, this)
        val linearLayoutManager = LinearLayoutManager(requireContext())
        binding.apply {
            taskRecyclerView.adapter = adapter
            taskRecyclerView.layoutManager = linearLayoutManager
            //taskRecyclerView.isNestedScrollingEnabled = false
        }
    }

    override fun deleteTaskInViewModel(calenderTaskModel: CalenderTaskModel) {
        itemTaskViewModel.deleteTaskInRepository(calenderTaskModel)
    }

}