package uk.org.socialistparty.spcc.activities;

import android.arch.persistence.room.Room;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.MenuItem;

import java.lang.ref.WeakReference;

import uk.org.socialistparty.spcc.R;
import uk.org.socialistparty.spcc.data.AppDatabase;
import uk.org.socialistparty.spcc.data.Sale;
import uk.org.socialistparty.spcc.fragments.AddSaleFragment;
import uk.org.socialistparty.spcc.fragments.NewsFragment;
import uk.org.socialistparty.spcc.fragments.SaleHistoryFragment;
import uk.org.socialistparty.spcc.fragments.SettingsFragment;

public class HomeActivity extends AppCompatActivity
        implements
        AddSaleFragment.OnSaleConfirmedListener,
        NavigationView.OnNavigationItemSelectedListener,
        SaleHistoryFragment.OnFragmentInteractionListener,
        NewsFragment.OnFragmentInteractionListener,
        SettingsFragment.OnFragmentInteractionListener{

    private FragmentManager fragmentManager;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getSupportFragmentManager();

        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        AppDatabase db = Room.databaseBuilder(
                getApplicationContext(),
                AppDatabase.class,
                "spcc-db")
                .build();
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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        Fragment fragment = null;
        int id = item.getItemId();

        if (id == R.id.nav_news) {
            fragment = new NewsFragment();
        } else if (id == R.id.nav_add_sale) {
            fragment = new AddSaleFragment();
        } else if (id == R.id.nav_sale_history) {
            fragment = new SaleHistoryFragment();
        } else if (id == R.id.nav_settings) {
            fragment = new SettingsFragment();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        fragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();

        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private AppDatabase getDB(){
        if(db == null){
            db = Room.databaseBuilder(
                    getApplicationContext(),
                    AppDatabase.class,
                    "spcc-db")
                    .build();
        }
        return db;
    }

    // Method and task to add sale from a fragment
    public void onSalesConfirmed(Sale... sales){
        new addSaleTask(this, sales).execute();
    }

    private static class addSaleTask extends AsyncTask<Void, Void, Void> {

        private WeakReference<HomeActivity> activityReference;
        private Sale[] sales;

        addSaleTask(HomeActivity context, Sale[] sales) {
            activityReference = new WeakReference<>(context);
            sales = sales;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            activityReference.get().getDB().saleDao().insertAll(sales);
            return null;
        }
    }

}
