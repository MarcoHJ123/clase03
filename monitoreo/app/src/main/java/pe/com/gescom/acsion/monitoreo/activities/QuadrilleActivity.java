package pe.com.gescom.acsion.monitoreo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import pe.com.gescom.acsion.monitoreo.R;
import pe.com.gescom.acsion.monitoreo.models.Quadrille;
import pe.com.gescom.acsion.monitoreo.models.QuadrilleService;

public class QuadrilleActivity extends AppCompatActivity {
    private static final String TAG = "QuadrilleActivity";
    private PieChart chartSummaryQuadrillePieChart;
    private static String idTechnical,nameTechnical;
    private TextView nameTechnicalTextView,idTechnicalTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quadrille);
        chartSummaryQuadrillePieChart = (PieChart) findViewById(R.id.chartSummaryQuadrille);
        Bundle bundle = getIntent().getExtras();
        idTechnical = bundle.getString("idTechnical");
        nameTechnical = bundle.getString("nameTechnical");
        idTechnicalTextView = (TextView) findViewById(R.id.idTechnicalTextView);
        nameTechnicalTextView = (TextView) findViewById(R.id.nameTechnicalTextView);
        idTechnicalTextView.setText(idTechnical);
        nameTechnicalTextView.setText(nameTechnical);
        createSummaryQuarilleChart();
    }

    private void createSummaryQuarilleChart(){
        QuadrilleService quadrilleService = new QuadrilleService();
        List<Quadrille> listSummaryQuadrille =quadrilleService.getSummaryQuadrille(idTechnical);
        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(listSummaryQuadrille.get(0).getAmountDownloaded(), 0));
        entries.add(new Entry(listSummaryQuadrille.get(0).getAmountFinished(), 1));
        entries.add(new Entry(listSummaryQuadrille.get(0).getAmountGenerated(), 2));
        entries.add(new Entry(listSummaryQuadrille.get(0).getAmountPending(), 3));
        PieDataSet dataset = new PieDataSet(entries, "#");
        ArrayList<String> labels = new ArrayList<String>();
        labels.add(getString(R.string.label_chart_downloaded));
        labels.add(getString(R.string.label_chart_finished));
        labels.add(getString(R.string.label_chart_generated));
        labels.add(getString(R.string.label_chart_pending));
        PieData data = new PieData(labels, dataset);
        dataset.setColors(ColorTemplate.COLORFUL_COLORS);
        chartSummaryQuadrillePieChart.setDescription(getString(R.string.description_chart));
        chartSummaryQuadrillePieChart.setData(data);
        chartSummaryQuadrillePieChart.animateY(1000);
        chartSummaryQuadrillePieChart.setCenterText(generateCenterSpannableText());
    }

    private SpannableString generateCenterSpannableText() {
        SpannableString s = new SpannableString("Estados\n de avance de trabajo");
        return s;
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
