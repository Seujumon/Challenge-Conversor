package Conversor.Persistencia;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONObject;

/**
 * Clase que contiene los métodos para realizar la conexión con la api externa
 * para poder realizar la conversión de divisas tomando los valores actuales del
 * mercado. 
  @author Juan Seura
 */
public class Conexion {
    //Creación de variables que realizan la conección 
    private URL url;
    HttpURLConnection conectar; 
    
    /**
     * Método para crear la conección
     * @param url Recibe la url a la cual conectarse
     * @throws Exception Puede arrojar una excepción si no se realiza la conección
     * exitósamente para que sea capturada por el método del servicio y enviar
     * desde allí un mensaje a la IGU. 
     */
    public void crearConexion(URL url) throws Exception{
        try {
            
            this.conectar = (HttpURLConnection)url.openConnection();
            this.conectar.setRequestMethod("GET");
            this.conectar.connect();
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }
    }
    /**
     * Método para crear la URL de la consulta
     * @param m1 moneda de la cual se quiere convertir
     * @param m2 moneda a la cual se quiere convertir
     * @param monto a convertir
     * @throws MalformedURLException Puede arrojar una excepción que 
     * será capturada por el Servicio para que envíe un mensaje de error 
     * que pueda ser mostrado por la IGU
     */
    public void crearURL(String m1, String m2, String monto) throws MalformedURLException{
        
        try {
            //La parte final de esta URL es la clave proporcionada por la api para realizar consultas gratuitas
            this.url = new URL("https://api.apilayer.com/exchangerates_data/convert?to=" + m2 + "&from="+ m1 +"&amount="+ monto + "&apikey=pQDzSwCQZAT6ITX7PzAujsNNG0W4rJC0");

        } catch (MalformedURLException ex) {
            System.out.println(ex);
            throw ex;

        }
    }
    /**
     * Este método es la conexión entre el servicio y los demás métodos de ésta 
     * clase. Recibe los datos necesarios y devuelve un String con el 
     * resultado de la conversión procesado
     * @param m1 moneda de la que se quiere convertir
     * @param m2 moneda a la cual se quiere convertir
     * @param monto cantidad de dinero que se quiere convertir
     * @return retorna el monto que devolvió la api externa. 
     */
    public String conectar(String m1, String m2, String monto) throws Exception{
        try {
            crearURL(m1, m2, monto);
            crearConexion(url);
            int codigo = this.conectar.getResponseCode();
            if(codigo!=200){
                throw new Exception("ERROR. Código = " + codigo);
            }
            ////Abrir un scanner que lea los datos 
            //creo un string builder
            StringBuilder cadenaDeInformacion = new StringBuilder();
            //creo un scanner que recibe los datos de la url que está abierta. 
            //este no es un receptor del teclado 
            //El Scaner va a guardar el Stream que devuelve la api
            Scanner scan = new Scanner(url.openStream());
            //El while se fija como si fuera un iterator si hay una nueva linea
            //si hay, el StringBuilder recibe la información de la próxima
            //linea del scanner 
            while(scan.hasNext()){
                cadenaDeInformacion.append(scan.nextLine());
            }
            //se cierra el scanner
            scan.close();
            //Crea un objeto con los datos recibidos del JSON que fueron
            //guardados en el StringBuilder
            JSONObject objeto = new JSONObject(cadenaDeInformacion.toString());
            //extraigo de ese objeto únicamente el valor que corresponde con
            //el resultado de la operación, que se guarda en un atributo
            //de tipo Double llamado result
            Double v = objeto.getDouble("result");
            //convierto el resultado en String
            String valor = v.toString();
            //retorna únicamente el resultado de la conversion
            return valor;
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
            throw e;
        }
    }
}
