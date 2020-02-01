package pe.globalchicken.globalchicken;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

public class Pedido extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView mTextMessage;
    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;
    private Pollo pollo;
    private Hamburguesa hamburguesa;
    private Chifa chifa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mMainFrame = (FrameLayout) findViewById(R.id.container);
        mMainNav = (BottomNavigationView) findViewById(R.id.navigation);
        pollo = new Pollo();
        hamburguesa = new Hamburguesa();
        chifa = new Chifa();

        setFragment(pollo);

        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.navigation_home:
                        setFragment(pollo);
                        getSupportActionBar().setTitle("Chasky Chicken");
                        return true;
                    case R.id.navigation_dashboard:
                        setFragment(hamburguesa);
                        getSupportActionBar().setTitle("Chasky Chicken");
                        return true;
                    case R.id.navigation_notifications:
                        setFragment(chifa);
                        getSupportActionBar().setTitle("Chasky Chicken");
                        return true;
                    default:
                        return false;
                }
            }
        });
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

    public void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }

    public void onPedirPollo1(View view) {
        Intent intent = new Intent(this, Confirmar.class);
        intent.putExtra("id_producto", "1");
        intent.putExtra("producto", "Pollo a la brasa entero");
        intent.putExtra("precio", "22");
        startActivity(intent);
    }

    public void onPedirPollo2(View view) {
        Intent intent = new Intent(this, Confirmar.class);
        intent.putExtra("id_producto", "2");
        intent.putExtra("producto", "1/2 Pollo a la brasa");
        intent.putExtra("precio", "17");
        startActivity(intent);
    }

    public void onPedirPollo3(View view) {
        Intent intent = new Intent(this, Confirmar.class);
        intent.putExtra("id_producto", "3");
        intent.putExtra("producto", "1/4 Pollo a la brasa");
        intent.putExtra("precio", "11");
        startActivity(intent);
    }

    public void onPedirChaufa(View view) {
        Intent intent = new Intent(this, Confirmar.class);
        intent.putExtra("id_producto", "4");
        intent.putExtra("producto", "Porción de arroz chaufa");
        intent.putExtra("precio", "10");
        startActivity(intent);
    }

    public void onPedirHamburguesa1(View view) {
        Intent intent = new Intent(this, Confirmar.class);
        intent.putExtra("id_producto", "5");
        intent.putExtra("producto", "Hamurguesa de pollo clásica");
        intent.putExtra("precio", "5");
        startActivity(intent);
    }

    public void onPedirHamburguesa2(View view) {
        Intent intent = new Intent(this, Confirmar.class);
        intent.putExtra("id_producto", "6");
        intent.putExtra("producto", "Hamburguesa de carne clásica");
        intent.putExtra("precio", "5");
        startActivity(intent);
    }
}
