// Generated by view binder compiler. Do not edit!
package com.example.projectmanagement.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.projectmanagement.R;
import de.codecrafters.tableview.SortableTableView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityListingProjectBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final Button btnAddProject;

  @NonNull
  public final Guideline guideline2;

  @NonNull
  public final Guideline guideline3;

  @NonNull
  public final Guideline guideline30;

  @NonNull
  public final Guideline guideline31;

  @NonNull
  public final Guideline guideline36;

  @NonNull
  public final Guideline guideline37;

  @NonNull
  public final Guideline guideline4;

  @NonNull
  public final Guideline guideline5;

  @NonNull
  public final Guideline guideline6;

  @NonNull
  public final ImageView imageTeamManager;

  @NonNull
  public final SortableTableView tableView;

  @NonNull
  public final TextView txtNoProjects;

  @NonNull
  public final TextView txtUserName;

  @NonNull
  public final TextView txtUserRole;

  private ActivityListingProjectBinding(@NonNull ConstraintLayout rootView,
      @NonNull Button btnAddProject, @NonNull Guideline guideline2, @NonNull Guideline guideline3,
      @NonNull Guideline guideline30, @NonNull Guideline guideline31,
      @NonNull Guideline guideline36, @NonNull Guideline guideline37, @NonNull Guideline guideline4,
      @NonNull Guideline guideline5, @NonNull Guideline guideline6,
      @NonNull ImageView imageTeamManager, @NonNull SortableTableView tableView,
      @NonNull TextView txtNoProjects, @NonNull TextView txtUserName,
      @NonNull TextView txtUserRole) {
    this.rootView = rootView;
    this.btnAddProject = btnAddProject;
    this.guideline2 = guideline2;
    this.guideline3 = guideline3;
    this.guideline30 = guideline30;
    this.guideline31 = guideline31;
    this.guideline36 = guideline36;
    this.guideline37 = guideline37;
    this.guideline4 = guideline4;
    this.guideline5 = guideline5;
    this.guideline6 = guideline6;
    this.imageTeamManager = imageTeamManager;
    this.tableView = tableView;
    this.txtNoProjects = txtNoProjects;
    this.txtUserName = txtUserName;
    this.txtUserRole = txtUserRole;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityListingProjectBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityListingProjectBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_listing_project, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityListingProjectBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btnAddProject;
      Button btnAddProject = ViewBindings.findChildViewById(rootView, id);
      if (btnAddProject == null) {
        break missingId;
      }

      id = R.id.guideline2;
      Guideline guideline2 = ViewBindings.findChildViewById(rootView, id);
      if (guideline2 == null) {
        break missingId;
      }

      id = R.id.guideline3;
      Guideline guideline3 = ViewBindings.findChildViewById(rootView, id);
      if (guideline3 == null) {
        break missingId;
      }

      id = R.id.guideline30;
      Guideline guideline30 = ViewBindings.findChildViewById(rootView, id);
      if (guideline30 == null) {
        break missingId;
      }

      id = R.id.guideline31;
      Guideline guideline31 = ViewBindings.findChildViewById(rootView, id);
      if (guideline31 == null) {
        break missingId;
      }

      id = R.id.guideline36;
      Guideline guideline36 = ViewBindings.findChildViewById(rootView, id);
      if (guideline36 == null) {
        break missingId;
      }

      id = R.id.guideline37;
      Guideline guideline37 = ViewBindings.findChildViewById(rootView, id);
      if (guideline37 == null) {
        break missingId;
      }

      id = R.id.guideline4;
      Guideline guideline4 = ViewBindings.findChildViewById(rootView, id);
      if (guideline4 == null) {
        break missingId;
      }

      id = R.id.guideline5;
      Guideline guideline5 = ViewBindings.findChildViewById(rootView, id);
      if (guideline5 == null) {
        break missingId;
      }

      id = R.id.guideline6;
      Guideline guideline6 = ViewBindings.findChildViewById(rootView, id);
      if (guideline6 == null) {
        break missingId;
      }

      id = R.id.imageTeamManager;
      ImageView imageTeamManager = ViewBindings.findChildViewById(rootView, id);
      if (imageTeamManager == null) {
        break missingId;
      }

      id = R.id.tableView;
      SortableTableView tableView = ViewBindings.findChildViewById(rootView, id);
      if (tableView == null) {
        break missingId;
      }

      id = R.id.txtNoProjects;
      TextView txtNoProjects = ViewBindings.findChildViewById(rootView, id);
      if (txtNoProjects == null) {
        break missingId;
      }

      id = R.id.txtUserName;
      TextView txtUserName = ViewBindings.findChildViewById(rootView, id);
      if (txtUserName == null) {
        break missingId;
      }

      id = R.id.txtUserRole;
      TextView txtUserRole = ViewBindings.findChildViewById(rootView, id);
      if (txtUserRole == null) {
        break missingId;
      }

      return new ActivityListingProjectBinding((ConstraintLayout) rootView, btnAddProject,
          guideline2, guideline3, guideline30, guideline31, guideline36, guideline37, guideline4,
          guideline5, guideline6, imageTeamManager, tableView, txtNoProjects, txtUserName,
          txtUserRole);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
