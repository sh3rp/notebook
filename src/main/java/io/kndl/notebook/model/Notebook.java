package io.kndl.notebook.model;

import java.io.Serializable;
import java.util.Set;

/**
 * Created by skend on 9/3/2016.
 */
public class Notebook implements Serializable {
    public final long id;
    public final String name;
    public final Set<Note> notes;
    public final Set<String> tags;

    public Notebook(long id, String name, Set<Note> notes, Set<String> tags) {
        this.id = id;
        this.name = name;
        this.notes = notes;
        this.tags = tags;
    }

    public Notebook(String name, Set<Note> notes, Set<String> tags) {
        this.id = 0;
        this.name = name;
        this.notes = notes;
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Notebook notebook = (Notebook) o;

        if (id != notebook.id) return false;
        if (name != null ? !name.equals(notebook.name) : notebook.name != null) return false;
        if (notes != null ? !notes.equals(notebook.notes) : notebook.notes != null) return false;
        return tags != null ? tags.equals(notebook.tags) : notebook.tags == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (notes != null ? notes.hashCode() : 0);
        result = 31 * result + (tags != null ? tags.hashCode() : 0);
        return result;
    }
}
