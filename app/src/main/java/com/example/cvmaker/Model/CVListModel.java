package com.example.cvmaker.Model;


public class CVListModel {
    String pdfName,pdfSize;
    public CVListModel(String pdfName,String pdfSize){
        this.pdfName=pdfName;
        this.pdfSize=pdfSize;
    }

    public String getPdfName() {
        return pdfName;
    }

    public void setPdfName(String pdfName) {
        this.pdfName = pdfName;
    }

    public String getPdfSize() {
        return pdfSize;
    }

    public void setPdfSize(String pdfSize) {
        this.pdfSize = pdfSize;
    }
}
