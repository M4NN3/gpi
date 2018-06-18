package com.example.m4nn3.gpi.network;

import com.example.m4nn3.gpi.model.Mensaje;
import com.example.m4nn3.gpi.model.Usuario;

import org.json.JSONObject;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MyApiEndpointInterface {
    /*@GET("api/{username}")
    Call<Usuario> getUser(@Path("username") String username);*/
    /*@GET("group/{id}/users")
    Call<List<User>> groupList(@Path("id") int groupId, @Query("sort") String sort);*/
    @POST("api/usuarios")
    Call<Mensaje> addUsuario(@Body Usuario user);
    @POST("api/usuarios/user")
    Call<Mensaje> doLogin(@Body Usuario user);
    @GET("api/usuarios")
    Call<List<Usuario>> getAllUsuarios();
    /*@GET("api/usuarios/user/{email}")
    Call<String> getUserByEmail(@Path("email") String email);*/
}
