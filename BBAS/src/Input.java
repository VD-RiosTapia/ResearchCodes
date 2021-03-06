
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Input {

	protected static int cantidadObjetos;
	protected static int beneficiosObjetos[];
	protected static int cantidadMochilas;
	protected static int matrizPesos[][];
	protected static int capacidadesMochilas[];
	protected static float optimo;
	
	protected static int contadorLineas = 0;
	
    public static List<String> read(String fileName) {
        
    	int i = 0;
    	int j = 0;
    	
    	File f;
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        List<String> lines = null;

        try {
            f = new File(fileName);
            lines = new ArrayList<String>();
            String line;
            fileReader = new FileReader(f);
            bufferedReader = new BufferedReader(fileReader);
            while ((line = bufferedReader.readLine()) != null) {
            	
            	//INICIO CODIGO AÑADIDO
            	contadorLineas++;
            			
    			if(contadorLineas == 1){
    				Problem.objectQuantity = Integer.parseInt(line);
    				//System.out.println(Problem.objectQuantity);
    				Problem.prices = new double[Problem.objectQuantity]; 
    				//System.out.println("==========================================");
    			}
    			
    			if(contadorLineas == 2){
    				String cadena[] = line.split(",");
    				for(i = 0; i < cadena.length; i++) {
    					Problem.prices[i] = Double.parseDouble(cadena[i]);
    					//System.out.println(Problem.prices[i]);
    				}
    				i = 0;
    				//System.out.println("==========================================");
    			}
    			
    			if(contadorLineas == 3){
    				Problem.m = Integer.parseInt(line);
    				//System.out.println(Problem.m);
    				Problem.weights = new double[Problem.m][Problem.objectQuantity];
    				//System.out.println("==========================================");
    			}
    			
    			if(contadorLineas >= 4 && i < Problem.m){
    				String cadena[] = line.split(",");
    				for(j = 0; j < Problem.objectQuantity; j++) {
    					Problem.weights[i][j] = Double.parseDouble(cadena[j]);
    					//System.out.println(Problem.weights[i][j]);
    				}
    				i++;
    				//System.out.println("==========================================");
    			}
    			
    			if(contadorLineas == Problem.m + 4){
    				String cadena[] = line.split(",");
    				Problem.b = new double[Problem.m];
    				for(i = 0; i < cadena.length; i++) {
    					Problem.b[i] = Double.parseDouble(cadena[i]);
    					//System.out.println(Problem.b[i]);
    				}
    				//System.out.println("==========================================");
    			}
    			
    			if(contadorLineas == Problem.m + 5){
    				String cadena = line+"f";
    				Problem.optimal = Float.parseFloat(cadena);
    				//System.out.println(Problem.optimal);
    				//System.out.println("==========================================");
    			}
            	
            }
            
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            try {
                if (fileReader != null) {
                    fileReader.close();
                    bufferedReader.close();
                }
            } catch (IOException e) {
                System.out.println(e);
            }
        }
        return lines;
    }
    
}
