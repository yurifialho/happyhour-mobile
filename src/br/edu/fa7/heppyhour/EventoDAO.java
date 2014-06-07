package br.edu.fa7.heppyhour;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class EventoDAO {
	
	private final static String TABLE_NAME = "evento";
	private final static String DATABASE_NAME = "happyhour.db";
	private final static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +" ("
			+ "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ "local TEXT NOT NULL, " 
			+ "descricao TEXT NOT NULL, "
			+ "data TEXT NOT NULL, "
			+ "hora TEXT NOT NULL)";
	private final static int DATABASE_VERSION = 1;
	
	private SQLHelper sqlHelper;
	private SQLiteDatabase db;
	
	public EventoDAO(Context context) {
		sqlHelper = new SQLHelper(context, DATABASE_NAME, DATABASE_VERSION, CREATE_TABLE, TABLE_NAME);
		db = sqlHelper.getWritableDatabase();
	}
	
	/*
	 * Cadastra um novo evento
	 */
	public void inserir(Evento evento) {
		ContentValues values = getEvento(evento);

		db.insert(TABLE_NAME, null, values);
	}

	
	/*
	 * Atualiza um evento
	 */
	public void atualizar(Evento evento) {
		ContentValues values = getEvento(evento);

		String[] id = { String.valueOf(evento.getId()) };
		db.update(TABLE_NAME, values, "_id = ?", id);
	}
	
	/*
	 * Exclui um evento
	 */
	public void excluir(Evento evento) {
		String[] id = { String.valueOf(evento.getId()) };
		db.delete(TABLE_NAME, "_id = ?", id);
	}
	
	/*
	 * Localiza um evento pelo seu id
	 */
	public Evento buscarEvento(int id) {
		String[] columns = { "_id", "local", "descricao", "data", "hora" };
		String[] param = { String.valueOf(id) };
		Cursor c = db.query(TABLE_NAME, columns, "_id = ?", param, null, null, "_id");
		c.moveToFirst();
		
		Evento p = setEvento(c);
		
		return p;
	}
	
	/*
	 * Lista todos os eventos ordenados pelo Nome
	 */
	public List<Evento> buscarTodosEventos() {

		List<Evento> lista = null;
		String[] columns = {  "_id", "local", "descricao", "data", "hora" };
		Cursor c = db.query(TABLE_NAME, columns, null, null, null, null, "nome");
		
		lista = new ArrayList<Evento>();
		if (c.getCount() > 0) {
			c.moveToFirst();
			while (!c.isAfterLast()) {
			
				Evento p = setEvento(c);
				
				lista.add(p);
				c.moveToNext();
			}
		}
		
		return lista;
	}

	private Evento setEvento(Cursor c) {
		Evento p = new Evento();
		p.setId(c.getInt(0));
		p.setLocal(c.getString(1));
		p.setDescricao(c.getString(2));
		p.setData(c.getString(3));
		p.setHora(c.getString(4));
		return p;
	}
	
	private ContentValues getEvento(Evento evento) {
		ContentValues values = new ContentValues();
		values.put("local", evento.getLocal());
		values.put("descricao", evento.getDescricao());
		values.put("data", evento.getData());
		values.put("hora", evento.getHora());
		return values;
	}

}
