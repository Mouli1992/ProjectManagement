package com.example.projectmanagement.table.team

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.projectmanagement.model.ProjectDetails
import com.example.projectmanagement.table.project.ProjectTableDataAdapter
import com.example.projectmanagement.utils.PROJECT_STATUS_PENDING
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
        return if(projectDetails!!.projectStatus.equals(PROJECT_STATUS_PENDING)){
            projectDetails.projectStatus?.let { renderStatus(it) }

        }else{
            projectDetails!!.projectStatus?.let { renderString(it) }

        }

    }

    private fun renderProjectName(projectDetails: ProjectDetails?): View? {
        return if(projectDetails!!.projectStatus.equals(PROJECT_STATUS_PENDING)){
            projectDetails.projectName?.let { renderStatus(it) }

        }else{
            projectDetails!!.projectName?.let { renderString(it) }

        }
    }

    private fun renderProjectDeadLine(projectDetails: ProjectDetails?): View? {
        return if(projectDetails!!.projectStatus.equals(PROJECT_STATUS_PENDING)){
            projectDetails.projectDeadline?.let {
                val dateFormat : DateFormat = SimpleDateFormat("dd/MM/yyyy")
                renderStatus(dateFormat.format(it.toDate()) )
            }

        }else{
            projectDetails!!.projectDeadline?.let {
                val dateFormat : DateFormat = SimpleDateFormat("dd/MM/yyyy")
                renderString(dateFormat.format(it.toDate()) )}

        }

    }


    private fun renderStatus(value : String) : View {
        val textView = TextView(context)
        textView.text=value
        textView.setPadding(20, 10, 20, 10)
        textView.textSize = TEXT_SIZE.toFloat()
        textView.setTextColor(Color.RED);
        return textView
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