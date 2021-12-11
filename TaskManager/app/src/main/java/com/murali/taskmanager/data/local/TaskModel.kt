package com.murali.taskmanager.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_table")

class TaskModel(
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