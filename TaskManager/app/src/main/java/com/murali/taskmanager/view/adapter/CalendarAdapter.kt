package com.murali.taskmanager.view.adapter

import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.murali.taskmanager.R
import com.murali.taskmanager.view.inter_face.onDateClickListener
import kotlinx.android.synthetic.main.calendar_date_view.view.*
import java.time.LocalDate
import java.util.ArrayList

@RequiresApi(Build.VERSION_CODES.O)
class CalendarAdapter(
    private var listOfDates: ArrayList<String>,
    private val onDateClicked: onDateClickListener,
    private val presentDate: String
) :
    RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>() {

    //list of item view
    private val listOfItemView = ArrayList<View>()
    val dateListSizeStatus = BooleanArray(listOfDates.size)
    private var todayDate: LocalDate? = null
    private var todayDateArray: List<String>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.calendar_date_view, parent, false)

        listOfItemView.add(view)
        return CalendarViewHolder(view, onDateClicked)
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        val date = listOfDates[position]
        holder.setData(date)
    }

    override fun getItemCount(): Int {
        return listOfDates.size
    }

    //calender view holder
    inner class CalendarViewHolder(
        private val view: View,
        private val clickListener: onDateClickListener
    ) : RecyclerView.ViewHolder(view) {

        internal fun setData(date: String) {
            view.apply {

                //getting date from text view
                tvDate.text = date

                //getting only YY-MM value of date
                var today = presentDate.substring(0, presentDate.length - 2)

                //making in YY-MM-DD format
                today += date

                //@RequiresApi(Build.VERSION_CODES.O) added for now
                todayDate = LocalDate.now()

                //splitting today date from YY-MM-DD to array format
                todayDateArray = todayDate.toString().split("-")

                //splitting present date from YY-MM-DD to array format
                val presentDateArray = presentDate.split("-")

                //setting background for today's date
                if (todayDateArray!![2] == listOfDates[adapterPosition] && todayDateArray!![0] == presentDateArray[0] && todayDateArray!![1] == presentDateArray[1]) {
                    dateListSizeStatus[adapterPosition] = true
                    view.relativeLayoutOfDate.setBackgroundResource(R.drawable.selected_today_date_backgroud)
                    view.tvDate.setTextColor(Color.parseColor("#FFFFFFFF"))
                }

                if (view.tvDate.text.toString() != "") {

                    relativeLayoutOfDate.setOnClickListener {
                        if (dateListSizeStatus[adapterPosition]) {
                            if (date == todayDateArray!![2]) {

                                //setting background for today's date
                                view.relativeLayoutOfDate.setBackgroundResource(R.drawable.selected_today_date_backgroud)
                                view.tvDate.setTextColor(Color.parseColor("#FFFFFFFF"))
                            }
                        } else {

                            //changing removing background if another date selected
                            ifDateSelectedChangedThenRemoveBackground()

                            if (date == todayDateArray!![2]) {

                                //setting background for today's date
                                view.relativeLayoutOfDate.setBackgroundResource(R.drawable.selected_today_date_backgroud)
                                view.tvDate.setTextColor(Color.parseColor("#FFFFFFFF"))
                            } else {

                                //setting background for other dates
                                relativeLayoutOfDate.setBackgroundResource(R.drawable.selected_date_background)
                            }

                            //storing true boolean value if selected
                            dateListSizeStatus[adapterPosition] = true
                        }

                        clickListener.onDateClicked(date, adapterPosition, today)
                    }
                }
            }
        }

        private fun ifDateSelectedChangedThenRemoveBackground() {
            for (i in 0 until listOfDates.size) {
                if (dateListSizeStatus[i] && i != adapterPosition) {

                    //if date selected changed then remove background
                    if (listOfItemView[i].tvDate.text.toString() == todayDateArray!![2]) {

                        //setting background for today's date
                        listOfItemView[i].tvDate.setTextColor(Color.parseColor("#FF03DAC5"))
                        listOfItemView[i].relativeLayoutOfDate.setBackgroundResource(0)

                    } else {

                        //setting background for other dates
                        listOfItemView[i].relativeLayoutOfDate.setBackgroundResource(0)

                    }

                    //storing false boolean value if un selected
                    dateListSizeStatus[i] = false
                }
            }
        }

    }


}