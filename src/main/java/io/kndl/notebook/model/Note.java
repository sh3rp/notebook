package io.kndl.notebook.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * Created by skend on 9/3/2016.
 */
public class Note implements Serializable {
    public final long id;
    public final long notebookId;
    public final String title;
    public final Set<NoteData> data;
    public final Set<String> tags;
    public final Date createdOn;
    public final Date updatedOn;

    public Note(long id, long notebookId, String title, Set<NoteData> data, Set<String> tags, Date createdOn, Date updatedOn) {
        this.id = id;
        this.notebookId = notebookId;
        this.title = title;
        this.data = data;
        this.tags = tags;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Note note = (Note) o;

        if (id != note.id) return false;
        if (notebookId != note.notebookId) return false;
        if (title != null ? !title.equals(note.title) : note.title != null) return false;
        if (data != null ? !data.equals(note.data) : note.data != null) return false;
        if (tags != null ? !tags.equals(note.tags) : note.tags != null) return false;
        if (createdOn != null ? !createdOn.equals(note.createdOn) : note.createdOn != null) return false;
        return updatedOn != null ? updatedOn.equals(note.updatedOn) : note.updatedOn == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (notebookId ^ (notebookId >>> 32));
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (data != null ? data.hashCode() : 0);
        result = 31 * result + (tags != null ? tags.hashCode() : 0);
        result = 31 * result + (createdOn != null ? createdOn.hashCode() : 0);
        result = 31 * result + (updatedOn != null ? updatedOn.hashCode() : 0);
        return result;
    }
}
