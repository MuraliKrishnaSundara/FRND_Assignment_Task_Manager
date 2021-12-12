package com.murali.taskmanager.view.ui

import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.murali.taskmanager.R
import com.murali.taskmanager.data.local.CalendarTaskRoomDataBase
import com.murali.taskmanager.data.response.delete.DeleteTaskRequestDTO
import com.murali.taskmanager.data.response.get.CalendarTaskModel
import com.murali.taskmanager.data.response.get.GetTasksRequestDTO
import com.murali.taskmanager.data.response.post.PostTasksRequestDTO
import com.murali.taskmanager.data.response.post.TaskDTO
import com.murali.taskmanager.databinding.FragmentHomeBinding
import com.murali.taskmanager.repository.TaskRepository
import com.murali.taskmanager.view.adapter.CalendarAdapter
import com.murali.taskmanager.view.adapter.TaskAdapter
import com.murali.taskmanager.view.inter_face.onDateClickListener
import com.murali.taskmanager.view.inter_face.onTaskDeleteClicked
import com.murali.taskmanager.view_model.TaskViewModel
import com.murali.taskmanager.view_model.ViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
@RequiresApi(Build.VERSION_CODES.O)
class HomeFragment : Fragment(R.layout.fragment_home), onDateClickListener, onTaskDeleteClicked {

    private var listOfCalenderTasks = arrayListOf<CalendarTaskModel>()
    private lateinit var taskAdapter: TaskAdapter
    private val user_id: Int = 1005
    private lateinit var fragmentHomeBinding: FragmentHomeBinding
    private lateinit var taskViewModel: TaskViewModel
    private var selectedDate: LocalDate? = null
    private lateinit var listOfDaysInMonth: ArrayList<String>
    private lateinit var calendarAdapter: CalendarAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val calenderTaskRoomDataBase =
            CalendarTaskRoomDataBase.getRoomDataBaseObject(requireContext())
        val calenderTaskDao = calenderTaskRoomDataBase.getCalenderTaskDao()
        val taskRepository = TaskRepository(calenderTaskDao)
        val viewModelFactory = ViewModelFactory(taskRepository)

        fragmentHomeBinding = FragmentHomeBinding.bind(view)
        taskViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(TaskViewModel::class.java)

        //@RequiresApi(Build.VERSION_CODES.O) added for now
        selectedDate = LocalDate.now()

