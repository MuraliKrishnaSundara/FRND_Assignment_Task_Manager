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
import com.murali.taskmanager.data.response.delete.DeleteTaskRequestDTO
import com.murali.taskmanager.data.response.get.GetTasksRequestDTO
import com.murali.taskmanager.databinding.FragmentTaskBinding
import com.murali.taskmanager.repository.TaskRepository
import com.murali.taskmanager.view.adapter.TaskAdapter
import com.murali.taskmanager.view.inter_face.onTaskDeleteClicked
import com.murali.taskmanager.view_model.TaskViewModel
import com.murali.taskmanager.view_model.ViewModelFactory

class TaskFragment : Fragment(), onTaskDeleteClicked {

    private lateinit var binding: FragmentTaskBinding
    private lateinit var itemTaskViewModel: TaskViewModel
    private val user_id: Int = 1005
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
        val dao = roomDatabase.getCalenderTaskDao()
        val repo = TaskRepository(dao)
        val viewModelFactory = ViewModelFactory(repo)

        //setting recycler view and getting data from room db
        setRecyclerView()

        //to get instance
        itemTaskViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(TaskViewModel::class.java)

        //observing live data
        itemTaskViewModel.getAllTasksFromRepository().observe(viewLifecycleOwner, Observer {
            list.clear()
            list.addAll(it)
            adapter.notifyDataSetChanged()
        })

        //getting tasks from api
        val getTasksRequestDTO = GetTasksRequestDTO(user_id = user_id)
        itemTaskViewModel.getAllApiTasksFromRepository(getTasksRequestDTO)

    }

    private fun setRecyclerView() {
        adapter = TaskAdapter(list, this)
        val linearLayoutManager = LinearLayoutManager(requireContext())
        binding.apply {
            taskRecyclerView.adapter = adapter
            taskRecyclerView.layoutManager = linearLayoutManager
        }
    }

    override fun deleteTaskInViewModel(task_id: Int) {

        /*
        as after deleting task in api we need update data in recycler view,
        so here after successfully deleting task from api
        we are again making api call and storing in room db
        which can be observed through live data and the recycler view will be updated.
         */

        val deleteTaskRequestDTO = DeleteTaskRequestDTO(user_id = user_id, task_id = task_id)
        val getTasksRequestDTO = GetTasksRequestDTO(user_id = user_id)
        itemTaskViewModel.deleteTaskInRepository(deleteTaskRequestDTO, getTasksRequestDTO)

    }

}