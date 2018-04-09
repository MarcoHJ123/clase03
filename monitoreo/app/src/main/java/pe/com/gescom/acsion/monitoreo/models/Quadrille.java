package pe.com.gescom.acsion.monitoreo.models;

import com.orm.SugarRecord;

/**
 * Created by gescom on 16/11/2016.
 */

public class Quadrille extends SugarRecord {
    private  int amountDownloaded;
    private  int amountFinished;
    private  int amountGenerated;
    private  int amountPending;
    private  String idTechnical;
    private  String nameTechnical;
    private  String nameState;
    private  int valueState;
    private  int idState;

    public Quadrille(){

    }


    public int getAmountDownloaded() {
        return amountDownloaded;
    }

    public void setAmountDownloaded(int amountDownloaded) {
        this.amountDownloaded = amountDownloaded;
    }

    public int getAmountFinished() {
        return amountFinished;
    }

    public void setAmountFinished(int amountFinished) {
        this.amountFinished = amountFinished;
    }

    public int getAmountGenerated() {
        return amountGenerated;
    }

    public void setAmountGenerated(int amountGenerated) {
        this.amountGenerated = amountGenerated;
    }

    public int getAmountPending() {
        return amountPending;
    }

    public void setAmountPending(int amountPending) {
        this.amountPending = amountPending;
    }

    public String getIdTechnical() {
        return idTechnical;
    }

    public void setIdTechnical(String idTechnical) {
        this.idTechnical = idTechnical;
    }

    public String getNameTechnical() {
        return nameTechnical;
    }

    public void setNameTechnical(String nameTechnical) {
        this.nameTechnical = nameTechnical;
    }

    public String getNameState() {
        return nameState;
    }

    public void setNameState(String nameState) {
        this.nameState = nameState;
    }

    public int getValueState() {
        return valueState;
    }

    public void setValueState(int valueState) {
        this.valueState = valueState;
    }

    public int getIdState() {
        return idState;
    }

    public void setIdState(int idState) {
        this.idState = idState;
    }
}
