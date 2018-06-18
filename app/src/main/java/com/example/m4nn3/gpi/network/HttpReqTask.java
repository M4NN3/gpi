package com.example.m4nn3.gpi.network;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.m4nn3.gpi.R;
import com.example.m4nn3.gpi.model.Usuario;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class HttpReqTask extends AsyncTask<Void, Void, Usuario[]> {
    ProgressDialog progressDialog;
    private Context actividad;
    @Override
    protected Usuario[] doInBackground(Void... voids) {
        try {
            String urlAPI = "http://192.168.1.11:8080/api/usuarios";
            RestTemplate restTemplate = new RestTemplate();
            //obtenr el JSON como tal
                /*ResponseEntity<String> response
                        = restTemplate.getForEntity(fooResourceUrl + "/1", String.class);
                ObjectMapper mapper = new ObjectMapper();
                JsonNode root = mapper.readTree(response.getBody());
                JsonNode name = root.path("name");*/
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            // aqui se obtiene un POJO directamente en lugar de un JSON
            Usuario[] usuarios = restTemplate.getForObject(urlAPI, Usuario[].class);
            List<Usuario> u = Arrays.asList(restTemplate.getForObject(urlAPI, Usuario[].class));
            return usuarios;
        }
        catch (Exception ex){
            //Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            //txtError.setText(ex.getMessage());
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(actividad,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(actividad.getResources().getString(R.string.pbTitle));
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(Usuario[] usuarios) {
        super.onPostExecute(usuarios);
        try {
            if (usuarios!=null) {
                for (Usuario u : usuarios) {
                    Log.i("Usuario", "###########");
                    Log.i("Nombre", u.getNombreUsuario());
                    Log.i("Contraseña", u.getContrasenia());
                }
            }
            progressDialog.dismiss();
        }
        catch (Exception ex){
            //Toast.makeText(MainActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            //txtError.setText(ex.getMessage());
        }
    }

    public HttpReqTask(Context actividad) {
        this.actividad = actividad;
    }

    public Context getActividad() {
        return actividad;
    }

    public void setActividad(Context actividad) {
        this.actividad = actividad;
    }
}