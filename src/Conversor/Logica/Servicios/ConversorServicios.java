package Conversor.Logica.Servicios;

import Conversor.Logica.ENUMS.LiquidoCocina;
import Conversor.Logica.ENUMS.LiquidoSMD;
import Conversor.Logica.ENUMS.SolidoCocina;
import Conversor.Logica.ENUMS.SolidoImperial;
import Conversor.Logica.ENUMS.SolidoSMD;
import Conversor.Logica.ENUMS.longitudIMP;
import Conversor.Logica.ENUMS.longitudSMD;
import Conversor.Persistencia.Conexion;

/**
 * Posee los métodos que hacen la conexión entre la IGU y la conexión con la api 
 * externa que realiza las conversiones. 
 * Cada método Convertir... recibe los datos necesarios para enviar 
 * a la conexion, pero antes realiza las comprobaciones de que los datos sean válidos. 
 * Al finalizar, retorna un Array de String con dos posiciones. 
 * La posición 0 del Array, retorna el valor de la conversión (en caso de que no se 
 * haya realizado con éxito retorna 0). 
 * La posición 1 del Array retorna un mensaje para el usuario. En caso exitoso 
 * retorna que la conversión fue realizada, y si no un mensaje de error.
 * @author Juan Seura
 */
public class ConversorServicios {
    Conexion conexion = new Conexion();
    
    /**
     * Método que hace el pedido a la persitencia de comunicarse con la
     * api externa para convertirel monto ingresado de la divisa 1(m1) a su valor
     * en la divisa 2(m2)
     * Desde el igu se va a llamar a este método para que envíe
     * el pedido a la base de datos y devuelva el resultado
     * @param m1 moneda desde la que quiere convertir el usuario
     * @param m2 moneda a la cual quiere convertir el usuario
     * @param monto cantidad de dinero que quiere convertir
     * @return retorna un array con el monto y un mensaje para imprimir en la
     * pantalla
     */
    public String[] conversionDivisas(String m1, String m2, String monto){
        /**
         * Esto hace un subString para cortar las últimas 3 letras de la opción elegida
         * que viene por parámetro. Eso es lo que se mandaa a la api externa para que
         * sepa de qué moneda a qué moneda convertir
         * */
        String monedaUno = m1.substring(m1.length()-3, m1.length());
        String monedaDos = m2.substring(m2.length()-3, m2.length());
        String[] resultadoMensaje = new String[2];
        String resultado = "0";
        String mensaje = "Conversión realizada";
        //Llamar a comprobar monto
        
        try {
            if(comprobarMonto(monto)){
                //si el monto es correcto se comprueba que sean dos 
                //monedas diferentes. Si son iguales no es necesario
                //llamar a la api
                if(comprobarTiposDiferentes(monedaUno, monedaDos)){
                resultado = monto;
                //si son dos monedas diferentes se llama a la api. Se guarda el
                //resultado en un string para devolverlo en el array
                } else{
                resultado = conexion.conectar(monedaUno, monedaDos, monto);
                resultado= resultado.replace(".", ",");
                }
                //Si comprobarMonto no puede parsear el monto quiere decir
                //que no se ingresó un valor numérico y coloca el mensaje 
                //para mostrar por pantalla
            } else{
            mensaje="El conversor únicamente admite montos numéricos enteros."
                    + "\nNo admite letras ni caracteres especiales";
            }
            
        } catch (Exception e) {
            //si algún método arroja una excepción se captura, y el mensaje
            //para el usuario retornará dicho error
            mensaje = "Hubo un problema al momento de "
                    + "realizar la operación. "
                    + "\nInténtelo nuevamente. Si el error persiste, puede deberse"
                    + "a errores de conexión momentáneos. ";
            resultado = "0";
        } 
        //coloco el resultado y el mensaje en el array para devolverlo a la IGU
        resultadoMensaje[0]=resultado;
        resultadoMensaje[1]=mensaje;
        return resultadoMensaje;

    }
    /**
     * Comprueba que el monto sea un numero Double
     * @param monto ingresado al usuario. Para hacer la comprobación se realiza un
     * try catch que parsea el resultado. Si no se puede tira una excepción que 
     * retorna un false
     * @return true si es un valor numérico, false si no lo es o es 0 o nulo
     */
    public boolean comprobarMonto(String monto){
        try{
            Double num = Double.parseDouble(monto);
            return true;
        }catch(Exception e){
            return false;
        }
    }
    /**
     * Comprueba que las dos monedas ingresadas sean distintas, para no hacer
     * una petición de datos innecesaria. Si son iguales devuelve true. 
     * En el otro método, el return va a ser igual al monto que ingresó el usuario
     * @param m1 moneda desde la cual cambiar
     * @param m2 moneda a la cual cambiar
     * @return true si son iguales, false si no
     */
    public boolean comprobarTiposDiferentes(String m1, String m2){
        if(m1.equals(m2)){
            return true;
        } 
        return false;
    }
    /**
     * 
     * Conversor de sólidos. Recibe la unidad de medida y el sistema del cual se 
     * quiere convertir, la unidad de medida y el sistema al cual se quiere convertir
     * y la cantidad a convertir. Llama a los respectivos métodos para comprobar
     * los valores que enviaron desde la IGU y realiza las operaciones para 
     * devolver el resultado que corresponda y un mensaje, de éxito o de error. 
     * 
     * @param s1 Sistema de medida del que se quiere convertir
     * @param s2 Sistema de medida al cual se quiere convertir
     * @param uM1 Unidad de medida del que se quiere convertir
     * @param uM2 Unidad de medida al que se quiere convertir
     * @param cantidad Cantidad que se quiere convertir
     */
    public String[] convertirSolidos(String s1, String s2, String uM1, String uM2, String cantidad){
        String[] retorno = new String[2];
        String valorConvertido = "0";
        String mensaje = "Conversión Exitosa";
        
         
        try {
            //Comprueba que sea parseable
            if(comprobarMonto(cantidad)){
                //Comprobar que uM1 y uM2 no sean iguales
                if(comprobarTiposDiferentes(uM1, uM2)){
                    valorConvertido = cantidad;
                } else{
                //Averiguar qué enum es cada uno 
                //Crear dos variables Double que guarden el volumen de cada tipo 
                Double valorA = obtenerPesoSolido(s1, uM1);
                Double valorB = obtenerPesoSolido(s2, uM2);
                Double monto = Double.parseDouble(cantidad);
                //Hacer la cuenta 
                Double resultado = (valorA*monto)/valorB;
                valorConvertido = resultado.toString();
                }
                //Si el número no es parseable arroja un error
            }  else{
            mensaje="El conversor únicamente admite montos numéricos enteros."
                    + "\nNo admite letras ni caracteres especiales";}
            //Si hay un error en el programa devuelve este mensaje de error
        } catch (Exception e) {
            mensaje = "Hubo un problema al momento de "
                    + "realizar la operación. "
                    + "\nInténtelo nuevamente. Si el error persiste, puede deberse"
                    + "a errores de conexión momentáneos. ";
            valorConvertido = "0";
        }
        //retornar 
        retorno[0]= valorConvertido;
        retorno[1] = mensaje;
        return retorno;
    }
    /**
     * Busca El sistema a través de un switch y consulta al respectivo ENUM 
     * mediante el método getPeso() el valor del peso en gramos de la unidad de 
     * medida consultada 
     * @param sistema Sistema de Conversión 
     * @param unidadMedida Unidad de Medida consultada
     * @return retorna el valor de la unidad de medida acorde a su sistema
     */
    public Double obtenerPesoSolido(String sistema, String unidadMedida){
        Double peso = 0.0;
        switch(sistema){
            case "SMD":
                SolidoSMD s = SolidoSMD.valueOf(unidadMedida);
                peso = s.getPeso();
                break;
            case "COC":
                SolidoCocina c = SolidoCocina.valueOf(unidadMedida);
                peso = c.getPeso();
                break;
            case "IMP":
                SolidoImperial i = SolidoImperial.valueOf(unidadMedida);
                peso = i.getPeso();
                break;
        }
        
        return peso;
    }
    
