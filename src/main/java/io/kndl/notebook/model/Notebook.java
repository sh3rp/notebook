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
}
