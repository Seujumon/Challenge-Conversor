package Conversor.Logica.ENUMS;

/**
 * Enumera los valores de sólidos según el
 * sistema de medida anglosajón o imperial
 * tomando como parámetro de medida
 * el gramo del Sistema Métrico Decimal
 * @author Juan Seura
 */
public enum SolidoImperial {
    
    onza(28.34952), stone(6350.29318), libra(453.59237);
    private Double peso;

    private SolidoImperial(double peso) {
        this.peso = peso;
    }

    public Double getPeso() {
        return peso;
    }
    
    
    
    
}
