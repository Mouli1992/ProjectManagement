package com.example.projectmanagement.utils

import com.google.firebase.Timestamp
import java.text.DateFormat
import java.text.SimpleDateFormat

class DateUtils {

     fun  convertTimeStampToDate( timeStamp : Timestamp): String {
        val dateFormat : DateFormat = SimpleDateFormat("dd/MM/yyyy")
        return dateFormat.format(timeStamp.toDate()) }

    }
