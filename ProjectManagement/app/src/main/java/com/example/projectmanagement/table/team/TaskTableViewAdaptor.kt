package com.example.projectmanagement.table.team

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.projectmanagement.model.TaskDetails
import com.example.projectmanagement.table.project.ProjectTableDataAdapter
import de.codecrafters.tableview.TableView
import de.codecrafters.tableview.toolkit.LongPressAwareTableDataAdapter
import java.text.NumberFormat

class TaskTableViewAdaptor  (context: Context, data: List<TaskDetails?>?, tableView: TableView<TaskDetails>)
    : LongPressAwareTableDataAdapter<TaskDetails>(context, data, tableView) {
    companion object {
        private const val TEXT_SIZE = 14
        private val PRICE_FORMATTER = NumberFormat.getNumberInstance()
    }

    override fun getDefaultCellView(rowIndex: Int, columnIndex: Int, parentView: ViewGroup?): View {
        val taskDetails: TaskDetails? = getRowData(rowIndex)
        var renderedView: View? = null
        when (columnIndex) {
            0 -> renderedView = renderTaskName(taskDetails)
            1 -> renderedView = renderTaskStatus(taskDetails)
            2 -> renderedView = renderTaskAssignedTo(taskDetails)
        }
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
        textView.textSize = TEXT_SIZE.toFloat()
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