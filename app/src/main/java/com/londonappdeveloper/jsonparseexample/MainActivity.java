package com.londonappdeveloper.jsonparseexample;

import android.app.DownloadManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.Response;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

public class MainActivity extends AppCompatActivity {

    EditText etGitHubUser;
    Button btnGetRepos;
    TextView tvRepoList;
    RequestQueue requestQueue;

    String baseUrl = "https://api.github.com/users/";
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.etGitHubUser = (EditText) findViewById(R.id.et_github_user);
        this.btnGetRepos = (Button) findViewById(R.id.btn_get_repos);
        this.tvRepoList = (TextView) findViewById(R.id.tv_repo_list);
        this.tvRepoList.setMovementMethod(new ScrollingMovementMethod());

        requestQueue = Volley.newRequestQueue(this);
    }

    private void clearRepoList() {
        this.tvRepoList.setText("");
    }

    private void addToRepoList(String repoName, String lastUpdated) {
        String strRow = repoName + " / " + lastUpdated;
        String currentText = tvRepoList.getText().toString();
        this.tvRepoList.setText(currentText + "\n\n" + strRow);
    }

    private void setRepoListText(String str) {
        this.tvRepoList.setText(str);
    }

    private void getRepoList(String username) {
        this.url = this.baseUrl + username + "/repos";
        JsonArrayRequest arrReq = new JsonArrayRequest(Request.Method.GET, url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response.length() > 0) {
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject jsonObj = response.getJSONObject(i);
                                    String repoName = jsonObj.get("name").toString();
                                    String lastUpdated = jsonObj.get("updated_at").toString();
                                    addToRepoList(repoName, lastUpdated);
                                } catch (JSONException e) {
                                    Log.e("Volley", "Invalid JSON Object.");
                                }

                            }
                        } else {
                            setRepoListText("No repos found.");
                        }

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        setRepoListText("Error while calling REST API");
                        Log.e("Volley", error.toString());
                    }
                }
        );
        requestQueue.add(arrReq);
    }

    public void getReposClicked(View v) {
        clearRepoList();
        getRepoList(etGitHubUser.getText().toString());
    }
}
