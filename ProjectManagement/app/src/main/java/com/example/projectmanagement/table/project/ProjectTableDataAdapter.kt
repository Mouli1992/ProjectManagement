package com.example.projectmanagement.table.project

import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.projectmanagement.model.ProjectDetails
import de.codecrafters.tableview.TableView
import de.codecrafters.tableview.toolkit.LongPressAwareTableDataAdapter
import java.text.NumberFormat
import kotlin.math.log


class ProjectTableDataAdapter(context: Context, data: List<ProjectDetails?>?, tableView: TableView<ProjectDetails>)
    : LongPressAwareTableDataAdapter<ProjectDetails>(context, data, tableView) {
    companion object {
        private const val TEXT_SIZE = 14
        private val PRICE_FORMATTER = NumberFormat.getNumberInstance()
    }

    override fun getDefaultCellView(rowIndex: Int, columnIndex: Int, parentView: ViewGroup?): View {
        val projectDetails: ProjectDetails? = getRowData(rowIndex)
        var renderedView: View? = null
        when (columnIndex) {
            0 -> renderedView = renderProjectName(projectDetails)
            1 -> renderedView = renderProjectStatus(projectDetails)
            2 -> renderedView = renderProjectDeadLine(projectDetails)
        }
        return renderedView!!

    }

    private fun renderProjectStatus(projectDetails: ProjectDetails?): View? {
        return projectDetails!!.projectStatus?.let { renderString(it) }

    }

    private fun renderProjectName(projectDetails: ProjectDetails?): View? {
        return projectDetails!!.projectName?.let { renderString(it) }
    }

    private fun renderProjectDeadLine(projectDetails: ProjectDetails?): View? {
        return projectDetails!!.projectDeadline?.let { renderString(it.toString()) }
    }

    private fun renderString(value: String): View {
        val textView = TextView(context)

        textView.text = value
        textView.setPadding(20, 10, 20, 10)
        textView.textSize = ProjectTableDataAdapter.TEXT_SIZE.toFloat()
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