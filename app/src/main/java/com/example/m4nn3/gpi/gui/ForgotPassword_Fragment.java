package com.example.m4nn3.gpi.gui;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.m4nn3.gpi.CustomToast;
import com.example.m4nn3.gpi.MainActivity;
import com.example.m4nn3.gpi.R;
import com.example.m4nn3.gpi.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForgotPassword_Fragment extends Fragment implements View.OnClickListener{
    private View view;
    private LinearLayout forgotPwdLayout;
    private EditText emailId;
    private TextView submit, back;
    private Animation shakeAnimation;
    public ForgotPassword_Fragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.forgotpassword_layout, container,
                false);
        initViews();
        setListeners();
        return view;
    }

    // Initialize the views
    private void initViews() {
        emailId = (EditText) view.findViewById(R.id.registered_emailid);
        submit = (TextView) view.findViewById(R.id.forgot_button);
        back = (TextView) view.findViewById(R.id.backToLoginBtn);
        forgotPwdLayout = (LinearLayout) view.findViewById(R.id.forgotPwdLayout);
        shakeAnimation = AnimationUtils.loadAnimation(getActivity(),
                R.anim.shake);
        // Setting text selector over textviews
        XmlResourceParser xrp = getResources().getXml(R.drawable.text_selector);
        try {
            ColorStateList csl = ColorStateList.createFromXml(getResources(),
                    xrp);
            back.setTextColor(csl);
            submit.setTextColor(csl);
        } catch (Exception e) {
        }

    }

    // Set Listeners over buttons
    private void setListeners() {
        back.setOnClickListener(this);
        submit.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backToLoginBtn:
                // Replace Login Fragment on Back Presses
                new MainActivity().replaceLoginFragment(false);
                break;
            case R.id.forgot_button:
                // Call Submit button task
                submitButtonTask();
                break;

        }
    }
    private void submitButtonTask() {
        String getEmailId = emailId.getText().toString();
        // Pattern for email id validation
        Pattern p = Pattern.compile(Utils.regEx);
        // Match the pattern
        Matcher m = p.matcher(getEmailId);
        // First check if email id is not null else show error toast
        if (getEmailId.equals("") || getEmailId.length() == 0) {
            forgotPwdLayout.startAnimation(shakeAnimation);
            new CustomToast().Show_Toast(getActivity(), view,
                    "Por favor, ingrese su correo.");
        }
            // Check if email id is valid or not
        else if (!m.find()){
            forgotPwdLayout.startAnimation(shakeAnimation);
            new CustomToast().Show_Toast(getActivity(), view,
                    "Ingrese un correo válido.");
        }
            // Else submit email id and fetch passwod or do your stuff
        else
            Toast.makeText(getActivity(), "Get Forgot Password.",
                    Toast.LENGTH_SHORT).show();
    }
}
