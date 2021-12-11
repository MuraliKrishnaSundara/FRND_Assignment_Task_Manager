package com.murali.taskmanager.view.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.murali.taskmanager.R
import com.murali.taskmanager.data.local.RoomDataBaseClass
import com.murali.taskmanager.data.local.TaskModel
import com.murali.taskmanager.databinding.FragmentTaskBinding
import com.murali.taskmanager.repository.RepositoryClass
import com.murali.taskmanager.view.adapter.TaskAdapter
import com.murali.taskmanager.view.inter_face.TaskClickListener
import com.murali.taskmanager.view_model.ViewModelClass
import com.murali.taskmanager.view_model.ViewModelFactory
import okhttp3.internal.notifyAll

class TaskFragment : Fragment(), TaskClickListener {

    private lateinit var binding: FragmentTaskBinding
    private lateinit var itemViewModel: ViewModelClass

    private lateinit var adapter: TaskAdapter

    private var list = arrayListOf<TaskModel>()

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

        val roomDatabase = RoomDataBaseClass.getDataBaseObject(requireContext())
        val dao = roomDatabase.getDao()
        val repo = RepositoryClass(dao)
        val viewModelFactory = ViewModelFactory(repo)
        itemViewModel = ViewModelProviders.of(this, viewModelFactory).get(ViewModelClass::class.java)

        itemViewModel.getDataFromTask().observe(viewLifecycleOwner, Observer {
            list.clear()
            list.addAll(it)

            adapter.notifyDataSetChanged()
        })
        setRecyclerView()

    }

    private fun setRecyclerView() {
        adapter = TaskAdapter(list, this, itemViewModel, this)
        val linearLayoutManager = LinearLayoutManager(requireContext())
        binding.apply {
            taskRecyclerView.adapter = adapter
            taskRecyclerView.layoutManager = linearLayoutManager
            //taskRecyclerView.isNestedScrollingEnabled = false
        }
    }

    override fun taskItemClicked(taskModel: TaskModel) {
        TODO("Not yet implemented")
    }

    override fun taskCompletedClicked(taskModel: TaskModel) {
        TODO("Not yet implemented")
    }

    override fun taskNotCompletedClicked(taskModel: TaskModel) {
        TODO("Not yet implemented")
    }

}
/*

        setContentView(binding.root)
 */