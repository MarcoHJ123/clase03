package pe.com.gescom.acsion.monitoreo.models;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.orm.SugarContext;
import com.orm.SugarDb;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gescom on 16/11/2016.
 */

public class QuadrilleService {
    public static String QUADRILLES_URL = "http://ti.gescom.com.pe/acsion/acl-intranet/api/monitoreo/listar_consolidado_tecnico/";

    /**
     * Guarda la informacion de cuadrillas que han sido obtenidas desde web services.
     * @param quadrille
     * @return null
     */
    public Quadrille addQuadrille(Quadrille quadrille) {
        List<Quadrille> beforeQuadrille = Quadrille.listAll(Quadrille.class);
        Quadrille.deleteAll(Quadrille.class);

        long id = quadrille.save();
        return Quadrille.findById(Quadrille.class, id);
    }

    public List<Quadrille> allQuadrille(String state){
        List<Quadrille> quadrilleList = new ArrayList<Quadrille>();
        switch(String.valueOf(state)) {
            case "1":
                state = "amount_downloaded";
                break;
            case "2":
                state = "amount_finished";
                break;
            case "3":
                state = "amount_generated";
                break;
            case "4":
                state = "amount_pending";
                break;
            default:
                state = "amount_downloaded";
                break;
        }
        Cursor cursor = getDatabase().rawQuery("SELECT NAME_TECHNICAL,ID_TECHNICAL FROM QUADRILLE ORDER BY "+ state, null);
        if (cursor.moveToNext()) {
            do{
                Quadrille quadrille = new Quadrille();
                quadrille.setNameTechnical(cursor.getString(cursor.getColumnIndex("NAME_TECHNICAL")));
                quadrille.setIdTechnical(cursor.getString(cursor.getColumnIndex("ID_TECHNICAL")));
                quadrilleList.add(quadrille);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return quadrilleList;
    }


    /**
     * Resumen por estados
     * Con los estados de las cuadrillas("DESCARGADOS", "FINALIZADOS", "GENERADOS", "PENDIENTES") existentes se agrupa y se suma para obtener un total por cada estado.
     * @return ArrayList setIdEstado,  setNombreEstado, setValorEstado
     */
    public List<Quadrille> getSummaryStatusQuadrilles() {
        List<Quadrille> listSummaryStatusQuadrilles = new ArrayList<Quadrille>();
        String query ;
        Cursor cursor;
        String estados[] = {"DESCARGADOS", "FINALIZADOS", "GENERADOS", "PENDIENTES"};
        int size = estados.length;
        for (int i=0; i<size; i++) {
            switch(estados[i]) {
                case "DESCARGADOS":
                    query = "SELECT SUM(amount_downloaded) AS total_descargado FROM QUADRILLE";
                    cursor = getDatabase().rawQuery(query, null);
                    if (cursor.moveToNext()) {
                        do{
                            Quadrille quadrille = new Quadrille();
                            quadrille.setValueState(cursor.getInt(cursor.getColumnIndex("total_descargado")));
                            quadrille.setNameState("DESCARGADOS");
                            quadrille.setIdState(1);
                            listSummaryStatusQuadrilles.add(quadrille);
                        }while(cursor.moveToNext());
                    }
                    cursor.close();
                    break;
                case "FINALIZADOS":
                    query = "SELECT SUM(amount_finished) AS total_finalizados FROM QUADRILLE";
                    cursor = getDatabase().rawQuery(query, null);
                    if (cursor.moveToNext()) {
                        do{
                            Quadrille quadrille = new Quadrille();
                            quadrille.setValueState(cursor.getInt(cursor.getColumnIndex("total_finalizados")));
                            quadrille.setNameState("FINALIZADOS");
                            quadrille.setIdState(2);
                            listSummaryStatusQuadrilles.add(quadrille);
                        }while(cursor.moveToNext());
                    }
                    cursor.close();
                    break;
                case "GENERADOS":
                    query = "SELECT SUM(amount_generated) AS total_generados FROM QUADRILLE";
                    cursor = getDatabase().rawQuery(query, null);
                    if (cursor.moveToNext()) {
                        do{
                            Quadrille quadrille = new Quadrille();
                            quadrille.setValueState(cursor.getInt(cursor.getColumnIndex("total_generados")));
                            quadrille.setNameState("GENERADOS");
                            quadrille.setIdState(3);
                            listSummaryStatusQuadrilles.add(quadrille);
                        }while(cursor.moveToNext());
                    }
                    cursor.close();
                    break;
                case "PENDIENTES":
                    query = "SELECT SUM(amount_pending) AS total_pendientes FROM QUADRILLE";
                    cursor = getDatabase().rawQuery(query, null);
                    if (cursor.moveToNext()) {
                        do{
                            Quadrille quadrille = new Quadrille();
                            quadrille.setValueState(cursor.getInt(cursor.getColumnIndex("total_pendientes")));
                            quadrille.setNameState("PENDIENTES");
                            quadrille.setIdState(4);
                            listSummaryStatusQuadrilles.add(quadrille);
                        }while(cursor.moveToNext());
                    }
                    cursor.close();
                    break;
                default:
                    break;
            }
        }
        return listSummaryStatusQuadrilles;
    }

    /**
     * Suma todos los estados de las cuadrillas y  retorna el total.
     * @return total
     */
    public int getTotalStatusQuadrilles() {
        String query = "SELECT SUM(amount_downloaded + amount_finished + amount_generated + amount_pending) AS total FROM QUADRILLE";
        Cursor cursor = getDatabase().rawQuery(query, null);
        cursor.moveToFirst();
        int total = cursor.getInt(cursor.getColumnIndexOrThrow("total"));
        cursor.close();
        return total;
    }

    public List<Quadrille> getSummaryQuadrille(String id_technical){
        //return Cuadrilla.listAll(Cuadrilla.class);
        List<Quadrille> cuadrillaList = new ArrayList<Quadrille>();
        String query = "SELECT * FROM QUADRILLE WHERE ID_TECHNICAL ="+id_technical;
        Cursor cursor = getDatabase().rawQuery(query, null);
        if (cursor.moveToNext()) {
            do{
                Quadrille quadrille = new Quadrille();
                quadrille.setAmountDownloaded(cursor.getInt(cursor.getColumnIndex("amount_downloaded".toUpperCase())));
                quadrille.setAmountFinished(cursor.getInt(cursor.getColumnIndex("amount_finished".toUpperCase())));
                quadrille.setAmountGenerated(cursor.getInt(cursor.getColumnIndex("amount_generated".toUpperCase())));
                quadrille.setAmountPending(cursor.getInt(cursor.getColumnIndex("amount_pending".toUpperCase())));
                cuadrillaList.add(quadrille);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return cuadrillaList;
    }

    private SQLiteDatabase getDatabase() {
        try {
            Field f = SugarContext.getSugarContext().getClass().getDeclaredField("sugarDb");
            f.setAccessible(true);
            return ((SugarDb) f.get(SugarContext.getSugarContext())).getDB();

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

}
