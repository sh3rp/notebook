package io.kndl.notebook.dao.test;

import io.kndl.notebook.dao.MapDBNotebookDAO;
import io.kndl.notebook.dao.NotebookDAO;
import io.kndl.notebook.model.Note;
import io.kndl.notebook.model.NoteData;
import io.kndl.notebook.model.Notebook;
import io.kndl.notebook.serialization.ObjectCodec;
import org.junit.Before;
import org.junit.Test;
import org.mapdb.DB;
import org.mapdb.DBMaker;

import java.util.Set;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * Created by skend on 9/4/2016.
 */
public class MapDBNotebookDAOTest {
    private NotebookDAO dao;
    private DB db;
    @Before
    public void setup() {
        this.db = DBMaker.memoryDB().make();
        this.dao = new MapDBNotebookDAO(db,new ObjectCodec<Notebook>(),new ObjectCodec<Note>(), new ObjectCodec<NoteData>());
    }

    @Test public void getAllNotebooksTest() {
        Notebook notebook1 = new Notebook("test1",null,null);
        Notebook notebook2 = new Notebook("test2",null,null);
        Notebook notebook3 = new Notebook("test3",null,null);
        notebook1 = dao.saveNotebook(notebook1);
        notebook2 = dao.saveNotebook(notebook2);
        notebook3 = dao.saveNotebook(notebook3);

        Set<Notebook> notebooks = dao.getAllNotebooks();
        assertEquals(3,notebooks.size());
    }

    @Test public void getAllNotesByNotebookIdTest () {
        Notebook notebook = new Notebook("test1",null,null);
        notebook = dao.saveNotebook(notebook);
    }
    @Test public void getNotebookByIdTest() {}
    @Test public void saveNotebookTest() {}
}
