package com.murali.taskmanager.view.adapter

import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.collection.arraySetOf
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.murali.taskmanager.R
import com.murali.taskmanager.data.local.CalenderTaskModel
import com.murali.taskmanager.view_model.TaskViewModel
import kotlinx.android.synthetic.main.calendar_date_view.view.*
import java.time.LocalDate
import java.util.ArrayList

@RequiresApi(Build.VERSION_CODES.O)
class CalendarAdapter(
    private var dateList: ArrayList<String>,
    private val clickListener: DateClickListener,
    private val currentDate: String,
    private val lifecycleOwner: LifecycleOwner,
    private val itemTaskViewModel: TaskViewModel
) :
    RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>() {

    val bool = BooleanArray(dateList.size)
    private val itemViewList = ArrayList<View>()
    private var selectedDate: LocalDate? = null
    private var curDate: List<String>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.calendar_date_view, parent, false)
        itemViewList.add(view)
        return CalendarViewHolder(view, clickListener, lifecycleOwner, itemTaskViewModel)
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        val date = dateList[position]
        holder.setData(date)
    }

    override fun getItemCount(): Int {
        return dateList.size
    }

    inner class CalendarViewHolder(
        private val view: View,
        private val clickListener: DateClickListener,
        private val lifecycleOwner: LifecycleOwner,
        private val itemTaskViewModel: TaskViewModel
    ) : RecyclerView.ViewHolder(view) {

        internal fun setData(date: String) {
            view.apply {

                cellDayText.text = date

                var today = currentDate.substring(0, currentDate.length - 2)
                today += date

                var listOfTasks = arraySetOf<CalenderTaskModel>()
                itemTaskViewModel.getAllTasksFromRepositoryAccordingToDate(today)
                    .observe(lifecycleOwner, androidx.lifecycle.Observer {
                        listOfTasks.addAll(it)
                        if (listOfTasks.isNotEmpty()) {
                            eventsCardView.visibility = View.VISIBLE
                        }
                    })

                selectedDate = LocalDate.now()
                curDate = selectedDate.toString().split("-")

                val ar = currentDate.split("-")

                if (curDate!![2] == dateList[adapterPosition] && curDate!![0] == ar[0] && curDate!![1] == ar[1]) {
                    bool[adapterPosition] = true
                    view.rlDate.setBackgroundResource(R.drawable.current_day_bg)
                    view.cellDayText.setTextColor(Color.parseColor("#FFFFFFFF"))
                }

                if (view.cellDayText.text.toString() != "") {

                    rlDate.setOnClickListener {
                        if (bool[adapterPosition]) {
                            if (date == curDate!![2]) {
                                view.rlDate.setBackgroundResource(R.drawable.current_day_bg)
                                view.cellDayText.setTextColor(Color.parseColor("#FFFFFFFF"))
                            }
//                        rlDate.setBackgroundResource(R.drawable.selected_date_bg)
                        } else {
                            check()
                            if (date == curDate!![2]) {
                                view.rlDate.setBackgroundResource(R.drawable.current_day_bg)
                                view.cellDayText.setTextColor(Color.parseColor("#FFFFFFFF"))
                            } else {
                                rlDate.setBackgroundResource(R.drawable.selected_date_bg)
                            }
                            bool[adapterPosition] = true
                        }
                        clickListener.onDateClicked(date, adapterPosition, today)
                    }
                }
            }
        }

        private fun check() {
            for (i in 0 until dateList.size) {
                if (bool[i] && i != adapterPosition) {
                    if (itemViewList[i].cellDayText.text.toString() == curDate!![2]) {
                        itemViewList[i].cellDayText.setTextColor(Color.parseColor("#527FF3"))
                        itemViewList[i].rlDate.setBackgroundResource(0)
                    } else {
                        itemViewList[i].rlDate.setBackgroundResource(0)
                    }
                    bool[i] = false
                }
            }

        }

    }
}

interface DateClickListener {
    fun onDateClicked(date: String, position: Int, today: String)
}