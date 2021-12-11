package com.murali.taskmanager.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "calender_tasks_table")

class CalenderTaskModel(
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "description")
    val desc: String,
    @ColumnInfo(name = "date")
    val date: String
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int? = null
}