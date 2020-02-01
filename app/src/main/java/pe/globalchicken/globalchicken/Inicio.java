package pe.globalchicken.globalchicken;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class Inicio extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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

    public void onRegistro(View view) {
        Intent intent = new Intent(this, Registro.class);
        startActivity(intent);
    }

    public void onPedido(View view) {
        Intent intent = new Intent(this, Pedido.class);
        startActivity(intent);
    }

    @SuppressLint("MissingPermission")
    public void onLlamar(View view) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:+51936794594"));
        startActivity(intent);
    }

    public void onFacebook(View view) {
        Uri uri = Uri.parse("https://m.facebook.com/ChaskyChicken/");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public void onNosotros(View view) {
        Intent intent = new Intent(this, Nosotros.class);
        startActivity(intent);
    }
}
