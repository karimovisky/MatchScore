package race.main.com.matchscore;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    String url = "http://www.fourfourtwo.com/statszone#:M8HZYBnHwYujBA";

    ArrayList<String> listTeamHome = new ArrayList<String>();
    ArrayList<String> listTeamAway = new ArrayList<String>();
    ArrayList<String> listTeamPics = new ArrayList<String>();
    ArrayList<String> listScore = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // UNIVERSAL IMAGE LOADER SETUP
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisc(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .discCacheSize(100 * 1024 * 1024).build();

        ImageLoader.getInstance().init(config);
        // END - UNIVERSAL IMAGE LOADER SETUP

        final ProgressBar spinner;
        spinner = (ProgressBar)findViewById(R.id.progressBar1);

        spinner.setVisibility(View.VISIBLE);

        ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conMgr.getActiveNetworkInfo() == null
                && !conMgr.getActiveNetworkInfo().isConnected()
                && !conMgr.getActiveNetworkInfo().isAvailable()){
            // No connectivity - Show alert
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(
                    "Unable to reach server, \nPlease check your connectivity.")
                    .setTitle("TD RSS Reader")
                    .setCancelable(false)
                    .setPositiveButton("Exit",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    finish();
                                }
                            });

            AlertDialog alert = builder.create();
            alert.show();
        }
        else {

            try {
                listTeamHome = new TeamHome().execute().get();
                listTeamAway = new TeamAway().execute().get();
                listTeamPics = new TeamPics().execute().get();
                listScore = new Scores().execute().get();

                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                Bundle extras = new Bundle();
                extras.putStringArrayList("teamHome", listTeamHome);
                extras.putStringArrayList("teamAway", listTeamAway);
                extras.putStringArrayList("teamPics", listTeamPics);
                extras.putStringArrayList("teamScore", listScore);
                intent.putExtras(extras);
                startActivity(intent);

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        }
    }

    private class Scores extends AsyncTask<Void, ArrayList<String>, ArrayList<String>> {

        @Override
        protected  ArrayList<String> doInBackground(Void... params) {
            // Connect to the web site
            try {
                Document document = Jsoup.connect(url).get();

                Elements scores = document.select("table[class=match-table] td[class=score]");


                for (org.jsoup.nodes.Element score : scores) {
                    //Toast.makeText(MainActivity.this, div.attr("src"), Toast.LENGTH_LONG).show();
                    listScore.add(score.text());
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }

            return listScore;
        }

        @Override
        protected void onPostExecute( ArrayList<String> s) {
            super.onPostExecute(s);
        }
    }


    private class TeamPics extends AsyncTask<Void, ArrayList<String>, ArrayList<String>> {

        @Override
        protected  ArrayList<String> doInBackground(Void... params) {
            // Connect to the web site
            try {
                Document documenturl = Jsoup.connect(url).get();

                Elements teamPics = documenturl.select("table[class=match-table] td[class=jersey] img[src");


                for (org.jsoup.nodes.Element pic : teamPics) {
                    //Toast.makeText(MainActivity.this, div.attr("src"), Toast.LENGTH_LONG).show();
                    listTeamPics.add(pic.absUrl("src"));
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }

            return listTeamPics;
        }

        @Override
        protected void onPostExecute( ArrayList<String> s) {
            super.onPostExecute(s);
        }
    }


    private class TeamAway extends AsyncTask<Void, ArrayList<String>,ArrayList<String>>{
        @Override
        protected ArrayList<String> doInBackground(Void... params) {
            try {
                Document document = Jsoup.connect(url).get();

                Elements teamAway = document.select("table[class=match-table] td[class=away-team]");

                for (org.jsoup.nodes.Element div : teamAway) {
                   // Toast.makeText(MainActivity.this, div.text(), Toast.LENGTH_LONG).show();
                    listTeamAway.add(div.text());
                }

            }
            catch (Exception e){
                e.printStackTrace();
            }
            return listTeamAway;
        }
    }

    private class TeamHome extends AsyncTask<Void, ArrayList<String>, ArrayList<String>> {

        @Override
        protected  ArrayList<String> doInBackground(Void... params) {
            // Connect to the web site
            try {
                Document document = Jsoup.connect(url).get();

                Elements teamHome = document.select("table[class=match-table] td[class=home-team]");


                for (org.jsoup.nodes.Element div : teamHome) {
                    //Toast.makeText(MainActivity.this, div.text(), Toast.LENGTH_LONG).show();
                    listTeamHome.add(div.text());
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }

            return listTeamHome;
        }

        @Override
        protected void onPostExecute( ArrayList<String> s) {
            super.onPostExecute(s);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
