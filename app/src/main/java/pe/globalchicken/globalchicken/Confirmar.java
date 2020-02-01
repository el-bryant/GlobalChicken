package pe.globalchicken.globalchicken;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Confirmar extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TextView nombre_producto, id_producto;
    String dni = "", apellido_paterno = "", apellido_materno = "", nombres = "", celular = "";
    AutoCompleteTextView etDni, etApellidoPaterno, etApellidoMaterno, etNombres, etCelular, tvUbicacion;
    EditText etCantidad;
    double lat = 0.0;
    double lng = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        etDni = (AutoCompleteTextView) findViewById(R.id.tvDniClienteConfirmar);
        etApellidoPaterno = (AutoCompleteTextView) findViewById(R.id.tvApellidoPaternoClienteConfirmar);
        etApellidoMaterno = (AutoCompleteTextView) findViewById(R.id.tvApellidoMaternoClienteConfirmar);
        etNombres = (AutoCompleteTextView) findViewById(R.id.tvNombresClienteConfirmar);
        etCelular = (AutoCompleteTextView) findViewById(R.id.tvCelularClienteConfirmar);
        etCantidad = (EditText) findViewById(R.id.etCantidadConfirmar);

        nombre_producto = findViewById(R.id.tvProductoConfirmar);
        id_producto = findViewById(R.id.tvIdProductoConfirmar);
        String recup_id_producto = getIntent().getStringExtra("id_producto");
        String recup_producto = getIntent().getStringExtra("producto");
        String recup_precio = getIntent().getStringExtra("precio");
        nombre_producto.setText(recup_producto);
        id_producto.setText(recup_id_producto);

        tvUbicacion = (AutoCompleteTextView) findViewById(R.id.tvUbicacionClienteConirmar);
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

    public void onCargarDatos(View view) {
        dni = etDni.getText().toString();
        Log.i("depurar", "click");
        Thread tr =  new Thread() {
            @Override
            public void run() {
                final String result = enviarDatosConsultar(dni);
                Log.i("depurar", result);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int r = obtDatosJson(result);
                        if (r > 0) {
                            MostrarData(result);
                        }
                    }
                });
            }
        };
        tr.start();
    }

    public String enviarDatosConsultar(String dni) {
        URL url = null;
        String linea = "";
        int respuesta = 0;
        StringBuilder result = null;
        try {
            url = new URL("http://www.gosmart.pe/websvc/consultar.php?dni_cliente=" + dni);
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
        try {
            JSONArray json = new JSONArray(response);
            if (json.length() > 0) {
                res = 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public void MostrarData(String response) {
        try {
            JSONArray json = new JSONArray(response);
            for (int i = 0; i < json.length(); i++) {
                etApellidoPaterno.setText(json.getJSONObject(i).getString("apellido_paterno"));
                etApellidoMaterno.setText(json.getJSONObject(i).getString("apellido_materno"));
                etNombres.setText(json.getJSONObject(i).getString("nombres"));
                etCelular.setText(json.getJSONObject(i).getString("celular"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onMaps(View view) {
        Intent intent = new Intent(this, Ubicacion.class);
        startActivity(intent);
        miUbicacion();
    }

    private void actualizarUbicacion(Location location) {
        if (location != null) {
            lat = location.getLatitude();
            lng = location.getLongitude();
            tvUbicacion.setText(lat + ";" + lng);
        }
    }

    private void miUbicacion() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        actualizarUbicacion(location);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 15000, 0, locListener);
    }



    LocationListener locListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            actualizarUbicacion(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    public void Confirmar(View view) {
        dni = etDni.getText().toString();
        final String id = id_producto.getText().toString();
        final String ubicacion = tvUbicacion.getText().toString();
        final String cantidad = etCantidad.getText().toString();
        Log.i("depurar", "click");
        Thread tr =  new Thread() {
            @Override
            public void run() {
                final String result = enviarDatosConfirmar(dni, id, ubicacion, cantidad);
                Log.i("depurar", result);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int r = obtDatosJsonConfirmar(result);
                        if (r > 0) {
                            MostrarDataConfirmar(result);
                        }
                    }
                });
            }
        };
        tr.start();
    }

    public String enviarDatosConfirmar(String dni, String producto, String ubicacion, String cantidad) {
        URL url = null;
        String linea = "";
        int respuesta = 0;
        StringBuilder result = null;
        try {
            url = new URL("http://www.gosmart.pe/websvc/pedido.php?dni_cliente=" + dni + "&id_producto=" + producto +
                    "&lugar=" + ubicacion + "&cantidad=" + cantidad);
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

    public int obtDatosJsonConfirmar(String response) {
        int res = 0;
        try {
            JSONArray json = new JSONArray(response);
            if (json.length() > 0) {
                res = 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public void MostrarDataConfirmar(String response) {
        Intent intent = new Intent(this, Inicio.class);
        startActivity(intent);
        finish();
    }
}
