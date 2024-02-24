package com.example.todo.database.model

import android.os.Parcelable
import android.widget.TextView
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Todo(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo
    var title: String?,
    @ColumnInfo
    var description: String?,
    @ColumnInfo
    var date: Long,
    @ColumnInfo
    var isDone: Boolean?,
) : Parcelable