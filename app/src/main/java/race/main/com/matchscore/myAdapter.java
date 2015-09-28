package race.main.com.matchscore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by Karim Omaya on 9/26/2015.
 */
public class myAdapter extends ArrayAdapter<String> {
    String[] teamHome;
    String[] teamAway;
    String[] teamPics;
    String[] teamScore;

    public myAdapter(Context context, int linknews2, String[] teamHome, String[] teamAway, String[] teamPics, String[] teamScore) {
        super(context,R.layout.linknews2, teamHome);
        this.teamHome = teamHome;
        this.teamAway = teamAway;
        this.teamPics = teamPics;
        this.teamScore = teamScore;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        LayoutInflater theInflater = LayoutInflater.from(getContext());

        View theView = theInflater.inflate(R.layout.linknews2, parent, false);

        //Toast.makeText(getContext(),teamPics[position],Toast.LENGTH_LONG).show();

        String strTeamHome = teamHome[position];
        String strTeamAway = teamAway[position];

        String imgUrlHome = teamPics[position + position];
        String imgUrlAway = teamPics[position+ position +1];

        String strTeamScore = teamScore[position];

        ImageLoader imageLoader = ImageLoader.getInstance();
        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisc(true).resetViewBeforeLoading(true)
                .showImageForEmptyUri(R.drawable.picempty)
                .showImageOnFail(R.drawable.picempty)
                .showImageOnLoading(R.drawable.loading).build();


        TextView txtTeamHome = (TextView) theView.findViewById(R.id.homeTeam);
        TextView txtTeamAway = (TextView) theView.findViewById(R.id.awayTeam);
        ImageView teamHomePic = (ImageView) theView.findViewById(R.id.teamHomePic);
        ImageView teamAwayPic = (ImageView) theView.findViewById(R.id.teamAwayPic);
        TextView txtTeamScore = (TextView) theView.findViewById(R.id.score);

        imageLoader.displayImage(imgUrlHome, teamHomePic, options);
        imageLoader.displayImage(imgUrlAway, teamAwayPic, options);




        txtTeamHome.setText(strTeamHome);
        txtTeamAway.setText(strTeamAway);
        txtTeamScore.setText(strTeamScore);

        return theView;
    }
}
