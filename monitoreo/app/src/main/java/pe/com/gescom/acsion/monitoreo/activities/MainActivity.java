package pe.com.gescom.acsion.monitoreo.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import pe.com.gescom.acsion.monitoreo.R;
import pe.com.gescom.acsion.monitoreo.models.Quadrille;
import pe.com.gescom.acsion.monitoreo.models.QuadrilleService;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private TableLayout tableSummaryStatusQuadrillesTableLayout;
    private PieChart chartSummaryStatusQuadrillesPieChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tableSummaryStatusQuadrillesTableLayout = (TableLayout) findViewById(R.id.tableSummaryStatusQuadrilles);
        chartSummaryStatusQuadrillesPieChart = (PieChart) findViewById(R.id.chartSummaryStatusQuadrilles);
        createSummaryStatusQuadrillesChart();
        createSummaryStatusQuadrillesDataTable();
    }
    /**
     * Crea una grafico
     *
     * */
    private void createSummaryStatusQuadrillesChart(){
        QuadrilleService quadrilleService = new QuadrilleService();
        List<Quadrille> listSummaryStatusQuadrilles = quadrilleService.getSummaryStatusQuadrilles();
        float total_state= quadrilleService.getTotalStatusQuadrilles();
        ArrayList<Entry> entries = new ArrayList<>();
        int i=0;
        for (Quadrille c : listSummaryStatusQuadrilles) {
            float total = (c.getValueState()/total_state)*100f;
            entries.add(new Entry(total, i));
        }
        PieDataSet dataset = new PieDataSet(entries, "#");
        ArrayList<String> labels = new ArrayList<String>();
        labels.add(getString(R.string.label_chart_downloaded));
        labels.add(getString(R.string.label_chart_finished));
        labels.add(getString(R.string.label_chart_generated));
        labels.add(getString(R.string.label_chart_pending));
        PieData data = new PieData(labels, dataset);
        dataset.setColors(ColorTemplate.COLORFUL_COLORS);
        chartSummaryStatusQuadrillesPieChart.setDescription(getString(R.string.description_chart));
        chartSummaryStatusQuadrillesPieChart.setData(data);
        chartSummaryStatusQuadrillesPieChart.animateY(1000);
        chartSummaryStatusQuadrillesPieChart.setCenterText(generateCenterSpannableText());
    }

    /**
     * Crea una tabla con los registros de votacion
     * */
    private void createSummaryStatusQuadrillesDataTable() {
        QuadrilleService quadrilleService = new QuadrilleService();
        List<Quadrille> listSummaryStatusQuadrilles =quadrilleService.getSummaryStatusQuadrilles();
        TableRow.LayoutParams tableRowParams = new TableRow.LayoutParams();
        tableRowParams.setMargins(5, 3, 5, 3);
        tableRowParams.weight = 1;
        //encabezado
        TableRow rowHead = new TableRow(this);
        rowHead.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        //col 1
        TextView col1 = new TextView(this);
        col1.setBackgroundResource(R.drawable.tv_bg);
        col1.setGravity(Gravity.CENTER);
        col1.setTypeface(null, Typeface.BOLD);
        col1.setTextSize(12);
        col1.setPadding(6, 10, 6, 10);
        col1.setText( "NOMBRE" );
        //col 2
        TextView col2 = new TextView(this);
        col2.setBackgroundResource(R.drawable.tv_bg);
        col2.setGravity(Gravity.CENTER);
        col2.setTypeface(null, Typeface.BOLD);
        col2.setTextSize(12);
        col2.setPadding(6, 10, 6, 10);
        col2.setText( "SUMINISTROS" );
        //col 3
        TextView col3 = new TextView(this);
        col3.setBackgroundResource(R.drawable.tv_bg);
        col3.setGravity(Gravity.CENTER);
        col3.setTypeface(null, Typeface.BOLD);
        col3.setTextSize(12);
        col3.setPadding(6, 10, 6, 10);
        col3.setText( "ACCION" );
        //agrega columnas
        rowHead.addView(col1,tableRowParams);
        rowHead.addView(col2,tableRowParams);
        rowHead.addView(col3,tableRowParams);
        //agrega fila
        tableSummaryStatusQuadrillesTableLayout.addView(rowHead);
        //filas de datos
        for (Quadrille c : listSummaryStatusQuadrilles) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            //col 1
            TextView tv1 = new TextView(this);
            tv1.setBackgroundResource(R.drawable.tv_bg);
            tv1.setGravity(Gravity.CENTER);
            tv1.setTypeface(null, Typeface.BOLD);
            tv1.setTextSize(12);
            tv1.setPadding(6, 10, 6, 10);
            tv1.setText(c.getNameState() );
            //col 2
            TextView tv2 = new TextView(this);
            tv2.setBackgroundResource(R.drawable.tv_bg);
            tv2.setGravity(Gravity.CENTER);
            tv2.setTextSize(12);
            tv2.setPadding(6, 10, 6, 10);
            tv2.setText(String.valueOf(c.getValueState()));
            //col 3
            TextView tv3 = new TextView(this);
            tv3.setBackgroundResource(R.drawable.tv_bg);
            tv3.setGravity(Gravity.CENTER);
            tv3.setTextSize(12);
            tv3.setPadding(6, 10, 6, 10);
            tv3.setText("VER CUADRILLA");
            //agrega columnas
            row.addView(tv1,tableRowParams);
            row.addView(tv2,tableRowParams);
            row.addView(tv3,tableRowParams);
            row.setClickable(true);
            row.setId(c.getIdState());
            row.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                //Highlight selected row
                for (int i = 0; i < tableSummaryStatusQuadrillesTableLayout.getChildCount(); i++) {
                    View row = tableSummaryStatusQuadrillesTableLayout.getChildAt(i);
                    if (row == v) {
                        row.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));
                        Log.d("DasboardClick:", String.valueOf(v.getId()));
                        Intent post = new Intent(MainActivity.this,QuadrillesActivity.class);
                        post.putExtra("estado", String.valueOf(v.getId()));
                        startActivity(post);
                    }else{
                        //Change this to your normal background color.
                        row.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    }
                }
                //...
                }
            });
            //agrega fila
            tableSummaryStatusQuadrillesTableLayout.addView(row);
        }
    }

    private SpannableString generateCenterSpannableText() {
        SpannableString s = new SpannableString("Estados\n de avance de trabajo");
        return s;
    }

    /**
     * [Metodo para cerrar sesion cuando se regrese al Login]
     */
    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        //moveTaskToBack(true);
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("¿Desea salir de ACSION?");
        dialog.setTitle("Confirmacion");
        dialog.setCancelable(false);
        dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseAuth mAuth;
                // [START initialize_auth]
                mAuth = FirebaseAuth.getInstance();
                // [END initialize_auth]
                mAuth.signOut();
                finish();
            }
        });
        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialog.show();
    }
}
