package pe.com.gescom.acsion.monitoreo.models;

import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import pe.com.gescom.acsion.monitoreo.activities.helpers.QuadrillesAsyncTask;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.arrayContaining;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.beans.HasProperty.hasProperty;
import static org.hamcrest.text.IsEmptyString.isEmptyOrNullString;
import static org.hamcrest.text.IsEmptyString.isEmptyString;
import static org.junit.Assert.*;

/**
 * Created by Administrador on 29/11/2016.
 */
@RunWith(AndroidJUnit4.class)
public class QuadrilleServiceInstrumentedTest {
    @Test
    public void addQuadrille() throws Exception {
        QuadrilleService quadrillesService = new QuadrilleService();
        Quadrille quadrille = new Quadrille();
        quadrille.setAmountDownloaded(1);
        quadrille.setAmountFinished(1);
        quadrille.setAmountGenerated(1);
        quadrille.setAmountPending(1);
        quadrille.setIdTechnical("1");
        quadrille.setNameTechnical("Joel");
        Quadrille response = quadrillesService.addQuadrille(quadrille);
        //assertEquals("2", response.getIdTechnical()); Junit
        //assertThat(response.getIdTechnical(), isEmptyString());
        //assertThat(response.getIdTechnical(), isEmptyOrNullString());
        assertThat(response.getIdTechnical(), is(String.valueOf(1)));
        assertThat(response.getNameTechnical(), is(String.valueOf("Joel")));
    }

    @Test
    public void allQuadrille() throws Exception {
        ArrayList<Quadrille> stringArrayList;
        String state,idTechnical, nameTechnical;
        QuadrilleService quadrilleService = new QuadrilleService();
        stringArrayList = new ArrayList<>();
        //Soporte para siguientes valores: 1,2,3,4 y por defecto si un valor  no esta reconocido sera 1.
        state = "2";
        List<Quadrille> listQuadrilles = quadrilleService.allQuadrille(state);
        for (Quadrille c : listQuadrilles) {
            stringArrayList.add(c);
        }
        Quadrille cuadrilla = stringArrayList.get(0);
        idTechnical = cuadrilla.getIdTechnical();
        nameTechnical = cuadrilla.getNameTechnical();
        assertThat(nameTechnical, is(String.valueOf("Joel")));
        //assertThat(nameTechnical, is(String.valueOf("Elias Lopez Lujan")));
        assertThat(idTechnical, is(String.valueOf("1")));
    }

    @Test
    public void getSummaryStatusQuadrilles() throws Exception {
        QuadrilleService quadrilleService = new QuadrilleService();
        List<Quadrille> listSummaryStatusQuadrilles =quadrilleService.getSummaryStatusQuadrilles();
        assertThat(listSummaryStatusQuadrilles, hasSize(4));
    }

    @Test
    public void getTotalStatusQuadrilles() throws Exception {
        QuadrilleService quadrilleService = new QuadrilleService();
        float total_state= quadrilleService.getTotalStatusQuadrilles();
        assertThat(total_state, is(Float.valueOf(1)));
    }

    @Test
    public void containsNumbersInAnyOrder() {
        List<Integer> list = Arrays.asList(5, 2, 4);
        assertThat(list, contains(4, 2, 5));
    }

    @Test
    public void getSummaryQuadrille() throws Exception {
        QuadrilleService quadrilleService = new QuadrilleService();
        String idTechnical = "1";
        List<Quadrille> listSummaryQuadrille =quadrilleService.getSummaryQuadrille(idTechnical);
        assertThat(listSummaryQuadrille.get(0).getAmountDownloaded(), is(2));
    }

}