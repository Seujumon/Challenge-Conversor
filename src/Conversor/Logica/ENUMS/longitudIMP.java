package Conversor.Logica.ENUMS;

/**
 * Enumera los valores de longitud según el 
 * sistema de medida anglosajón o imperial
 * tomando como parámetro de medida
 * el centímetro del Sistema Métrico Decimal
 * @author Juan Seura
 */
public enum longitudIMP {
    pulgada(2.54), 
    pie(30.48), 
    yarda(91.44), 
    milla_terrestre(160934.0), 
    milla_nautica(185200.0);
    private Double medida;

    private longitudIMP(Double medida) {
        this.medida = medida;
    }

    public Double getMedida() {
        return medida;
    }
    
    
}
