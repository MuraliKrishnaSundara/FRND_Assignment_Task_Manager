package com.murali.taskmanager.view.ui

import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
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
import com.murali.taskmanager.data.local.CalenderTaskRoomDataBase
import com.murali.taskmanager.data.local.CalenderTaskModel
import com.murali.taskmanager.data.response.post.PostTasksRequestDTO
import com.murali.taskmanager.data.response.post.TaskDTO
import com.murali.taskmanager.databinding.FragmentHomeBinding
import com.murali.taskmanager.repository.TaskRepository
import com.murali.taskmanager.view.adapter.CalendarAdapter
import com.murali.taskmanager.view.adapter.TaskAdapter
import com.murali.taskmanager.view.inter_face.DateClickListener
import com.murali.taskmanager.view.inter_face.onTaskDeleteClicked
import com.murali.taskmanager.view_model.TaskViewModel
import com.murali.taskmanager.view_model.ViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
@RequiresApi(Build.VERSION_CODES.O)
class HomeFragment : Fragment(R.layout.fragment_home), DateClickListener, onTaskDeleteClicked {

    private var dateSelected: LocalDate? = null
    private lateinit var listOfDaysInMonth: ArrayList<String>
    private var listOfCalenderTasks = arrayListOf<CalenderTaskModel>()
    private lateinit var calendarAdapter: CalendarAdapter
    private lateinit var taskAdapter: TaskAdapter
    private lateinit var fragmentHomeBinding: FragmentHomeBinding
    private lateinit var taskViewModel: TaskViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val calenderTaskRoomDataBase =
            CalenderTaskRoomDataBase.getRoomDataBaseObject(requireContext())
        val calenderTaskDao = calenderTaskRoomDataBase.getCalenderTaskDao()
        val taskRepository = TaskRepository(calenderTaskDao)
        val viewModelFactory = ViewModelFactory(taskRepository)

        fragmentHomeBinding = FragmentHomeBinding.bind(view)
        taskViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(TaskViewModel::class.java)
        dateSelected = LocalDate.now()
        calenderTasksOfSelectedDate(dateSelected.toString())
        createMonthLayout()

    }

    private fun calenderTasksOfSelectedDate(today: String) {

        //getting tasks list from view model
        taskViewModel.getAllTasksFromRepositoryAccordingToDate(today)
            .observe(viewLifecycleOwner, Observer {
                listOfCalenderTasks.clear()
                listOfCalenderTasks.addAll(it)
                if (listOfCalenderTasks.isNotEmpty()) {
                    tvTasksCalendar.visibility = View.VISIBLE
                }
                if (listOfCalenderTasks.isEmpty()) {
                    nothingToShow.visibility = View.VISIBLE
                } else {
                    nothingToShow.visibility = View.GONE
                }
                taskAdapter.notifyDataSetChanged()
            })

        //setting recycler view
        taskAdapter = TaskAdapter(listOfCalenderTasks, this, taskViewModel, this)
        val linearLayoutManagerTask = LinearLayoutManager(requireContext())
        fragmentHomeBinding.apply {
            calenderTasksRecyclerView.adapter = taskAdapter
            calenderTasksRecyclerView.layoutManager = linearLayoutManagerTask
            calenderTasksRecyclerView.isNestedScrollingEnabled = false
        }

    }

    private fun createMonthLayout() {
        setPreviousMonth(view)
        setNextMonth(view)
        val currentDate = dateSelected.toString()
        listOfDaysInMonth = daysInMonthArray(dateSelected!!)
        calendarAdapter =
            CalendarAdapter(
                listOfDaysInMonth,
                this,
                currentDate,
                viewLifecycleOwner,
                taskViewModel
            )
        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(requireContext(), 7)

        //setting month year
        fragmentHomeBinding.apply {
            monthYearTV.text = monthAndYearOfPresentDate(dateSelected!!)
            calendarRecyclerView.layoutManager = layoutManager
            calendarRecyclerView.adapter = calendarAdapter
        }
    }

    private fun daysInMonthArray(date: LocalDate): ArrayList<String> {
        val daysInMonthArray: ArrayList<String> = ArrayList()
        val yearMonth: YearMonth = YearMonth.from(date)
        val daysInMonth: Int = yearMonth.lengthOfMonth()
        val firstOfMonth = dateSelected!!.withDayOfMonth(1)
        val dayOfWeek = firstOfMonth.dayOfWeek.value
        for (i in 1..42) {
            if (i <= dayOfWeek || i > daysInMonth + dayOfWeek) {
                if (i > daysInMonth + dayOfWeek) break
                daysInMonthArray.add("")
            } else {
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
    private fun setPreviousMonth(view: View?) {
        fragmentHomeBinding.previousMonth.setOnClickListener {
            dateSelected = dateSelected!!.minusMonths(1)
            createMonthLayout()
        }
    }

    //on click of right arrow set next month
    private fun setNextMonth(view: View?) {
        fragmentHomeBinding.nextMonth.setOnClickListener {
            dateSelected = dateSelected!!.plusMonths(1)
            createMonthLayout()
        }
    }

    override fun onDateClicked(date: String, position: Int, today: String) {

        calenderTasksOfSelectedDate(today)

        if (date != "") {
            val message =
                "Selected Date " + date.toString() + " " + monthAndYearOfPresentDate(dateSelected!!)
            //show edit text dialog to add task
            showEditTextDialogToAddTaskName(
                "" + date.toString() + " " + monthAndYearOfPresentDate(
                    dateSelected!!
                )
            )
        } else {
            Toast.makeText(context, "Date Error", Toast.LENGTH_LONG).show()
        }

    }


    //add task name and save to data base
    private fun showEditTextDialogToAddTaskName(date: String) {

        val builder = AlertDialog.Builder(context)
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.add_task_layout, null)

        val etTaskName = dialogLayout.findViewById<EditText>(R.id.etTaskName)
        val etTaskDescription = dialogLayout.findViewById<EditText>(R.id.etTaskDescription)

        with(builder) {
            setTitle(" Add Task For Selected Date ")
            setPositiveButton("Add") { dialog, which ->

                //storing in database
                val taskModel = TaskDTO(
                    etTaskName.text.toString(),
                    etTaskDescription.text.toString(),
                    date
                )

                val postTasksRequestDTO = PostTasksRequestDTO(user_id = 1005, task = taskModel)
                taskViewModel.addTaskApiToThroughRepository(postTasksRequestDTO)

            }
            setNegativeButton("Cancel") { dialog, which ->
                //task will not add to data base
            }
            setView(dialogLayout)
            show()
        }
    }

    override fun deleteTaskInViewModel(calenderTaskModel: CalenderTaskModel) {
        //taskModel.status = 0
        taskViewModel.deleteTaskInRepository(calenderTaskModel)
    }

}