package com.example.tallerfinalmovilesandroid;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.os.Bundle;




public class MainActivity extends AppCompatActivity {

    TextView id, descripcion, valor, cantidad;
    Button enviar;
    Spinner spinner1;
    RequestQueue requestQueue;

    private static final String url_enviar = "https://backend-tallerfinal-production.up.railway.app/Post";
    private static final String url_compra = "https://backend-tallerfinal-production.up.railway.app/Compra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestQueue = Volley.newRequestQueue(this);

        spinner1 = findViewById(R.id.spinner);

        id = findViewById(R.id.Id);
        descripcion = findViewById(R.id.Descripcion);
        valor = findViewById(R.id.valor);
        cantidad = findViewById(R.id.cantidad);
        enviar = findViewById(R.id.enviar);



        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Leer();
              //  Enviar("Prueba2", "Prueba2", "1990", "1016024401");
               // EnviarCompra("1","1","3","789456123","12/22","852");
            }
        });
    }

    private void Leer() {

        String url = "https://backend-tallerfinal-production.up.railway.app/Productos";

        StringRequest postRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.i("Valor:", response);
                    JSONArray jsonArray  = new  JSONArray(response);

                    for (int i=0; i<jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String id = jsonObject.getString("Id_Producto");
                        String descripcion = jsonObject.getString("Descripcion");
                        String valor = jsonObject.getString("Valor");
                        String cantidad = jsonObject.getString("Cantidad");

                       String[] arrayRespuesta = {id,descripcion,valor,cantidad};


                        List<String> list = new ArrayList<String>();
                        list.add(""+i+"");

                        ArrayAdapter <String> adapterItems = new ArrayAdapter<String>(MainActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,list);
                        spinner1.setAdapter(adapterItems);
                    }
                    //id.setText(jsonObject.getString("Id_Producto"));
                    //descripcion.setText(jsonObject.getString("Descripcion"));
                    //valor.setText(jsonObject.getString("Valor"));
                    //cantidad.setText(jsonObject.getString("Cantidad"));

                    //String [] opciones = {Descrip,valorProd,Cant};







                } catch (JSONException e) {

                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error:", error.getMessage());
            }
        });
        Volley.newRequestQueue(this).add(postRequest);
    }

    private void Enviar(String Nombre_Cliente, String Apellido_Cliente, String Fecha_Nacimiento, String Identificacion) {

        Map<String, String> params = new HashMap();
        params.put("Nombre_Cliente", Nombre_Cliente);
        params.put("Apellido_Cliente", Apellido_Cliente);
        params.put("Fecha_Nacimiento", Fecha_Nacimiento);
        params.put("Identificacion", Identificacion);

        JSONObject parameters = new JSONObject(params);

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url_enviar, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    descripcion.setText(response.getString("insertId"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.e("Error:", error.getMessage());
            }
        });
        Volley.newRequestQueue(this).add(jsonRequest);
    }
    private void EnviarCompra(String Id_Clien, String Id_Produc, String Cant, String Num_Tarjeta,String Mes_Año, String CVV) {

        Map<String, String> params = new HashMap();
        params.put("Id_Clien", Id_Clien);
        params.put("Id_Produc", Id_Produc);
        params.put("Cant", Cant);
        params.put("Num_Tarjeta", Num_Tarjeta);
        params.put("Mes_Año", Mes_Año);
        params.put("CVV", CVV);

        JSONObject parameters = new JSONObject(params);

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url_compra, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    descripcion.setText(response.getString("IdFactura"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.e("Error:", error.getMessage());
            }
        });
        Volley.newRequestQueue(this).add(jsonRequest);
    }
}


