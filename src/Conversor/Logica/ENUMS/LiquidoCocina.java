package Conversor.Logica.ENUMS;

/**
 * Enumera los valores de líquidos  según las convenciones 
 * de cocina tomando como parámetro de medida
 * el mililitro del Sistema Métrico Decimal
 * @author Juan Seura
 */
public enum LiquidoCocina {
    taza(228.57), cucharada(14.28), cucharadita(5.0);
    private Double volumen;

    private LiquidoCocina(Double volumen) {
        this.volumen = volumen;
    }

    public Double getVolumen() {
        return volumen;
    }
    
}
