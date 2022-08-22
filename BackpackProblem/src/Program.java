/*
     napad na jubilera:
     zadanie:
     załadowanie plecaka (rozmiar podajemy na wstepie) przedmiotami
     o najmniejszej wadze i jednoczesnie najwiekszej wartosci

     tablice:
     size - pojemnosc plecaka, maksymalna waga
     n - liczba dostepnych rzeczy ktore mozna wybrać
     weights[] - tablica z wagami przedmioitow
     values[] - tablica z wartosciami przedmiotow
     id[] - tablica z numerami id




*/
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

class Plecak {

    public Plecak(int size, int n, int[] weights, int[] values, int[] id) {

        // Tworzymy tablice-tabele
        int[][] array = new int[n + 1][size + 1];

        // Tablica do przechowywania produktów, pasujących do plecaka
        int[][] produkty = new int[n + 1][size + 1];

        for (int i = 0; i <= size; i++) {
            array[0][i] = 0;
        }

        for (int i = 1; i < n; i++) {
            for (int j = 0; j <= size; j++) {
                int item;
                if (weights[i] <= j) {
                    item = array[i - 1][j - weights[i]];
                } else item = 0;

                if ((weights[i] <= j) && (values[i] + item) > array[i - 1][j]) {
                    array[i][j] = item + values[i];
                    produkty[i][j] = 1;
                } else {
                    array[i][j] = array[i - 1][j];
                    produkty[i][j] = 0;
                }
            }
        }

        // Lista z produktami, które należy włożyć do plecaka
        ArrayList<Integer> zawartoscPlecaka = new ArrayList<>();
        int tW = size;
        for (int i = n; i >= 0; i--) {
            if (produkty[i][tW] == 1) {
                tW -= weights[i];
                zawartoscPlecaka.add(i);
            }
        }

        // Sortowanie listy
        Collections.sort(zawartoscPlecaka);

        // Wyświetlenie rozwiązania
        System.out.println("\nWybrane przedmioty:");
        int totalWeight = 0;
        int totalValue = 0;
        for (int i : zawartoscPlecaka) {
            totalWeight += weights[i];
            totalValue += values[i];
            System.out.println("Produkt: " + id[i]);
        }
        if (totalWeight==0 && totalValue==0){
            System.out.println("Niczego nie udało Ci się ukraść!");
        }
        else {
            System.out.print("\n>>> Ukradłeś " + totalWeight + " gramy biżuterii");
            System.out.println(" o łącznej wartości: " + " " + totalValue + " $$$ <<< ");
        }

    }
}

public class Program {

    protected Plecak plecak;


    /* Konstruktor wczytuje dane z pliku "dane.csv" i zapisuje je do ArrayLists,
       a następnie zamienia je w tablice */
    public Program() {

        // Plik do wczytania
        String plik = "dane.csv";
        BufferedReader fileReader = null;

        final String SEPARATOR = ";";
        String line = "";

        ArrayList<Integer> listIds = new ArrayList<>();
        ArrayList<Integer> listWeights = new ArrayList<>();
        ArrayList<Integer> listValues = new ArrayList<>();

        try {

            // Tworzenie File Reader
            fileReader = new BufferedReader(new FileReader(plik));

            // Pomijamy pierwszy wiersz
            fileReader.readLine();

            // Czyta po kolei linie z pliku
            while ((line = fileReader.readLine()) != null) {

                // Wczytuje dane rozdzielone separatorem
                String[] dane = line.split(SEPARATOR);

                int id = Integer.parseInt(dane[0]);
                int weight = Integer.parseInt(dane[1]);
                int value = Integer.parseInt(dane[2]);

                // Dodaje dane do list
                listIds.add(id);
                listWeights.add(weight);
                listValues.add(value);

            }
        } catch (FileNotFoundException e) {
            System.out.println("Nie można odczytać pliku");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // Zamyka plik
                if (fileReader != null) fileReader.close();
            } catch (IOException e) {
                System.out.println("Nie udało się zamknąć pliku");
            }
        }

        // Konwersja ArrayLists do tablic
        int[] id = new int[listIds.size()];
        for (int i = 0; i < listIds.size(); i++) {
            id[i] = listIds.get(i);
        }

        int[] weights = new int[listWeights.size()];
        for (int i = 0; i < listWeights.size(); i++) {
            weights[i] = listWeights.get(i);
        }

        int[] values = new int[listValues.size()];
        for (int i = 0; i < listValues.size(); i++) {
            values[i] = listValues.get(i);
        }

        int n = id.length;

        System.out.println("Dane zostały wczytane");


        // MENU
        Scanner in = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n*** MENU ***");
            System.out.println("Wybierz opcję:");
            System.out.println("1 - Problem plecakowy");
            System.out.println("2 - Zakończ");

            choice = in.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Podaj rozmiar plecaka: ");
                    int size = in.nextInt();

                    plecak = new Plecak(size, n, weights, values, id);
                    break;
                case 2:
                    System.exit(0);
                default:
                    System.out.println("Wybierz prawidłową opcję: ");
                    break;
            }
        } while (true);
    }


    public static void main(String[] args) {

        Program obj = new Program();

    }
}
