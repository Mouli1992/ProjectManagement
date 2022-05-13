package com.example.projectmanagement.table.project

import android.R
import android.content.Context
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.example.projectmanagement.model.TaskDetails
import com.example.projectmanagement.utils.TableHeader
import de.codecrafters.tableview.SortableTableView
import de.codecrafters.tableview.model.TableColumnWeightModel
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter
import de.codecrafters.tableview.toolkit.SortStateViewProviders
import de.codecrafters.tableview.toolkit.TableDataRowBackgroundProviders


class SortableProjectTableView @JvmOverloads constructor(
    context: Context?,
    attributes: AttributeSet? = null,
    styleAttributes: Int = R.attr.listViewStyle
) :
    SortableTableView<TaskDetails?>(context, attributes, styleAttributes) {
    init {
        val simpleTableHeaderAdapter = TableHeader.getTaskTableHeader(context)

        headerAdapter = simpleTableHeaderAdapter
        headerSortStateViewProvider = SortStateViewProviders.brightArrows()

    }

}