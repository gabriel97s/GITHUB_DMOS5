package br.edu.ifsp.github_dmos5.model;

import java.io.Serializable;

public class Repository implements Serializable {
    private String name;
    private Owner owner;
    private String html_url;
    private String description;

    public Repository(String name, Owner owner, String html_url, String description) {
        this.name = name;
        this.owner = owner;
        this.html_url = html_url;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
