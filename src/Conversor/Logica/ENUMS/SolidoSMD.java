package Conversor.Logica.ENUMS;

/**
 * Enumera los valores de sólidos según el
 * sistema métrico decimal
 * tomando como parámetro de medida
 * el gramo del Sistema Métrico Decimal
 * @author Juan Seura
 */
public enum SolidoSMD {
    gramo(1), kilogramo(1000);
    
    private Double peso;

    private SolidoSMD(double peso) {
        this.peso = peso;
    }

    public Double getPeso() {
        return peso;
    }
    
    
    
    
    
    
}
