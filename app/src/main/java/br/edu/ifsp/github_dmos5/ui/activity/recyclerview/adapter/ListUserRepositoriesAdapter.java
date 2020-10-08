package br.edu.ifsp.github_dmos5.ui.activity.recyclerview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.edu.ifsp.github_dmos5.R;
import br.edu.ifsp.github_dmos5.model.Repository;

public class ListUserRepositoriesAdapter extends RecyclerView.Adapter<ListUserRepositoriesAdapter.RepositoryViewHolder> {

    private final List<Repository> repositories;
    private final Context context;

    public ListUserRepositoriesAdapter(Context context, List<Repository> repositories) {
        this.context = context;
        this.repositories = repositories;
    }

    @Override
    public ListUserRepositoriesAdapter.RepositoryViewHolder onCreateViewHolder(
            ViewGroup parent
            , int viewType
    ) {
        View viewCriada = LayoutInflater.from(context)
                .inflate(R.layout.item_repository, parent, false);
        return new RepositoryViewHolder(viewCriada);
    }

    @Override
    public void onBindViewHolder(ListUserRepositoriesAdapter.RepositoryViewHolder holder, int position) {
        Repository repository = repositories.get(position);
        holder.fill(repository);
    }

    @Override
    public int getItemCount() {
        return repositories.size();
    }

    static class RepositoryViewHolder extends RecyclerView.ViewHolder {

        private final TextView textTitle;
        private final TextView textUrl;
        private final ImageView imageOwnerPicture;

        public RepositoryViewHolder(View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.item_repository_title);
            textUrl = itemView.findViewById(R.id.item_repositoy_url);
            imageOwnerPicture = itemView.findViewById(R.id.item_repository_owner_picture);
        }


        public void fill(Repository repository) {
            textTitle.setText(repository.getName());
            textUrl.setText(repository.getHtml_url());
            Picasso.get().load(repository.getOwner().getAvatar_url()).into(imageOwnerPicture);
        }
    }

    public void change(List<Repository> repositories) {
        this.repositories.clear();
        this.repositories.addAll(repositories);
        notifyDataSetChanged();
    }

    public void add(Repository repository) {
        repositories.add(repository);
        notifyDataSetChanged();
    }

}
