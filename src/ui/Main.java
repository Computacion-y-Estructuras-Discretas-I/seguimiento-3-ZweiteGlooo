package ui;

import java.util.Scanner;
import structures.PilaGenerica;
import structures.TablasHash;

public class Main {

    private Scanner sc;

    public Main() {
        sc = new Scanner(System.in);
    }

    public void ejecutar() throws Exception {
        while (true) {
            System.out.println("\nSeleccione la opcion:");
            System.out.println("1. Punto 1, Verificar balanceo de expresión");
            System.out.println("2. Punto 2, Encontrar pares con suma objetivo");
            System.out.println("3. Salir del programa");
            System.out.print("Opcion: ");

            int opcion = sc.nextInt();
            sc.nextLine(); 

            switch (opcion) {
                case 1:
                    System.out.println("Ingrese expresion a verificar:");
                    String expresion = sc.nextLine();
                    boolean resultado = verificarBalanceo(expresion);
                    System.out.println("Resultado: " + (resultado ? "TRUE" : "FALSE"));
                    break;

                case 2:
                    System.out.println("Ingrese numeros separados por espacio: ");
                    String lineaNumeros = sc.nextLine();
                    System.out.println("Ingrese suma objetivo: ");
                    int objetivo = Integer.parseInt(sc.nextLine());

                    String[] partes = lineaNumeros.trim().split("\\s+");
                    int[] numeros = new int[partes.length];
                    for (int i = 0; i < partes.length; i++) {
                        numeros[i] = Integer.parseInt(partes[i]);
                    }

                    encontrarParesConSuma(numeros, objetivo);
                    break;

                case 3:
                    System.out.println("Chao");
                    sc.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Opcion no permitida");
            }
        }
    }

    /**
     * Verifica si la expresion esta balanceada usando PilaGenerica
     * @param s expresion a verificar
     * @return true si esta balanceada, false si no
     */
    public boolean verificarBalanceo(String s) {
         int len = s.length();
         char[] leftChars = {'{', '[', '('};
         char[] rightChars = {'}', ']', ')'};

         PilaGenerica<Character> pila = new PilaGenerica<>(len);

         if(len == 0) return true;
         else{
             for (int i = 0; i < len; i++) {
                 char c = s.charAt(i);

                 for (int j = 0; j < leftChars.length; j++) {
                     if (c == leftChars[j]) {
                         pila.Push(c);
                         break;
                     }
                 }

                 if(pila.isEmpty()) return false;

                 for (int j = 0; j < rightChars.length; j++) {
                     if (c == rightChars[j]) {
                         char top = pila.Pop();
                         if (top != leftChars[j]) return false;
                     }
                 }
             }

             if (!pila.isEmpty()) return false;
         }
        return true;
    }

    /**
     * Encuentra y muestra todos los pares unicos de numeros que sumen objetivo usando TablasHash.
     * @param numeros arreglo de numeros enteros
     * @param objetivo suma objetivo
     */
    public void encontrarParesConSuma(int[] numeros, int objetivo) {
        try{
            boolean found = false;
            TablasHash seen = new TablasHash(numeros.length);
            TablasHash used = new TablasHash(numeros.length);

            for (int num: numeros){
                seen.insert(num, num);
            }

            for (int num1: numeros){
                int num2 = objetivo - num1;
                if (num1!=num2 && seen.search(num2,num2) && !used.search(num1, num1) && !used.search(num2,num2)) {
                    System.out.print("(" + num1 + ", " + num2 + ")");
                    used.insert(num1, num1);
                    used.insert(num2, num2);
                    found = true;
                }
            }

            if(!found){
                System.out.println("No hay pares para " + objetivo);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) throws Exception {
        Main app = new Main();
        app.ejecutar();
    }
}
