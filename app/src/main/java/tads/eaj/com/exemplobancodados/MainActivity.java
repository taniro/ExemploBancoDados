package tads.eaj.com.exemplobancodados;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "sql";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        BancoHelper db = new BancoHelper(this);

        Carro corsa = new Carro(2000, "Corsa", "Hatch", "Carro Corsa");
        Carro palio = new Carro(2016, "Palio", "Hatch", "Carro Palio");
        Carro hbvinte = new Carro(2014, "HB20", "Sedan", "Carro HB20");
        Carro tuckson = new Carro(2015, "Tuckson", "Sedan", "Carro Tuckson");
        Carro corsa2 = new Carro(2015, "Corsa", "Hatch", "Carro Corsa 2");

        db.save(corsa);
        db.save(palio);
        db.save(hbvinte);
        db.save(tuckson);
        db.save(corsa2);

       List<Carro> lista = db.findAll();

       for (Carro c: lista) {
           Log.i(TAG, c.toString());
       }


       /*
        * ATENÇÃO: Como essa aplicação usa IDs fixos para os testes, ela precisa ser
        * desinstalada para que volte a funcionar depois de certa quantidade de execuções.
        */

        tuckson = db.findById(4);
        tuckson.setDesc("Nova descrição.");
        db.save(tuckson);

        lista = db.findAll();
        for (Carro c: lista) {
            Log.i(TAG, c.toString());
        }

        db.delete(db.findById(1));
        db.delete(db.findById(2));

        lista = db.findAll();
        for (Carro c: lista) {
            Log.i(TAG, c.toString());
        }

        db.deleteCarrosByTipo("Sedan");

        lista = db.findAll();
        for (Carro c: lista) {
            Log.i(TAG, c.toString());
        }

        lista = db.findAllByTipo("Sedan");
        for (Carro c: lista) {
            Log.i(TAG, c.toString());
        }

        /*
            Não foi possível acessar o banco, criando um novo...
            Banco criado com sucesso.
            Inseriu id [1] no banco.
            Inseriu id [2] no banco.
            Inseriu id [3] no banco.
            Inseriu id [4] no banco.
            Inseriu id [5] no banco.
            Listou todos os registros
            Carro{id=1, nome='Corsa', tipo='Hatch', desc='Carro Corsa', ano=2000}
            Carro{id=2, nome='Palio', tipo='Hatch', desc='Carro Palio', ano=2016}
            Carro{id=3, nome='HB20', tipo='Sedan', desc='Carro HB20', ano=2014}
            Carro{id=4, nome='Tuckson', tipo='Sedan', desc='Carro Tuckson', ano=2015}
            Carro{id=5, nome='Corsa', tipo='Hatch', desc='Carro Corsa 2', ano=2015}
            Buscou carro com id = 4
            Atualizou id [4] no banco.
            Listou todos os registros
            Carro{id=1, nome='Corsa', tipo='Hatch', desc='Carro Corsa', ano=2000}
            Carro{id=2, nome='Palio', tipo='Hatch', desc='Carro Palio', ano=2016}
            Carro{id=3, nome='HB20', tipo='Sedan', desc='Carro HB20', ano=2014}
            Carro{id=4, nome='Tuckson', tipo='Sedan', desc='Nova descrição.', ano=2015}
            Carro{id=5, nome='Corsa', tipo='Hatch', desc='Carro Corsa 2', ano=2015}
            Buscou carro com id = 1
            Deletou 1 registro.
            Buscou carro com id = 2
            Deletou 1 registro.
            Listou todos os registros
            Carro{id=3, nome='HB20', tipo='Sedan', desc='Carro HB20', ano=2014}
            Carro{id=4, nome='Tuckson', tipo='Sedan', desc='Nova descrição.', ano=2015}
            Carro{id=5, nome='Corsa', tipo='Hatch', desc='Carro Corsa 2', ano=2015}
            Deletou [2] registro(s)
            Listou todos os registros
            Carro{id=5, nome='Corsa', tipo='Hatch', desc='Carro Corsa 2', ano=2015}
            Listou todos os registros por tipo
         */

    }
}
