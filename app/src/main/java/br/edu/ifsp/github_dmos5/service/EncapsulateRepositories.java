package br.edu.ifsp.github_dmos5.service;

import java.io.Serializable;
import java.util.List;

import br.edu.ifsp.github_dmos5.model.Repository;

public class EncapsulateRepositories implements Serializable {
    private List<Repository> repositories;

    public EncapsulateRepositories(List<Repository> repositories) {
        this.repositories = repositories;
    }

    public List<Repository> getRepositories() {
        return repositories;
    }
}