    /**
     * Conversor de Líquidos. Recibe la unidad de medida y el sistema del cual se 
     * quiere convertir, la unidad de medida y el sistema al cual se quiere convertir
     * y la cantidad a convertir. Llama a los respectivos métodos para comprobar
     * los valores que enviaron desde la IGU y realiza las operaciones para 
     * devolver el resultado que corresponda y un mensaje, de éxito o de error. 
     * @param s1 Sistema del cual se quiere convertir
     * @param s2 Sistema al cual se quiere convertir
     * @param uM1 unidad de medida desde la cual se quiere convertir
     * @param uM2 unidad de medida a la cual se quiere convertir
     * @param cantidad cantidad que se quire convertir
     * @return retorna un Array con un valor y un mensaje para mostrar en la IGU
     * Si el resultado es correcto devuelve un mensaje de Operación exitosa y el
     * valor de la operación, y en caso contrario devuelve un mensaje de ERROR 
     * y el valor de la conversión 0
     */
    public String[] convertirLiquidos(String s1, String s2, String uM1, String uM2, String cantidad){
        String[] retorno = new String[2];
        String valorConvertido = "0";
        String mensaje = "Conversión Exitosa";
        
        try {
            if(comprobarMonto(cantidad)){
                //Comprobar que uM1 y uM2 no sean iguales 
                if(comprobarTiposDiferentes(uM1, uM2)){
                    valorConvertido = cantidad;
                } else{
                    
                //Averiguar qué enum es cada uno 
                //Crear dos variables Double que guarden el volumen de vada tipo 
                Double valorA = obtenerVolumenLiquido(s1, uM1);
                Double valorB = obtenerVolumenLiquido(s2, uM2);
                Double monto = Double.parseDouble(cantidad);
                //Hace la cuenta 
                Double resultado = (valorA*monto)/valorB;
                valorConvertido = resultado.toString();
                    
                }
                //Si el valor ingresado en la IGU no es un número devuelve el mensaje
            }  else{
            mensaje="El conversor únicamente admite montos numéricos enteros."
                    + "\nNo admite letras ni caracteres especiales";}
        } catch (Exception e) {
            mensaje = "Hubo un problema al momento de "
                    + "realizar la operación. "
                    + "\nInténtelo nuevamente. Si el error persiste, puede deberse"
                    + "a errores de conexión momentáneos. ";
            valorConvertido = "0";
        }
         //retornar 
        retorno[0]= valorConvertido;
        retorno[1] = mensaje;
        return retorno;
    }
    /**
     * Busca El sistema a través de un switch y consulta al respectivo ENUM 
     * mediante el método getVolumen() el valor del volumen en mililitros 
     * de la unidad de medida consultada 
     * @param sistema Sistema de Conversión
     * @param unidadMedida Unidad de medida de conversión
     * @return retorna el valor según el sistema y la unidad de medida ingresados
     */
    private Double obtenerVolumenLiquido(String sistema, String unidadMedida){
        Double volumen = 0.0;
        switch(sistema){
            case "SMD":
                LiquidoSMD s = LiquidoSMD.valueOf(unidadMedida);
                volumen = s.getVolumen();
                break;
            case "COC":
                LiquidoCocina c = LiquidoCocina.valueOf(unidadMedida);
                volumen = c.getVolumen();
                break;
        }
        
        return volumen;
    }
    /**
     * Conversor de Longitud. 
     * Recibe la unidad de medida y el sistema del cual se 
     * quiere convertir, la unidad de medida y el sistema al cual se quiere convertir
     * y la cantidad a convertir. Llama a los respectivos métodos para comprobar
     * los valores que enviaron desde la IGU y realiza las operaciones para 
     * devolver el resultado que corresponda y un mensaje, de éxito o de error. 
     * @param s1 Sistema del cual se quiere convertir
     * @param s2 Sistema al cual se quiere convertir
     * @param uM1 unidad de medida desde la cual se quiere convertir
     * @param uM2 unidad de medida a la cual se quiere convertir
     * @param cantidad cantidad que se quire convertir
     * @return retorna un Array con un valor y un mensaje para mostrar en la IGU
     * Si el resultado es correcto devuelve un mensaje de Operación exitosa y el
     * valor de la operación, y en caso contrario devuelve un mensaje de ERROR 
     * y el valor de la conversión 0
     */
    public String[] convertirLongitud(String s1, String s2, String uM1, String uM2, String cantidad){
        
        String[] retorno = new String[2];
        String valorConvertido = "0";
        String mensaje = "Conversión Exitosa";
        
        try {
            //comprueba que la cantidad a convertir sea un número
            if(comprobarMonto(cantidad)){
                //Comprobar que uM1 y uM2 no sean iguales 
                if(comprobarTiposDiferentes(uM1, uM2)){
                    valorConvertido = cantidad;
                } else{
                    
                //Averiguar qué enum es cada uno 
                //Crear dos variables Double que guarden el volumen de vada tipo 
                Double valorA = obtenerLongitud(s1, uM1);
                Double valorB = obtenerLongitud(s2, uM2);
                Double monto = Double.parseDouble(cantidad);
                //Hacer la cuenta 
                Double resultado = (valorA*monto)/valorB;
                valorConvertido = resultado.toString();
                    
                }
                //si la cantidad no es un número devuelve este mensaje de error
            }  else{
            mensaje="El conversor únicamente admite montos numéricos enteros."
                    + "\nNo admite letras ni caracteres especiales";}
        } catch (Exception e) {
            mensaje = "Hubo un problema al momento de "
                    + "realizar la operación. "
                    + "\nInténtelo nuevamente. Si el error persiste, puede deberse"
                    + "a errores de conexión momentáneos. ";
            valorConvertido = "0";
        }
         //retornar 
        retorno[0]= valorConvertido;
        retorno[1] = mensaje;
        return retorno;
    }
    /**
     * Busca El sistema a través de un switch y consulta al respectivo ENUM 
     * mediante el método getMedida() el valor de la medida en centímetros
     * de la unidad de medida consultada 
     * @param sistema Sistema de Conversión
     * @param unidadMedida Unidad de medida de conversión
     * @return retorna el valor según el sistema y la unidad de medida ingresados
     */
    
    private Double obtenerLongitud(String sistema, String unidadMedida){
        Double volumen = 0.0;
        switch(sistema){
            case "SMD":
                longitudSMD s = longitudSMD.valueOf(unidadMedida);
                volumen = s.getMedida();
                break;
            case "IMP":
                longitudIMP c = longitudIMP.valueOf(unidadMedida);
                volumen = c.getMedida();
                break;
        }
        
        return volumen;
    }

}
