package sg.edu.rp.c346.apiseedsread;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    EditText name, song;
    Button lyricBtn;
    TextView lyric;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = findViewById(R.id.name);
        song = findViewById(R.id.name);
        lyric = findViewById(R.id.lyric);
        lyricBtn = findViewById(R.id.button);

        lyricBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameS = name.getText().toString();
                String songS = song.getText().toString();
                nameS.replaceAll(" ","%20");
                songS.replaceAll(" ","%20");
                HttpRequest request = new HttpRequest
                        ("https://orion.apiseeds.com/api/music/lyric/"+nameS+"/"+songS+"?apikey=uB53ibV3mdCwYYiucFl3Riusw3573X3EqxJMeJnKYkC9lQLPfcQdQMEbuOtfXgv0");
//                HttpRequest request = new HttpRequest
//                        ("https://orion.apiseeds.com/api/music/lyric/Post%20Malone/Better%20Now?apikey=uB53ibV3mdCwYYiucFl3Riusw3573X3EqxJMeJnKYkC9lQLPfcQdQMEbuOtfXgv0");

                request.setOnHttpResponseListener(mHttpResponseListener);
                request.setMethod("GET");
                request.execute();
            }
        });
    }
    private HttpRequest.OnHttpResponseListener mHttpResponseListener =
            new HttpRequest.OnHttpResponseListener() {
                @Override
                public void onResponse(String response){

                    // process response here
                    try {
                        Log.d("Lyric", "onResponse: "+response.toString());
                        JSONObject obj = new JSONObject(response);
                        JSONObject result = obj.getJSONObject("result");
                        JSONObject trackObj = result.getJSONObject("track");

                        String track = trackObj.getString("text");

                        lyric.setText(track);

                    }
                    catch(Exception e){
                        e.printStackTrace();
                        Log.d("Lyric", "onResponse: "+e.toString());
                    }
                }
            };

}
