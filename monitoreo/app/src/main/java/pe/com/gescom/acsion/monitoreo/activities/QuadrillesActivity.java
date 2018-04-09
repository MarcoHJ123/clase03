package pe.com.gescom.acsion.monitoreo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;
import pe.com.gescom.acsion.monitoreo.R;
import pe.com.gescom.acsion.monitoreo.adapters.QuadrilleAdapter;
import pe.com.gescom.acsion.monitoreo.models.Quadrille;
import pe.com.gescom.acsion.monitoreo.models.QuadrilleService;

public class QuadrillesActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private static final String TAG = "QuadrillesActivity";
    private static String state,idTechnical, nameTechnical;
    private ListView quadrillesListView;
    private ArrayList<Quadrille> stringArrayList;
    private ArrayAdapter<Quadrille> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quadrilles);

        Bundle bundle = getIntent().getExtras();
        state = bundle.getString("estado");
        Log.d(TAG ,String.valueOf(state));
    }

    @Override
    protected void onResume() {
        try {
            quadrillesListView = (ListView) findViewById(R.id.quadrillesListView);
            setData();
            adapter = new QuadrilleAdapter(this, R.layout.activity_quadrilles, stringArrayList);
            quadrillesListView.setAdapter(adapter);
            quadrillesListView.setOnItemClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onResume();
    }

    private void setData() {
        QuadrilleService quadrilleService = new QuadrilleService();
        stringArrayList = new ArrayList<>();
        Log.d(TAG, "ListadoCuadLOSTSQL:"+ String.valueOf(state));
        List<Quadrille> listQuadrilles = quadrilleService.allQuadrille(state);
        for (Quadrille c : listQuadrilles) {
            stringArrayList.add(c);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Quadrille cuadrilla=stringArrayList.get(position);
        idTechnical = cuadrilla.getIdTechnical();
        nameTechnical = cuadrilla.getNameTechnical();
        Intent i = new Intent(this,QuadrilleActivity.class);
        i.putExtra("idTechnical",idTechnical);
        i.putExtra("nameTechnical",nameTechnical);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.opciones, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.back_dashboard) {
            Intent IntentDasboardActivity = new Intent(this, MainActivity.class);
            startActivity(IntentDasboardActivity);
        }
        if (id == R.id.back_cuadrillas) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
