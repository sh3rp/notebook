package io.kndl.notebook.dao;

import io.kndl.notebook.model.Note;
import io.kndl.notebook.model.Notebook;

import java.util.Set;

/**
 * Created by skend on 9/3/2016.
 */
public interface NotebookDAO {

    // accessors

    public Set<Notebook> getAllNotebooks();
    public Notebook getNotebookById(long id);
    public Note getNoteById(long notebookId, long noteId);
    public Set<Note> getAllNotes(long notebookId);
    public boolean tagNotebook(long notebookId, String tag);
    public boolean tagNote(long notebookId, long noteId, String tag);

    // mutators

    public Notebook saveNotebook(Notebook notebook);
    public Note saveNote(Note note);
    public boolean deleteNote(long notebookId, long noteId);
    public boolean deleteNotebook(long notebookId);

}
