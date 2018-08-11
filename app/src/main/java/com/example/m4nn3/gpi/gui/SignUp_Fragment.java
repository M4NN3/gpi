package com.example.m4nn3.gpi.gui;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.m4nn3.gpi.CustomToast;
import com.example.m4nn3.gpi.MainActivity;
import com.example.m4nn3.gpi.R;
import com.example.m4nn3.gpi.Utils;
import com.example.m4nn3.gpi.model.Cuidador;
import com.example.m4nn3.gpi.model.Mensaje;
import com.example.m4nn3.gpi.model.Usuario;
import com.example.m4nn3.gpi.network.MyApiEndpointInterface;
import com.example.m4nn3.gpi.network.RetrofitClientInstance;

import org.json.JSONObject;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp_Fragment extends Fragment implements View.OnClickListener{
    private View view;
    private EditText fullName, emailId, mobileNumber, location,
            password, confirmPassword;
    private TextView login;
    private Button signUpButton;
    private CheckBox terms_conditions;
    private ProgressDialog progressDialog;
    public SignUp_Fragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.signup_layout, container, false);
        initViews();
        setListeners();
        return view;
    }

    // Initialize all views
    private void initViews() {
        fullName = (EditText) view.findViewById(R.id.fullName);
        emailId = (EditText) view.findViewById(R.id.userEmailId);
        mobileNumber = (EditText) view.findViewById(R.id.mobileNumber);
        location = (EditText) view.findViewById(R.id.location);
        password = (EditText) view.findViewById(R.id.password);
        confirmPassword = (EditText) view.findViewById(R.id.confirmPassword);
        signUpButton = (Button) view.findViewById(R.id.signUpBtn);
        login = (TextView) view.findViewById(R.id.already_user);
        terms_conditions = (CheckBox) view.findViewById(R.id.terms_conditions);
        progressDialog = new ProgressDialog(getActivity(),
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Registrando usuario...");
        // Setting text selector over textviews
        @SuppressLint("ResourceType") XmlResourceParser xrp = getResources().getXml(R.drawable.text_selector);
        try {
            ColorStateList csl = ColorStateList.createFromXml(getResources(),
                    xrp);

            login.setTextColor(csl);
            terms_conditions.setTextColor(csl);
        } catch (Exception e) {
        }
    }

    // Set Listeners
    private void setListeners() {
        signUpButton.setOnClickListener(this);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signUpBtn:
                // Call checkValidation method
                checkValidation();
                break;

            case R.id.already_user:
                // Replace login fragment
                new MainActivity().replaceLoginFragment(false);
                break;
        }
    }
    // Check Validation Method
    private void checkValidation() {
        // Get all edittext texts
        String getFullName = fullName.getText().toString();
        String getEmailId = emailId.getText().toString();
        String getMobileNumber = mobileNumber.getText().toString();
        String getLocation = location.getText().toString();
        String getPassword = password.getText().toString();
        String getConfirmPassword = confirmPassword.getText().toString();
        // Pattern match for email id
        Pattern p = Pattern.compile(Utils.regEx);
        Matcher m = p.matcher(getEmailId);
        // Check if all strings are null or not
        if (getFullName.equals("") || getFullName.length() == 0
                || getEmailId.equals("") || getEmailId.length() == 0
                || getMobileNumber.equals("") || getMobileNumber.length() == 0
                || getLocation.equals("") || getLocation.length() == 0
                || getPassword.equals("") || getPassword.length() == 0
                || getConfirmPassword.equals("")
                || getConfirmPassword.length() == 0)

            new CustomToast().Show_Toast(getActivity(), view,
                    "Todos los campos son requridos.");

            // Check if email id valid or not
        else if (!m.find())
            new CustomToast().Show_Toast(getActivity(), view,
                    "Ingrese una dirección de correo válida");

            // Check if both password should be equal
        else if (!getConfirmPassword.equals(getPassword))
            new CustomToast().Show_Toast(getActivity(), view,
                    "Las contraseñas no coinciden");

            // Make sure user should check Terms and Conditions checkbox
        else if (!terms_conditions.isChecked())
            new CustomToast().Show_Toast(getActivity(), view,
                    "Acepete los Términos y condociones");
         // Else do signup or do your stuff
        else{
            progressDialog.show();
            //Primero valdida q el email no este en la BD
            Usuario u = new Usuario();
            u.setNombreUsuario("excalibur");
            u.setContrasenia("ppp");
            Cuidador cuidador = new Cuidador();
            cuidador.setNombre(getFullName);
            cuidador.setFamilia("Rodriguez");
            cuidador.setCorreo(getEmailId);
            cuidador.setFamiliatelef("123");
            cuidador.setRfid("777");
            cuidador.setTelefono("456");
            u.setCuidador(cuidador);
            MyApiEndpointInterface myApiEndpointInterface = RetrofitClientInstance.getRetrofitInstance()
                    .create(MyApiEndpointInterface.class);
            Call<Mensaje> call = myApiEndpointInterface.addUsuario(u);
            call.enqueue(new Callback<Mensaje>() {
                @Override
                public void onResponse(Call<Mensaje> call, Response<Mensaje> response) {
                    Mensaje msj = new Mensaje();
                    progressDialog.dismiss();
                    if (response.isSuccessful()){
                        msj = response.body();
                        if (msj.getEstado().equals("1")){
                            //new MainActivity().replaceLoginFragment(true);
                            Log.d("Reg", msj.getMensaje());
                            Toast.makeText(getActivity(), "Registro exitoso. Inicie sesión...", Toast.LENGTH_SHORT).show();
                        }
                        else
                            Log.d("NoReg", msj.getMensaje());
                            //Toast.makeText(getActivity(), msj.getMensaje(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Mensaje> call, Throwable t) {
                    progressDialog.dismiss();
                    new CustomToast().Show_Toast(getActivity(), view, "Error al registrar" + t.getMessage());
                }
            });
        }
    }
}

