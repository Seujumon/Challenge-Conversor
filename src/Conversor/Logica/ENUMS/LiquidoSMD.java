package Conversor.Logica.ENUMS;

/**
 * Enumera los valores de líquidos según el Sistema métrico decimal 
 * tomando como parámetro de medida
 * el mililitro del Sistema Métrico Decimal
 * @author Juan Seura
 */
public enum LiquidoSMD {
    litro(1000.0), mililitro(1.0);
    private Double volumen;

    private LiquidoSMD(Double volumen) {
        this.volumen = volumen;
    }

    public Double getVolumen() {
        return volumen;
    }
    
    
    
}
