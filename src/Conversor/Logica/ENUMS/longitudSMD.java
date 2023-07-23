package Conversor.Logica.ENUMS;

/**
 * Enumera los valores de longitud según el 
 * sistema métrico decimal
 * tomando como parámetro de medida
 * el centímetro del Sistema Métrico Decimal
 * @author Juan Seura
 */
public enum longitudSMD {
    centimetro(1.0), metro(100.0), kilometro(100000.0);
    private Double medida;

    private longitudSMD(Double medida) {
        this.medida = medida;
    }

    public Double getMedida() {
        return medida;
    }
    
    
}
