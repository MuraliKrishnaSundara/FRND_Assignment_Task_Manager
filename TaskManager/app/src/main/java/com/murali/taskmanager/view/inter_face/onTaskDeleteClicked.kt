package com.murali.taskmanager.view.inter_face

import com.murali.taskmanager.data.local.CalenderTaskModel

interface onTaskDeleteClicked {

    fun deleteTaskInViewModel(calenderTaskModel: CalenderTaskModel)

}