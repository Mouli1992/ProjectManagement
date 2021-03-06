package com.example.projectmanagement.utils;

import android.content.Context;

import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

public class TableHeader {

    private static final String[] TABLE_HEADERS = { "Project Name", "Status", "Deadline" };
    private static final String[] TASK_TABLE_HEADERS = { "Task Name", "Status", "Assigned To" };
    private static final String[] TASK_TEAM_TABLE_HEADERS = { "Team Members" };
    public static SimpleTableHeaderAdapter getProjectTableHeader(Context context, String... a) {

        return new SimpleTableHeaderAdapter(context,TABLE_HEADERS);
    }
    public static SimpleTableHeaderAdapter getTaskTableHeader(Context context, String... a) {

        return new SimpleTableHeaderAdapter(context,TASK_TABLE_HEADERS);
    }

    public static SimpleTableHeaderAdapter getTeamLstTableHeader(Context context, String... a) {

        return new SimpleTableHeaderAdapter(context,TASK_TEAM_TABLE_HEADERS);
    }
}