        Log.d("murali", selectedDate.toString())
        getTasksOfSelectedDate(selectedDate.toString())
        setMonthView()

    }

    private fun setMonthView() {

        setLastMonth(view)
        setNextMonth(view)

        //selected date
        val currentDate = selectedDate.toString()

        //getting day list of month
        listOfDaysInMonth = daysInMonthArray(selectedDate!!)

        //setting adapter
        calendarAdapter =
            CalendarAdapter(
                listOfDaysInMonth,
                this,
                currentDate
            )

        //setting layout manager
        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(requireContext(), 7)

        fragmentHomeBinding.apply {

            //setting month year
            tvMonthYearView.text = monthAndYearOfPresentDate(selectedDate!!)

            //setting dates to month recycler view
            monthRecyclerView.layoutManager = layoutManager
            monthRecyclerView.adapter = calendarAdapter

        }
    }

    private fun daysInMonthArray(date: LocalDate): ArrayList<String> {

        val daysInMonthArray: ArrayList<String> = ArrayList()

        //year and month of date
        val yearMonthOfDate: YearMonth = YearMonth.from(date)

        //number of Days in month
        val numberOfDaysInMonth: Int = yearMonthOfDate.lengthOfMonth()

        //day of first date of month
        val dayOfFirstDateOfMonth = selectedDate!!.withDayOfMonth(1)

        //day of week
        val dayOfWeek = dayOfFirstDateOfMonth.dayOfWeek.value

        for (i in 1..42) {

            if (i <= dayOfWeek || i > numberOfDaysInMonth + dayOfWeek) {

                //if i exceeds number of days in that month then it will break
                if (i > numberOfDaysInMonth + dayOfWeek) break

                //if no date of that day present then it will be empty
                daysInMonthArray.add("")

            } else {

                //adding day of dates in daysInMonthArray array list
                daysInMonthArray.add((i - dayOfWeek).toString())
            }

        }

        return daysInMonthArray
    }

    //get month year of current date
    private fun monthAndYearOfPresentDate(date: LocalDate): String? {
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MMMM yyyy")
        return date.format(formatter)
    }

    //on click of left arrow set previous month
    private fun setLastMonth(view: View?) {
        fragmentHomeBinding.ivLastMonth.setOnClickListener {
            //subtracting month
            selectedDate = selectedDate!!.minusMonths(1)
            setMonthView()
        }
    }

    //on click of right arrow set next month
    private fun setNextMonth(view: View?) {
        fragmentHomeBinding.ivNextMonth.setOnClickListener {
            //adding month
            selectedDate = selectedDate!!.plusMonths(1)
            setMonthView()
        }
    }

    override fun onDateClicked(date: String, position: Int, today: String) {

        //clicked date
        val taskDate =
            "" + date.toString() + " " + monthAndYearOfPresentDate(selectedDate!!)

        if (date != "") {
            //show edit text dialog to add task
            showEditTextDialogToAddTaskName(taskDate)
        } else {
            Toast.makeText(context, "Date Error", Toast.LENGTH_LONG).show()
        }

        //set recycler view of selected task
        getTasksOfSelectedDate(taskDate)

    }

    //add task name and post to api
    private fun showEditTextDialogToAddTaskName(date: String) {

        val builder = AlertDialog.Builder(context)
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.add_task_layout, null)
        val etTaskName = dialogLayout.findViewById<EditText>(R.id.etTaskName)
        val etTaskDescription = dialogLayout.findViewById<EditText>(R.id.etTaskDescription)

        with(builder) {
            setTitle(" Add Task for $date")

            setPositiveButton("Add") { dialog, which ->

                //setting data to model
                val taskModel = TaskDTO(
                    etTaskName.text.toString(),
                    etTaskDescription.text.toString(),
                    date
                )

                val postTasksRequestDTO = PostTasksRequestDTO(user_id = user_id, task = taskModel)

                //posting data to api
                taskViewModel.postTaskToApiThroughViewModel(postTasksRequestDTO)

            }

            setNegativeButton("Cancel") { dialog, which ->
                //task will not add to data base
            }

            setView(dialogLayout)
            show()
        }
    }

    private fun getTasksOfSelectedDate(today: String) {

        //getting tasks list of selected from view model
        taskViewModel.getAllTasksFromRoomDatabaseAccordingToDateThroughViewModel(today)
            .observe(viewLifecycleOwner, Observer {
                listOfCalenderTasks.clear()
                listOfCalenderTasks.addAll(it)
                taskAdapter.notifyDataSetChanged()
            })

        //setting recycler view
        taskAdapter = TaskAdapter(listOfCalenderTasks, this)
        val linearLayoutManagerTask = LinearLayoutManager(requireContext())
        fragmentHomeBinding.apply {
            calenderTasksRecyclerView.adapter = taskAdapter
            calenderTasksRecyclerView.layoutManager = linearLayoutManagerTask
            calenderTasksRecyclerView.isNestedScrollingEnabled = false
        }

    }

    override fun deleteTaskThroughViewModelInApi(task_id: Int) {

        /*
        as after deleting task in api we need update data in recycler view,
        so here after successfully deleting task from api
        we are again making api call and storing in room db
        which can be observed through live data and the recycler view will be updated.
         */

        val deleteTaskRequestDTO = DeleteTaskRequestDTO(user_id = user_id, task_id = task_id)
        val getTasksRequestDTO = GetTasksRequestDTO(user_id = user_id)
        taskViewModel.deleteTaskInApiThroughViewModel(deleteTaskRequestDTO, getTasksRequestDTO)

    }

}