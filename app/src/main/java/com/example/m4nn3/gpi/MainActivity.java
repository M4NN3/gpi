package com.example.m4nn3.gpi;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.m4nn3.gpi.model.Usuario;
import com.example.m4nn3.gpi.network.HttpReqTask;
import com.example.m4nn3.gpi.gui.Login_Fragment;
import com.example.m4nn3.gpi.network.MyApiEndpointInterface;
import com.example.m4nn3.gpi.network.RetrofitClientInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.HTTP;

public class MainActivity extends AppCompatActivity {
    private static FragmentManager fragmentManager;
    //private Button btnCallRestApi;
    //private TextView txtError;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.frameContainer, new Login_Fragment(),
                            Utils.Login_Fragment).commit();
        }
        // On close icon click finish activity
        findViewById(R.id.closeLayout).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        //finish();
                        MyApiEndpointInterface myApiEndpointInterface = RetrofitClientInstance.getRetrofitInstance()
                                .create(MyApiEndpointInterface.class);
                        Call<List<Usuario>> call = myApiEndpointInterface.getAllUsuarios();
                        call.enqueue(new Callback<List<Usuario>>() {
                            @Override
                            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                                if (response.isSuccessful()){
                                    //Log.d("body", response.body().get(0).getCreatedAt().toString());
                                    new CustomToast().Show_Toast(MainActivity.this, getWindow().getDecorView(),
                                            "Bienvenido: " + response.body().get(0).getNombreUsuario());
                                }
                            }

                            @Override
                            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                                Toast.makeText(MainActivity.this, "Error al registrar ", Toast.LENGTH_SHORT).show();
                            }
                        });
                        /*HttpReqTask reqTask = new HttpReqTask(MainActivity.this);
                        reqTask.execute();*/
                    }
                });
       /*btnCallRestApi = (Button) findViewById(R.id.btnCheck);
        xtError = (TextView) findViewById(R.id.txtError);
        btnCallRestApi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    new HttpReqTask().execute();
                    //Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
                }
                catch (Exception ex){
                    txtError.setText(ex.getMessage());
                }

            }
        });*/
    }
    // Replace Login Fragment with animation
    public void replaceLoginFragment(boolean isRegistered) {
        try {
            fragmentManager
                    .beginTransaction()
                    .setCustomAnimations(R.anim.left_enter, R.anim.right_out)
                    .replace(R.id.frameContainer, new Login_Fragment(),
                            Utils.Login_Fragment).commit();
            /*if (isRegistered)
                Toast.makeText(this, "Registrado correctamente. Inicie sesión...", Toast.LENGTH_SHORT).show();*/

        }
        catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onBackPressed() {
        // Find the tag of signup and forgot password fragment
        android.support.v4.app.Fragment SignUp_Fragment = fragmentManager
                .findFragmentByTag(Utils.SignUp_Fragment);
        android.support.v4.app.Fragment ForgotPassword_Fragment = fragmentManager
                .findFragmentByTag(Utils.ForgotPassword_Fragment);
        // Check if both are null or not
        // If both are not null then replace login fragment else do backpressed
        // task
        if (SignUp_Fragment != null)
            replaceLoginFragment(false);
        else if (ForgotPassword_Fragment != null)
            replaceLoginFragment(false);
        else
            super.onBackPressed();
    }
}
