// Generated by view binder compiler. Do not edit!
package com.example.projectmanagement.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.projectmanagement.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityMainBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final Button btnSignIn;

  @NonNull
  public final Button btnSignUp;

  @NonNull
  public final EditText editEmail;

  @NonNull
  public final EditText editPassword;

  @NonNull
  public final Guideline guideline7;

  @NonNull
  public final Guideline guideline8;

  @NonNull
  public final ImageView imgMainScreen;

  @NonNull
  public final TextView txtForgetPassword;

  @NonNull
  public final TextView txtNotRegistered;

  private ActivityMainBinding(@NonNull ConstraintLayout rootView, @NonNull Button btnSignIn,
      @NonNull Button btnSignUp, @NonNull EditText editEmail, @NonNull EditText editPassword,
      @NonNull Guideline guideline7, @NonNull Guideline guideline8,
      @NonNull ImageView imgMainScreen, @NonNull TextView txtForgetPassword,
      @NonNull TextView txtNotRegistered) {
    this.rootView = rootView;
    this.btnSignIn = btnSignIn;
    this.btnSignUp = btnSignUp;
    this.editEmail = editEmail;
    this.editPassword = editPassword;
    this.guideline7 = guideline7;
    this.guideline8 = guideline8;
    this.imgMainScreen = imgMainScreen;
    this.txtForgetPassword = txtForgetPassword;
    this.txtNotRegistered = txtNotRegistered;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityMainBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityMainBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_main, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityMainBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btnSignIn;
      Button btnSignIn = ViewBindings.findChildViewById(rootView, id);
      if (btnSignIn == null) {
        break missingId;
      }

      id = R.id.btnSignUp;
      Button btnSignUp = ViewBindings.findChildViewById(rootView, id);
      if (btnSignUp == null) {
        break missingId;
      }

      id = R.id.editEmail;
      EditText editEmail = ViewBindings.findChildViewById(rootView, id);
      if (editEmail == null) {
        break missingId;
      }

      id = R.id.editPassword;
      EditText editPassword = ViewBindings.findChildViewById(rootView, id);
      if (editPassword == null) {
        break missingId;
      }

      id = R.id.guideline7;
      Guideline guideline7 = ViewBindings.findChildViewById(rootView, id);
      if (guideline7 == null) {
        break missingId;
      }

      id = R.id.guideline8;
      Guideline guideline8 = ViewBindings.findChildViewById(rootView, id);
      if (guideline8 == null) {
        break missingId;
      }

      id = R.id.imgMainScreen;
      ImageView imgMainScreen = ViewBindings.findChildViewById(rootView, id);
      if (imgMainScreen == null) {
        break missingId;
      }

      id = R.id.txtForgetPassword;
      TextView txtForgetPassword = ViewBindings.findChildViewById(rootView, id);
      if (txtForgetPassword == null) {
        break missingId;
      }

      id = R.id.txtNotRegistered;
      TextView txtNotRegistered = ViewBindings.findChildViewById(rootView, id);
      if (txtNotRegistered == null) {
        break missingId;
      }

      return new ActivityMainBinding((ConstraintLayout) rootView, btnSignIn, btnSignUp, editEmail,
          editPassword, guideline7, guideline8, imgMainScreen, txtForgetPassword, txtNotRegistered);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
