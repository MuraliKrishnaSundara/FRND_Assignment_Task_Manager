package com.murali.taskmanager.view.inter_face

import com.murali.taskmanager.data.local.CalenderTaskModel

interface onTaskClicked {

    fun deleteTaskInViewModel(calenderTaskModel: CalenderTaskModel)

}