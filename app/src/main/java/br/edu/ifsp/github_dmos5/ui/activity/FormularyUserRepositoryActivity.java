package br.edu.ifsp.github_dmos5.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import br.edu.ifsp.github_dmos5.R;
import br.edu.ifsp.github_dmos5.api.GitHubService;
import br.edu.ifsp.github_dmos5.model.Repository;
import br.edu.ifsp.github_dmos5.service.EncapsulateRepositories;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static br.edu.ifsp.github_dmos5.ui.activity.UserRepositoryActivityConstants.CODE_REQUEST_REPOSITORIES;
import static br.edu.ifsp.github_dmos5.ui.activity.UserRepositoryActivityConstants.KEY_LIST_REPOSITORY;


public class FormularyUserRepositoryActivity extends AppCompatActivity {

    private EditText editUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulary_user_repository);
        initComponents();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_formulary_user_repository_search, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_user_repository_search) {
            validateUserBeforeFindUserRepositories();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initComponents() {
        editUserName = findViewById(R.id.edit_user_name);
    }

    private void findUserRepositories(String user) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GitHubService.baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GitHubService service = retrofit.create(GitHubService.class);

        Call<List<Repository>> call = service.listUserRepositories(user);


        call.enqueue(new Callback<List<Repository>>() {
            @Override
            public void onResponse(Call<List<Repository>> call, Response<List<Repository>> response) {
                if (response.isSuccessful()) {
                    List<Repository> repositories = response.body();
                    EncapsulateRepositories encapsulateRepositories
                            = new EncapsulateRepositories(repositories);

                    returnListRepositoriesToMainActivity(encapsulateRepositories);
                    finish();
                } else {
                    Toast.makeText(
                            getApplicationContext()
                            , R.string.error_retrieving_data_from_api
                            , Toast.LENGTH_SHORT
                    ).show();
                }
            }

            @Override
            public void onFailure(Call<List<Repository>> call, Throwable t) {
                Toast.makeText(
                        getApplicationContext()
                        , "Timeout"
                        , Toast.LENGTH_SHORT
                ).show();
            }
        });
    }

    private void validateUserBeforeFindUserRepositories() {
        String user = editUserName.getText().toString();
        if (user.isEmpty()) {
            Toast.makeText(
                    getApplicationContext()
                    , R.string.error_empty_user
                    , Toast.LENGTH_SHORT
            ).show();
        } else {
            findUserRepositories(user);
        }
    }

    private void returnListRepositoriesToMainActivity(
            EncapsulateRepositories encapsulateRepositories
    ) {
        Intent intent = new Intent();
        intent.putExtra(KEY_LIST_REPOSITORY, encapsulateRepositories);
        setResult(CODE_REQUEST_REPOSITORIES, intent);
    }
}