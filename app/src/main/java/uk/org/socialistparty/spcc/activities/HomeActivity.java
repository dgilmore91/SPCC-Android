package uk.org.socialistparty.spcc.activities;

import android.arch.persistence.room.Room;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.List;

import uk.org.socialistparty.spcc.R;
import uk.org.socialistparty.spcc.data.AppDatabase;
import uk.org.socialistparty.spcc.data.Sale;
import uk.org.socialistparty.spcc.fragments.AddSaleFragment;
import uk.org.socialistparty.spcc.fragments.NewsFragment;
import uk.org.socialistparty.spcc.fragments.SaleHistoryFragment;
import uk.org.socialistparty.spcc.fragments.SettingsFragment;

public class HomeActivity extends AppCompatActivity
        implements
        NavigationView.OnNavigationItemSelectedListener,
        NewsFragment.OnFragmentInteractionListener,
        SettingsFragment.OnFragmentInteractionListener {

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
    }

    @Override
    protected void onStart() {
        super.onStart();
        moveToFragment(R.id.nav_news);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        int id = item.getItemId();
        moveToFragment(id);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private AppDatabase getDB() {
        if (db == null) {
            db = Room.databaseBuilder(
                    getApplicationContext(),
                    AppDatabase.class,
                    "spcc-db")
                    .build();
        }
        return db;
    }
    
    public void moveToFragment(int fragmentId) {
        Fragment fragment = null;

        switch (fragmentId) {
            case R.id.nav_news:
                fragment = new NewsFragment();
                break;
            case R.id.nav_add_sale:
                fragment = new AddSaleFragment();
                break;
            case R.id.nav_sale_history:
                fragment = new SaleHistoryFragment();
                break;
            case R.id.nav_settings:
                fragment = new SettingsFragment();
                break;
        }

        if (fragment != null) {
            clearBackStack();
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
        }
    }

    public void clearBackStack() {
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public void sendMessageToUser(String message) {
        Snackbar.make(findViewById(R.id.home_coordinator), message,
                Snackbar.LENGTH_LONG)
                .show();
    }

    public List<Sale> getSales() {
        return getDB().saleDao().getAllOrderedByDateDesc();
    }

    public List<Sale> getSaleForID(int saleId) {
        return getDB().saleDao().loadAllByIds(new int[]{saleId});
    }

    public Void addSale(Sale sale) {
        getDB().saleDao().insertAll(sale);
        sendMessageToUser("Paper sale saved!");
        moveToFragment(R.id.nav_sale_history);
        return null;
    }

    public void openSale(Sale sale){
        AddSaleFragment fragment = new AddSaleFragment();

        Bundle args = new Bundle();
        args.putInt(AddSaleFragment.INCOMING_SALE_ID, sale.getSale_id());
        fragment.setArguments(args);

        fragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }
}
