package race.main.com.matchscore;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    ListView list;
    ArrayList<String> teamHome = new ArrayList<String>();
    ArrayList<String> teamAway = new ArrayList<String>();
    ArrayList<String> teamPics = new ArrayList<String>();
    ArrayList<String> listScore = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        list = (ListView) findViewById(R.id.listView);

        Bundle extras = getIntent().getExtras();
        teamHome = extras.getStringArrayList("teamHome");
        teamAway = extras.getStringArrayList("teamAway");
        teamPics = extras.getStringArrayList("teamPics");
        listScore = extras.getStringArrayList("teamScore");

        if (teamPics.size() > 0 && teamHome.size() > 0 && teamAway.size() > 0 && listScore.size()>0) {


            String[] arrTeamHome = new String[teamHome.size()];
            arrTeamHome = teamHome.toArray(arrTeamHome);

            String[] arrTeamAway = new String[teamAway.size()];
            arrTeamAway = teamAway.toArray(arrTeamAway);

            String[] arrTeamPics = new String[teamPics.size()];
            arrTeamPics = teamPics.toArray(arrTeamPics);

            String[] arrScores = new String[listScore.size()];
            arrScores = listScore.toArray(arrScores);


            ListAdapter theAdapter = new myAdapter(this, R.layout.linknews, arrTeamHome, arrTeamAway, arrTeamPics, arrScores);
            list.setAdapter(theAdapter);
        }
        else {
            Toast.makeText(this,"Come Back Later",Toast.LENGTH_LONG).show();
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list, menu);
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
