package ashu.help.letshelp.com.letshelp.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.Timer;
import java.util.TimerTask;

import ashu.help.letshelp.com.letshelp.Adapters.Adapter.ImageSliderAdapter;
import ashu.help.letshelp.com.letshelp.Adapters.Adapter.NewsAdapter.NewsDetailAdapter;
import ashu.help.letshelp.com.letshelp.R;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView recyclerView;
    ViewPager imageSliderViewPager;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        recyclerView=(RecyclerView)findViewById(R.id.recyclerView_news);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        NewsDetailAdapter newsDetailAdapter=new NewsDetailAdapter();
        recyclerView.setAdapter(newsDetailAdapter);

        sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDots);
        imageSliderViewPager = (ViewPager) findViewById(R.id.imageSlider);
        ImageSliderAdapter imageSliderAdapter = new ImageSliderAdapter(this);
        imageSliderViewPager.setAdapter(imageSliderAdapter);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new AutoSlider(), 2000, 4000);

        dotscount = imageSliderAdapter.getCount();
        dots = new ImageView[dotscount];

        for (int i = 0; i < dotscount; i++) {

            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            sliderDotspanel.addView(dots[i], params);

        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

        imageSliderViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for (int i = 0; i < dotscount; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

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
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            final ProgressDialog progressDialog = new ProgressDialog(Home.this,
                    R.style.AppTheme_AppBarOverlay);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Signing you out...");
            progressDialog.show();
            startActivity(new Intent(Home.this,AuthActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_events) {
            startActivity(new Intent(Home.this,EventActivity.class));
        }
        else if (id == R.id.nav_donate) {
            startActivity(new Intent(Home.this,DonateActivity.class));
        }
        else if (id == R.id.nav_donateblood) {
            startActivity(new Intent(Home.this,BloodDonateActivity.class));
        }
        else if (id == R.id.nav_notification) {
            startActivity(new Intent(Home.this,Notification.class));
        }
        else if (id == R.id.nav_profile) {

        startActivity(new Intent(Home.this,Profile.class));
        }
        else if (id == R.id.nav_send) {

            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "An app to help out the people by donating money, clothes and blood";
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "LetsHelp \n");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public class AutoSlider extends TimerTask {
        @Override
        public void run() {
            Home.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (imageSliderViewPager.getCurrentItem() == 0) {
                        imageSliderViewPager.setCurrentItem(1);
                    } else if (imageSliderViewPager.getCurrentItem() == 1) {
                        imageSliderViewPager.setCurrentItem(2);
                    } else {
                        imageSliderViewPager.setCurrentItem(0);
                    }
                }
            });
        }
    }
}
