package br.edu.fa7.heppyhour;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ConvidadoDAO {
	
	private final static String TABLE_NAME = "convidado";
	private final static String DATABASE_NAME = "happyhour.db";
	private final static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +" ("
			+ "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ "nome TEXT NOT NULL, " 
			+ "status TEXT NOT NULL, "
			+ "contato TEXT NOT NULL, "
			+ "evento INTEGER)";
	private final static int DATABASE_VERSION = 1;
	
	private SQLHelper sqlHelper;
	private SQLiteDatabase db;
	
	public ConvidadoDAO(Context context) {
		sqlHelper = new SQLHelper(context, DATABASE_NAME, DATABASE_VERSION, CREATE_TABLE, TABLE_NAME);
		db = sqlHelper.getWritableDatabase();
	}
	
	/*
	 * Cadastra um novo convidado
	 */
	public void inserir(Convidado convidado) {
		ContentValues values = getConvidado(convidado);

		db.insert(TABLE_NAME, null, values);
	}

	
	/*
	 * Atualiza um convidado
	 */
	public void atualizar(Convidado convidado) {
		ContentValues values = getConvidado(convidado);

		String[] id = { String.valueOf(convidado.getId()) };
		db.update(TABLE_NAME, values, "_id = ?", id);
	}
	
	/*
	 * Exclui um convidado
	 */
	public void excluir(Convidado convidado) {
		String[] id = { String.valueOf(convidado.getId()) };
		db.delete(TABLE_NAME, "_id = ?", id);
	}
	
	/*
	 * Localiza um convidado pelo seu id
	 */
	public Convidado buscarConvidado(int id) {
		String[] columns = { "_id", "nome", "status", "contato", "evento" };
		String[] param = { String.valueOf(id) };
		Cursor c = db.query(TABLE_NAME, columns, "_id = ?", param, null, null, "_id");
		c.moveToFirst();
		
		Convidado p = setConvidado(c);
		
		return p;
	}
	
	/*
	 * Lista todos os convidados ordenados pelo Nome
	 */
	public List<Convidado> buscarTodosConvidados() {

		List<Convidado> lista = null;
		String[] columns = { "_id", "nome", "status", "contato", "evento" };
		Cursor c = db.query(TABLE_NAME, columns, null, null, null, null, "nome");
		
		lista = new ArrayList<Convidado>();
		if (c.getCount() > 0) {
			c.moveToFirst();
			while (!c.isAfterLast()) {
			
				Convidado p = setConvidado(c);
				
				lista.add(p);
				c.moveToNext();
			}
		}
		
		return lista;
	}

	private Convidado setConvidado(Cursor c) {
		Convidado p = new Convidado();
		p.setId(c.getInt(0));
		p.setNome(c.getString(1));
		p.setStatus(c.getString(2));
		p.setContato(c.getString(3));
		p.setEvento(c.getInt(4));
		return p;
	}
	
	private ContentValues getConvidado(Convidado convidado) {
		ContentValues values = new ContentValues();
		values.put("nome", convidado.getNome());
		values.put("status", convidado.getStatus());
		values.put("contato", convidado.getContato());
		values.put("evento", convidado.getEvento());
		return values;
	}

}
