package com.example.projectmanagement.table.project

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.projectmanagement.model.TaskDetails
import com.example.projectmanagement.model.TeamDetails
import de.codecrafters.tableview.TableView
import de.codecrafters.tableview.toolkit.LongPressAwareTableDataAdapter
import java.text.NumberFormat

class TeamLstTableAdaptor(context: Context, data: List<TeamDetails?>?, tableView: TableView<TeamDetails>)
    : LongPressAwareTableDataAdapter<TeamDetails>(context, data, tableView) {
    companion object {
        private const val TEXT_SIZE = 14
        private val PRICE_FORMATTER = NumberFormat.getNumberInstance()
    }

    override fun getDefaultCellView(rowIndex: Int, columnIndex: Int, parentView: ViewGroup?): View {
        val teamDetails: TeamDetails? = getRowData(rowIndex)
        var renderedView: View? = null
        when (columnIndex) {
            0 -> renderedView = renderTeamEmail(teamDetails)

        }
        return renderedView!!

    }


    private fun renderTeamEmail(teamDetails: TeamDetails?): View? {
        return teamDetails!!.emailId?.let { renderString(it) }
    }

    private fun renderString(value: String): View {
        val textView = TextView(context)

        textView.text = value
        textView.setPadding(20, 10, 20, 10)
        textView.textSize = TeamLstTableAdaptor.TEXT_SIZE.toFloat()
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