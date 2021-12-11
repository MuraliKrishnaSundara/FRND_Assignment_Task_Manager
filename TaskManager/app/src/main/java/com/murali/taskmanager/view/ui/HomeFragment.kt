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
import com.murali.taskmanager.data.local.RoomDataBaseClass
import com.murali.taskmanager.data.local.TaskModel
import com.murali.taskmanager.databinding.FragmentHomeBinding
import com.murali.taskmanager.repository.RepositoryClass
import com.murali.taskmanager.view.adapter.CalendarAdapter
import com.murali.taskmanager.view.adapter.DateClickListener
import com.murali.taskmanager.view.adapter.TaskAdapter
import com.murali.taskmanager.view.inter_face.TaskClickListener
import com.murali.taskmanager.view_model.ViewModelClass
import com.murali.taskmanager.view_model.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_home.*
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
class HomeFragment : Fragment(R.layout.fragment_home), DateClickListener, TaskClickListener {

    private var selectedDate: LocalDate? = null
    private lateinit var calendarAdapter: CalendarAdapter
    private lateinit var taskAdapter: TaskAdapter
    private lateinit var binding: FragmentHomeBinding
    private lateinit var itemViewModel: ViewModelClass
    private var listTask = arrayListOf<TaskModel>()
    private lateinit var daysInMonth: ArrayList<String>


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentHomeBinding.bind(view)

        val roomDatabase = RoomDataBaseClass.getDataBaseObject(requireContext())
        val dao = roomDatabase.getDao()
        val repo = RepositoryClass(dao)
        val viewModelFactory = ViewModelFactory(repo)
        itemViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(ViewModelClass::class.java)

        selectedDate = LocalDate.now()
        Toast.makeText(context, selectedDate.toString(), Toast.LENGTH_SHORT).show()
        setMonthView()
        jumpToDate()

        tasksOfSelectedDate(selectedDate.toString())

    }

    private fun tasksOfSelectedDate(today: String) {

        itemViewModel.getTasksByDate(today).observe(viewLifecycleOwner, Observer {
            listTask.clear()
            listTask.addAll(it)
            if (listTask.isNotEmpty()) {
                tvTasksCalendar.visibility = View.VISIBLE
            }
            if (listTask.isEmpty()) {
                nothingToShow.visibility = View.VISIBLE
            } else {
                nothingToShow.visibility = View.GONE
            }
            taskAdapter.notifyDataSetChanged()
        })

        taskAdapter = TaskAdapter(listTask, this, itemViewModel, this)
        val linearLayoutManagerTask = LinearLayoutManager(requireContext())
        binding.apply {

            //Task
            rcvTasksCalendar.adapter = taskAdapter
            rcvTasksCalendar.layoutManager = linearLayoutManagerTask
            rcvTasksCalendar.isNestedScrollingEnabled = false

        }
    }

    private fun jumpToDate() {
//        val dateDialog = Dialog
    }

    private fun setMonthView() {

        setPreviousMonth(view)
        setNextMonth(view)

        val currentDate = selectedDate.toString()
        daysInMonth = daysInMonthArray(selectedDate!!)
        calendarAdapter =
            CalendarAdapter(daysInMonth, this, currentDate, viewLifecycleOwner, itemViewModel)
        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(requireContext(), 7)

        //setting month year
        binding.apply {
            monthYearTV.text = presentDateMonthYear(selectedDate!!)
            calendarRecyclerView.layoutManager = layoutManager
            calendarRecyclerView.adapter = calendarAdapter
        }
//        currentdate.text = selectedDate.toString()
    }

    private fun daysInMonthArray(date: LocalDate): ArrayList<String> {
        val daysInMonthArray: ArrayList<String> = ArrayList()
        val yearMonth: YearMonth = YearMonth.from(date)
        val daysInMonth: Int = yearMonth.lengthOfMonth()
        val firstOfMonth = selectedDate!!.withDayOfMonth(1)
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
    private fun presentDateMonthYear(date: LocalDate): String? {
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MMMM yyyy")
        return date.format(formatter)
    }

    //on click of left arrow set previous month
    private fun setPreviousMonth(view: View?) {
        binding.previousMonth.setOnClickListener {
            selectedDate = selectedDate!!.minusMonths(1)
            setMonthView()
        }
    }

    //on click of right arrow set next month
    private fun setNextMonth(view: View?) {
        binding.nextMonth.setOnClickListener {
            selectedDate = selectedDate!!.plusMonths(1)
            setMonthView()
        }
    }

    override fun onDateClicked(date: String, position: Int, today: String) {

        tasksOfSelectedDate(today)

        if (date != "") {
            val message =
                "Selected Date " + date.toString() + " " + presentDateMonthYear(selectedDate!!)

            showEditTextDialogToAddTaskName(date.toString())

//            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        } else {
//            Toast.makeText(context, "null", Toast.LENGTH_LONG).show()
        }
    }


    //add task name and save data base
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
                val taskModel = TaskModel(
                    etTaskName.text.toString(),
                    etTaskDescription.text.toString(),
                    date
                )

                itemViewModel.insertDataInTaskTable(taskModel)

            }
            setNegativeButton("Cancel") { dialog, which ->
                //code for cancel
            }
            setView(dialogLayout)
            show()
        }
    }

    override fun taskItemClicked(taskModel: TaskModel) {
//        Toast.makeText(context, "Task Clicked", Toast.LENGTH_SHORT).show()
    }

    override fun taskCompletedClicked(taskModel: TaskModel) {
        //taskModel.status = 1
        itemViewModel.updateDataInTaskTable(taskModel)
    }

    override fun taskNotCompletedClicked(taskModel: TaskModel) {
        //taskModel.status = 0
        itemViewModel.updateDataInTaskTable(taskModel)
    }


}