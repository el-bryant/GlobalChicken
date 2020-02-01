package pe.globalchicken.globalchicken;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Registro extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    AutoCompleteTextView etDni, etApPat, etApMat, etNom, etCel;
    String dni = "", ap_pat = "", ap_mat = "", nomb = "", cel = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        etDni = (AutoCompleteTextView) findViewById(R.id.etDniClienteRegistro);
        etApPat = (AutoCompleteTextView) findViewById(R.id.etApellidoPaternoRegistro);
        etApMat = (AutoCompleteTextView) findViewById(R.id.etApellidoMaternoRegistro);
        etNom = (AutoCompleteTextView) findViewById(R.id.etNombresRegistro);
        etCel = (AutoCompleteTextView) findViewById(R.id.etCelularRegistro);
        etDni.requestFocus();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer, menu);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_inicio) {
            Intent intent = new Intent(this, Inicio.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_registro) {
            Intent intent = new Intent(this, Registro.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_delivery) {
            Intent intent = new Intent(this, Pedido.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_nosotros) {
            Intent intent = new Intent(this, Nosotros.class);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onRegistrar(View view) {
        dni = etDni.getText().toString();
        Log.i("depurar", "click");
        Thread tr =  new Thread() {
            @Override
            public void run() {
                ap_pat = etApPat.getText().toString();
                ap_mat = etApMat.getText().toString();
                nomb = etNom.getText().toString();
                cel = etCel.getText().toString();
                final String result = enviarDatosGet(dni, ap_pat, ap_mat, nomb, cel);
                Log.i("depurar", result);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int r = obtDatosJson(result);
                        if (r > 0) {
                            MostrarData();
                        }
                    }
                });
            }
        };
        tr.start();
    }

    public String enviarDatosGet(String dni_cliente, String apellido_p, String apellido_m, String nombres, String celu) {
        URL url = null;
        String linea = "";
        int respuesta = 0;
        StringBuilder result = null;
        try {
            url = new URL("http://www.gosmart.pe/websvc/registrar.php?dni_cliente=" + dni_cliente + "&apellido_paterno="
                    + apellido_p + "&apellido_materno=" + apellido_m + "&nombres=" + nombres + "&celular=" + celu);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            respuesta = connection.getResponseCode();
            result = new StringBuilder();
            if (respuesta == HttpURLConnection.HTTP_OK) {
                InputStream in = new BufferedInputStream(connection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                while ((linea = reader.readLine()) != null) {
                    result.append(linea);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    public int obtDatosJson(String response) {
        int res = 0;
        if (response != "") {
            res = 1;
        }
        return res;
    }

    public void MostrarData() {
        Intent intent = new Intent(this, Inicio.class);
        startActivity(intent);
        finish();
    }
}
