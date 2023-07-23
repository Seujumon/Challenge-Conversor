
package Conversor.Logica;

import Conversor.IGU.Principal;
/**
 * Conversor de Divisas, pesos y medidas: 
 * Proyecto realizado para la capacitación BackEnd en Java de 
 * Oracle en su programa ONE junto a Alura Latam. 
 * Consiste en un conversor de divisas, usando una api externa para 
 * obtener los datos actualizados del mercado, y un conjunto de ENUM 
 * para manejar los valores de las constantes de las demás conversiones 
 * requeridas. 
 * Para la interface gráfica utilicé Swing, y el modelo de paquetes y 
 * clases corresponde con un Diagrama de capas dividiendo La Lógica(almacena 
 * las clases que realizan las operaciones), la Interface Gráfica de Usuario 
 * (contiene los JFrame que observa el/la usuario/a) y la Persistencia(realiza 
 * la conexión con la base externa y la consulta de los datos pedidos desde 
 * la lógica). 
 *
 @author Juan Seura
 * GITHUB @Seujumon
 * Linkedin @JuanSeura
 */
public class Conversor {
    
    public static void main(String[] args) {
        /**Inicio de la Interface Gráfica
         * Crea una instancia de la gráfica principal, la coloca visible,
         * y la centra
         */
        Principal principal = new Principal();
        principal.setVisible(true);
        principal.setLocationRelativeTo(null);
    }

}