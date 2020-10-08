package br.edu.ifsp.github_dmos5.ui.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.edu.ifsp.github_dmos5.R;
import br.edu.ifsp.github_dmos5.model.Repository;
import br.edu.ifsp.github_dmos5.service.EncapsulateRepositories;
import br.edu.ifsp.github_dmos5.ui.activity.recyclerview.adapter.ListUserRepositoriesAdapter;

import static br.edu.ifsp.github_dmos5.ui.activity.UserRepositoryActivityConstants.CODE_REQUEST_REPOSITORIES;
import static br.edu.ifsp.github_dmos5.ui.activity.UserRepositoryActivityConstants.KEY_LIST_REPOSITORY;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_PERMISSION = 64;

    private TextView textFindUser;
    private RecyclerView recyclerRepositories;
    private ListUserRepositoriesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
    }

    private void initComponents() {
        textFindUser = findViewById(R.id.text_find_user);
        recyclerRepositories = findViewById(R.id.recyclerview_repository_list);

        textFindUser.setOnClickListener(this::changeFormularyUserView);
    }

    private void changeFormularyUserView(View view) {
        if (checkPermission()) {
            Intent intent =
                    new Intent(
                            MainActivity.this
                            , FormularyUserRepositoryActivity.class
                    );
            startActivityForResult(intent, CODE_REQUEST_REPOSITORIES);
        } else {
            requestPermission();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == CODE_REQUEST_REPOSITORIES) {
            if (data != null) {
                EncapsulateRepositories encapsulateRepositories
                        = (EncapsulateRepositories) data.getSerializableExtra(KEY_LIST_REPOSITORY);
                refreshAdapter(encapsulateRepositories.getRepositories());
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setAdapter(List<Repository> repositories) {
        adapter = new ListUserRepositoriesAdapter(getApplicationContext(), repositories);
        recyclerRepositories.setAdapter(adapter);
    }

    private void refreshAdapter(List<Repository> repositories) {
        if (adapter == null) {
            setAdapter(repositories);
        } else {
            adapter.change(repositories);
        }
    }

    private boolean checkPermission() {
        return ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.INTERNET)
                == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                MainActivity.this
                , Manifest.permission.INTERNET
        )) {
            new AlertDialog.Builder(getApplicationContext())
                    .setMessage(R.string.msg_info_permission_internet)
                    .setPositiveButton(
                            R.string.grant_permission
                            , new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    ActivityCompat.requestPermissions(
                                            MainActivity.this,
                                            new String[]{
                                                    Manifest.permission.INTERNET
                                            },
                                            REQUEST_PERMISSION
                                    );
                                }
                            })
                    .setNegativeButton(
                            R.string.no_grant_permission
                            , new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            })
                    .show();
        }
    }
}