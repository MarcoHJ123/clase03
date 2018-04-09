package pe.com.gescom.acsion.monitoreo.activities.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pe.com.gescom.acsion.monitoreo.activities.MainActivity;
import pe.com.gescom.acsion.monitoreo.models.Quadrille;
import pe.com.gescom.acsion.monitoreo.models.QuadrilleService;
import pe.com.gescom.acsion.monitoreo.utils.JsonParser;

/**
 * Created by gescom on 16/11/2016.
 */

public class QuadrillesAsyncTask extends AsyncTask<String, String, String> {
    public static String QUADRILLES_URL = "http://ti.gescom.com.pe/acsion/acl-intranet/api/monitoreo/listar_consolidado_tecnico/";

    private QuadrilleService quadrillesService = new QuadrilleService();
    private String mensaje;
    private Context context;

    //Para enviar vai Context u otra variable por AsyncTask
    public QuadrillesAsyncTask(Context context) {
        this.context = context;
    }

    protected void onPreExecute() {
    }

    @Override
    protected String doInBackground(String... params) {

        try {
            JsonParser jsonParser = new JsonParser();
            JSONObject JSONIngreso = new JSONObject();

            try {
                JSONIngreso.put("fecha_inicio",params[0]);
                JSONIngreso.put("fecha_fin",params[1]);
                JSONIngreso.put("proveedor_id",Integer.parseInt(params[2]));
                JSONIngreso.put("uunn_id",Integer.parseInt(params[3]));
            } catch (JSONException e1) {
                e1.printStackTrace();
            }

            Log.d("Parametros", JSONIngreso.toString());

            if (jsonParser.sendJsonP(QUADRILLES_URL,JSONIngreso) == 200) {
                JSONObject jsonData = jsonParser.getjObj();

                Log.d("Respuesta", jsonData.toString());

                try {
                    JSONObject data = null;
                    JSONArray cuadrillaData = jsonData.getJSONArray("Data");
                    int Cuadrillas_size =  cuadrillaData.length();
                    for ( int i = 0; i < Cuadrillas_size ; i++) {
                        data = cuadrillaData.getJSONObject(i);
                        Quadrille quadrille = new Quadrille();
                        quadrille.setAmountDownloaded(data.getInt("Cantidad_Descargados"));
                        quadrille.setAmountFinished(data.getInt("Cantidad_Finalizados"));
                        quadrille.setAmountGenerated(data.getInt("Cantidad_Generados"));
                        quadrille.setAmountPending(data.getInt("Cantidad_Pendientes"));
                        quadrille.setIdTechnical(data.getString("IdTecnico"));
                        quadrille.setNameTechnical(data.getString("NombreTecnico"));
                        Quadrille response = quadrillesService.addQuadrille(quadrille);
                        Log.d("DespuesdeGuardar : ", response.getNameTechnical());
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                mensaje = "Mensajes enviado";
            } else {
                mensaje = "Verifique su conexion de datos";
            }


        } catch (Exception e) {
            mensaje = "Error de confirmacion de servidor";
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String valor) {
        Log.d("onPostExecute : ", mensaje);
        onLoginSuccess();
    }

    public void onLoginSuccess() {
        Intent i = new Intent(context,MainActivity.class);
        context.startActivity(i);
        ((Activity)context).finish();
    }
}