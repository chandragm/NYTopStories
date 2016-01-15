package in.chandramouligoru.nytopstories.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.chandramouligoru.nytopstories.R;
import in.chandramouligoru.nytopstories.fragments.TopStoriesFragment;

public class TopStoriesActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Bind(R.id.toolbar)
    protected Toolbar mToolbar;

    @Bind(R.id.drawer_layout)
    protected DrawerLayout mDrawerLayout;

    @Bind(R.id.nav_view)
    protected NavigationView mNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_stories);
        ButterKnife.bind(this);
        initUI();

        if(savedInstanceState == null) {
            TopStoriesFragment fragment = TopStoriesFragment.newInstance("home");
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, fragment);
            transaction.commit();
        }
    }

    private void initUI() {
        setSupportActionBar(mToolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        mNavigationView.setNavigationItemSelectedListener(this);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        if(isNetworkAvailable()) {
            // Handle navigation view item clicks here.
            TopStoriesFragment fragment = TopStoriesFragment.newInstance(item.getTitle().toString());
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, fragment);
            transaction.commit();
        }
         else {
            showNoNetworkError();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
