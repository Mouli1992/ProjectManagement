package com.example.projectmanagement.table.project

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.projectmanagement.model.TaskDetails
import de.codecrafters.tableview.TableView
import de.codecrafters.tableview.toolkit.LongPressAwareTableDataAdapter
import java.text.NumberFormat

class TaskTableViewAdaptor2(context: Context, data: List<TaskDetails?>?, tableView: TableView<TaskDetails>)
    : LongPressAwareTableDataAdapter<TaskDetails>(context, data, tableView) {
    companion object {
        private const val TEXT_SIZE = 14
        private val PRICE_FORMATTER = NumberFormat.getNumberInstance()
    }

    override fun getDefaultCellView(rowIndex: Int, columnIndex: Int, parentView: ViewGroup?): View {
        val taskDetails: TaskDetails? = getRowData(rowIndex)
        println("Task Details in Cell$taskDetails" )
        var renderedView: View? = null
        when (columnIndex) {
            0 -> renderedView = renderTaskName(taskDetails)
            1 -> renderedView = renderTaskStatus(taskDetails)
            2 -> renderedView = renderTaskAssignedTo(taskDetails)
        }
        println(renderedView)
        return renderedView!!

    }

    private fun renderTaskStatus(taskDetails: TaskDetails?): View? {
        return taskDetails!!.taskStatus?.let { renderString(it) }

    }

    private fun renderTaskName(taskDetails: TaskDetails?): View? {
        return taskDetails!!.taskName?.let { renderString(it) }
    }

    private fun renderTaskAssignedTo(taskDetails: TaskDetails?): View? {
        return taskDetails!!.assignedTo?.let { renderString(it.toString()) }
    }

    private fun renderString(value: String): View {
        val textView = TextView(context)

        textView.text = value
        textView.setPadding(20, 10, 20, 10)
        textView.textSize = TaskTableViewAdaptor2.TEXT_SIZE.toFloat()
        return textView
    }

    override fun getLongPressCellView(
        rowIndex: Int,
        columnIndex: Int,
        parentView: ViewGroup?
    ): View {
        TODO("Not yet implemented")
    }
}