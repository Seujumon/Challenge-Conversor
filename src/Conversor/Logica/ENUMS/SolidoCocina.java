
package Conversor.Logica.ENUMS;

/**
 * Enumera los valores de sólidos según las convenciones 
 * de cocina
 * tomando como parámetro de medida
 * el gramo del Sistema Métrico Decimal
 * @author Juan Seura
 */
public enum SolidoCocina {
    taza(128), cucharada(8), cucharadita((double)8/3);
    private Double peso;

    private SolidoCocina(double peso) {
        this.peso = peso;
    }

    public Double getPeso() {
        return peso;
    }
    
    
    
}
