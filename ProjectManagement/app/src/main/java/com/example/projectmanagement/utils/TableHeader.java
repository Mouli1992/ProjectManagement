package com.example.projectmanagement.utils;

import android.content.Context;

import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

public class TableHeader {

    private static final String[] TABLE_HEADERS = { "ProjectName", "Status", "Deadline" };
    public static SimpleTableHeaderAdapter getProjectTableHeader(Context context, String... a) {

        return new SimpleTableHeaderAdapter(context,TABLE_HEADERS);
    }
}
