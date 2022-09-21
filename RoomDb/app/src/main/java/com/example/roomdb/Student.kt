package com.example.roomdb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName="student_Table")
data class dataClass(
    @PrimaryKey val id :Int,
    @ColumnInfo (name="First_name") val firstname:String?,
    @ColumnInfo (name="Last_name") val Lastname :String?
)
//ColumninFo sets the name of the column ,

//This class defines the Entities /database contents
