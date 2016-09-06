package br.com.projeto.gerenciadorfluxo;

import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.firebase.client.Firebase;

public class ActHomeScreen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        FragmentLeitura.OnFragmentInteractionListener
        {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_home_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

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
        getMenuInflater().inflate(R.menu.act_home_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_scanner_ra) {

            FragmentLeitura fragmentLeitura = new FragmentLeitura();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().
                    replace(R.id.relative_layout_for_fragment, fragmentLeitura
                    ).commit();
        } else if (id == R.id.nav_autorizar_entrada) {
            Toast.makeText(this, "Botão Autorizar Entrada Pressionado", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_historico_aluno) {
            Toast.makeText(this, "Botão Histórico Pressionado", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ActHistorico.class); startActivity(intent);
        } else if (id == R.id.nav_manage) {
            Toast.makeText(this, "Botão Tools Pressionado", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_configuracoes) {
            Toast.makeText(this, "Botão Configurações Pressionado", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_sobre) {
            Toast.makeText(this, "Botão Sobre Pressionado", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_sair) {
            Toast.makeText(this, "Botão Sair Pressionado", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

                @Override

                public void onFragmentInteraction(Uri uri) {
                }
}
