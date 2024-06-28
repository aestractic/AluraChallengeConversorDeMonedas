package Main.models;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {

    private static final List<String> conversionHistory = new ArrayList<>();
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Bienvenido al conversor de monedas");

        String menu = """
                =====Monedas $$$=====
                1 - Dólar =>> Peso Argentino
                2 - Peso Argentino =>> Dólar
                3 - Dólar =>> Real Brasileño
                4 - Real Brasileño =>> Dólar
                5 - Dólar =>> Peso Colombiano
                6 - Peso Colombiano =>> Dólar
                7 - Otras conversiones
                8 - Ver historial de conversiones
                9 - Salir
                Elije una opcion
                """;

        ClienteExchange clienteExchange = new ClienteExchange();

        int salir = 9;
        int opt;

        while (true) {
            System.out.println(menu);
            opt = sc.nextInt();

            if (opt == salir) {
                break;
            }

            String fromCurrency, toCurrency;
            double amount, result;

            switch (opt) {
                case 1:
                    convertAndRecord(clienteExchange, sc, "USD", "ARS");
                    break;
                case 2:
                    convertAndRecord(clienteExchange, sc, "ARS", "USD");
                    break;
                case 3:
                    convertAndRecord(clienteExchange, sc, "USD", "BRL");
                    break;
                case 4:
                    convertAndRecord(clienteExchange, sc, "BRL", "USD");
                    break;
                case 5:
                    convertAndRecord(clienteExchange, sc, "USD", "COP");
                    break;
                case 6:
                    convertAndRecord(clienteExchange, sc, "COP", "USD");
                    break;
                case 7:
                    customConversion(clienteExchange, sc);
                    break;
                case 8:
                    showConversionHistory();
                    break;
                default:
                    System.out.println("Opción no válida");
            }
        }

        System.out.println("Gracias por usar el conversor de monedas");
        sc.close();
    }

    private static void convertAndRecord(ClienteExchange clienteExchange, Scanner sc, String fromCurrency, String toCurrency) {
        System.out.printf("Ingrese el valor en %s que desea convertir a %s: ", fromCurrency, toCurrency);
        double amount = sc.nextDouble();
        double result = clienteExchange.convert(fromCurrency, toCurrency, amount);
        String conversionResult = String.format("%.2f %s = %.2f %s", amount, fromCurrency, result, toCurrency);
        System.out.println(conversionResult);
        recordConversion(conversionResult);
    }

    private static void customConversion(ClienteExchange clienteExchange, Scanner sc) {
        System.out.println("Ingrese la moneda de origen (ejemplo: USD):");
        String fromCurrency = sc.next().toUpperCase();
        System.out.println("Ingrese la moneda de destino (ejemplo: EUR):");
        String toCurrency = sc.next().toUpperCase();
        convertAndRecord(clienteExchange, sc, fromCurrency, toCurrency);
    }

    private static void recordConversion(String conversionResult) {
        LocalDateTime now = LocalDateTime.now();
        String record = formatter.format(now) + " - " + conversionResult;
        conversionHistory.add(record);
        if (conversionHistory.size() > 10) {
            conversionHistory.remove(0);
        }
    }

    private static void showConversionHistory() {
        System.out.println("Historial de conversiones:");
        for (String record : conversionHistory) {
            System.out.println(record);
        }
    }
}