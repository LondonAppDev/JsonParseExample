package com.londonappdeveloper.jsonparseexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText etGitHubUser;
    Button btnGetRepos;
    TextView tvRepoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.etGitHubUser = (EditText) findViewById(R.id.et_github_user);
        this.btnGetRepos = (Button) findViewById(R.id.btn_get_repos);
        this.tvRepoList = (TextView) findViewById(R.id.tv_repo_list);
    }

    public void getReposClicked(View v) {
        this.tvRepoList.setText("ABACUS");
    }
}
