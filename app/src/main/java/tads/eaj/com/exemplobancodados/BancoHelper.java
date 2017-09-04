package tads.eaj.com.exemplobancodados;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class BancoHelper extends SQLiteOpenHelper {

    //String auxiliares
    private static final String TAG = "sql";
    private static final String TEXT_TYPE = " TEXT";
    private static final String NUMBER_TYPE = " INTEGER";
    private static final String VIRGULA = ",";

    // Nome do banco
    private static final String DATABASE_NAME = "banco_exemplo.sqlite";

    //versão do banco
    private static final int DATABASE_VERSION = 1;

    //SQLs
    private static final String SQL_CREATE_TABLE =
            ("CREATE TABLE " + CarroContrato.CarroEntry.TABLE_NAME +
                    "("+
                    CarroContrato.CarroEntry._ID + NUMBER_TYPE+  " PRIMARY KEY"+ VIRGULA+
                    CarroContrato.CarroEntry.NOME + TEXT_TYPE + VIRGULA+
                    CarroContrato.CarroEntry.DESCRICAO + TEXT_TYPE + VIRGULA+
                    CarroContrato.CarroEntry.TIPO_CARRO + TEXT_TYPE + VIRGULA+
                    CarroContrato.CarroEntry.ANO + NUMBER_TYPE+
                    ");"
            );
    private static final String SQL_DROP_TABLE =
            ("DROP TABLE "+ CarroContrato.CarroEntry.TABLE_NAME+";"
            );



    public BancoHelper(Context context) {
        // context, nome do banco, factory, versão
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "Não foi possível acessar o banco, criando um novo...");
        db.execSQL(SQL_CREATE_TABLE);
        Log.d(TAG, "Banco criado com sucesso.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Caso mude a versãoo do banco de dados, podemos executar um SQL aqui
        if (oldVersion != newVersion) {
            // Execute o script para atualizar a versão...
            Log.d(TAG, "Foi detectada uma nova versão do banco, aqui deverão ser executados os scripts de update.");
            db.execSQL(SQL_DROP_TABLE);
            this.onCreate(db);
        }
    }

    @Override
    public void  onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
        if (oldVersion != newVersion) {
            // Execute o script para atualizar a versão...
            Log.d(TAG, "Foi detectada uma nova versão do banco, aqui deverão ser executados os scripts de downgrade.");
            db.execSQL(SQL_DROP_TABLE);
            this.onCreate(db);
        }
    }

    // Insere um novo carro, ou atualiza se já existe.
    public long save(Carro carro) {

        long id = carro.getId();
        SQLiteDatabase db = getWritableDatabase();

        try {
            ContentValues values = new ContentValues();
            values.put(CarroContrato.CarroEntry.NOME , carro.getNome());
            values.put(CarroContrato.CarroEntry.DESCRICAO, carro.getDesc());
            values.put(CarroContrato.CarroEntry.TIPO_CARRO , carro.getTipo());
            values.put(CarroContrato.CarroEntry.ANO , carro.getAno());

            if (id != 0) {

                String selection = CarroContrato.CarroEntry._ID + "= ?";
                String[] whereArgs = new String[]{String.valueOf(id)};

                // update carro set values = ... where _id=?
                int count = db.update(CarroContrato.CarroEntry.TABLE_NAME, values, selection, whereArgs);
                Log.i(TAG, "Atualizou id [" + id + "] no banco.");
                return count;

            } else {
                // insert into carro values (...)-------------------alterei de "" para null
                id = db.insert(CarroContrato.CarroEntry.TABLE_NAME, null, values);
                Log.i(TAG, "Inseriu id [" + id + "] no banco.");
                return id;
            }
        } finally {
            db.close();
        }
    }

    // Deleta o carro
    public int delete(Carro carro) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            // delete from carro where _id=?
            String selection = CarroContrato.CarroEntry._ID + "= ?";
            String[] whereArgs = new String[] {String.valueOf(carro.getId())};
            int count = db.delete(CarroContrato.CarroEntry.TABLE_NAME, selection,whereArgs);
            Log.i(TAG, "Deletou " + count + " registro.");
            return count;
        } finally {
            db.close();
        }
    }

    // Deleta os carros do tipo fornecido
    public int deleteCarrosByTipo(String tipo) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            // delete from carro where tipo=?

            String selection = CarroContrato.CarroEntry.TIPO_CARRO + "= ?";
            int count = db.delete(CarroContrato.CarroEntry.TABLE_NAME, selection, new String[]{tipo});
            Log.i(TAG, "Deletou [" + count + "] registro(s)");
            return count;
        } finally {
            db.close();
        }
    }

    // Consulta a lista com todos os carros
    public List<Carro> findAll() {
        SQLiteDatabase db = getReadableDatabase();
        try {
            // select * from carro
            Cursor c = db.query(CarroContrato.CarroEntry.TABLE_NAME, null, null, null, null, null, null, null);
            Log.i(TAG, "Listou todos os registros");
            return toList(c);
        } finally {
            db.close();
        }
    }

    // Consulta a lista com todos os carros
    public Carro findById(int id) {
        SQLiteDatabase db = getReadableDatabase();

        Log.i(TAG, "Buscou carro com id = "+ id);

        try {
            // select * from carro
            String selection = CarroContrato.CarroEntry._ID + "= ?";
            String[] whereArgs = new String[]{String.valueOf(id)};
            Cursor c = db.query(CarroContrato.CarroEntry.TABLE_NAME, null, selection, whereArgs, null, null, null, null);

            if (c.moveToFirst()){
                Carro carro = new Carro();

                // recupera os atributos de carro
                carro.setId(c.getInt(c.getColumnIndex(CarroContrato.CarroEntry._ID)));
                carro.setNome(c.getString(c.getColumnIndex(CarroContrato.CarroEntry.NOME)));
                carro.setTipo(c.getString(c.getColumnIndex(CarroContrato.CarroEntry.TIPO_CARRO)));
                carro.setDesc(c.getString(c.getColumnIndex(CarroContrato.CarroEntry.DESCRICAO)));
                carro.setAno(c.getInt(c.getColumnIndex(CarroContrato.CarroEntry.ANO)));

                return carro;
            }else {
                return null;
            }
        } finally {
            db.close();
        }
    }

    // Consulta o carro pelo tipo
    public List<Carro> findAllByTipo(String tipo) {
        SQLiteDatabase db = getReadableDatabase();
        try {
            // "select * from carro where tipo=?"
            String selection = CarroContrato.CarroEntry.TIPO_CARRO + "= ?";
            String[] whereArgs = new String[]{String.valueOf(tipo)};
            Cursor c = db.query(CarroContrato.CarroEntry.TABLE_NAME, null, selection, whereArgs, null, null, null);
            Log.i(TAG, "Listou todos os registros por tipo");
            return toList(c);
        } finally {
            db.close();
        }
    }

    // Lê o cursor e cria a lista de carros
    private List<Carro> toList(Cursor c) {

        List<Carro> carros = new ArrayList<>();

        if (c.moveToFirst()) {
            do {
                Carro carro = new Carro();
                carros.add(carro);

                // recupera os atributos de carro
                carro.setId(c.getInt(c.getColumnIndex(CarroContrato.CarroEntry._ID)));
                carro.setNome(c.getString(c.getColumnIndex(CarroContrato.CarroEntry.NOME)));
                carro.setTipo(c.getString(c.getColumnIndex(CarroContrato.CarroEntry.TIPO_CARRO)));
                carro.setDesc(c.getString(c.getColumnIndex(CarroContrato.CarroEntry.DESCRICAO)));
                carro.setAno(c.getInt(c.getColumnIndex(CarroContrato.CarroEntry.ANO)));

            } while (c.moveToNext());
        }

        return carros;
    }

    // Executa um SQL
    public void execSQL(String sql) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.execSQL(sql);
        } finally {
            db.close();
        }
    }

    // Executa um SQL
    public void execSQL(String sql, Object[] args) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.execSQL(sql, args);
        } finally {
            db.close();
        }
    }
}