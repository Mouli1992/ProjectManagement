package com.example.projectmanagement.table.team

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.projectmanagement.model.ProjectDetails
import de.codecrafters.tableview.TableView
import de.codecrafters.tableview.toolkit.LongPressAwareTableDataAdapter
import java.text.DateFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat

class TeamMemberTableAdaptor (context: Context, data: List<ProjectDetails?>?, tableView: TableView<ProjectDetails>)
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
        return projectDetails!!.projectDeadline?.let {
            val dateFormat : DateFormat = SimpleDateFormat("dd/MM/yyyy")

            renderString(dateFormat.format(it.toDate()) )}
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