import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class KolkoKrzyzyk {

    private static final char puste = '-';
    private static final char graczCzlowiek = 'X';
    private static final char graczAI = 'O';
    private static final int rozmiar = 3;
    private static char[][] plansza;


    public static void main(String[] args) {
        plansza = new char[rozmiar][rozmiar];
        resetujPlansze();
        Scanner scanner = new Scanner(System.in);
        int wybor;

        System.out.println("Wybierz, kto ma zaczynać: ");
        System.out.println("1 - rozpoczyna sztuczna inteligencja ");
        System.out.println("2 - rozpoczyna gracz  ");
        wybor = scanner.nextInt();
        if(wybor == 1)
        {
            graj1();

        }else if(wybor == 2)
        {
            graj2();
        }else {
            System.out.println("Wpisano nieprawidłową liczbę \n ");
        }
    }


    private static void resetujPlansze() {
        for (int i = 0; i < rozmiar; i++) {
            for (int j = 0; j < rozmiar; j++) {
                plansza[i][j] = puste;
            }
        }
    }


    private static void wyswietlPlansze() {
        System.out.println("\n  1 2 3");
        for (int i = 0; i < rozmiar; i++) {
            System.out.print(i + 1 + " ");
            for (int j = 0; j < rozmiar; j++) {
                System.out.print(plansza[i][j] + " ");
            }
            System.out.println();
        }
    }


    private static char sprawdzZwyciezce() {

        for (int i = 0; i < rozmiar; i++) {
            if (plansza[i][0] == plansza[i][1] && plansza[i][1] == plansza[i][2] && plansza[i][0] != puste) {
                return plansza[i][0];
            }
        }


        for (int j = 0; j < rozmiar; j++) {
            if (plansza[0][j] == plansza[1][j] && plansza[1][j] == plansza[2][j] && plansza[0][j] != puste) {
                return plansza[0][j];
            }
        }


        if (plansza[0][0] == plansza[1][1] && plansza[1][1] == plansza[2][2] && plansza[0][0] != puste) {
            return plansza[0][0];
        }
        if (plansza[0][2] == plansza[1][1] && plansza[1][1] == plansza[2][0] && plansza[0][2] != puste) {
            return plansza[0][2];
        }


        return puste;
    }


    private static boolean czyPlanszaPelna() {
        for (int i = 0; i < rozmiar; i++) {
            for (int j = 0; j < rozmiar; j++) {
                if (plansza[i][j] == puste) {
                    return false;
                }
            }
        }
        return true;
    }


    private static void wykonajRuch(char gracz) {
        Scanner scanner = new Scanner(System.in);
        int wiersz, kolumna;

        do {
            System.out.print("Podaj numer wiersza (1-3): ");
            wiersz = scanner.nextInt() - 1;
            System.out.print("Podaj numer kolumny (1-3): ");
            kolumna = scanner.nextInt() - 1;
        } while (wiersz < 0 || wiersz >= rozmiar || kolumna < 0 || kolumna >= rozmiar || plansza[wiersz][kolumna] != puste);

        plansza[wiersz][kolumna] = gracz;
    }

    
    private static void graj1() {
        char zwyciezca = puste;

        do {
            najlepszyRuch();
            zwyciezca = sprawdzZwyciezce();
            if (zwyciezca == graczAI) {
                break;
            }
            if (czyPlanszaPelna()) {
                wyswietlPlansze();
                System.out.println("Remis!");
                return;
            }
            wyswietlPlansze();
            wykonajRuch(graczCzlowiek);
            zwyciezca = sprawdzZwyciezce();

            if (zwyciezca == puste) {
                if (czyPlanszaPelna()) {
                    wyswietlPlansze();
                    System.out.println("Remis!");
                    return;
                }
            }
        } while (zwyciezca == puste);

        wyswietlPlansze();
        System.out.println("Gracz " + zwyciezca + " przegrał!");
    }
    private static void graj2() {
        char zwyciezca = puste;

        do {

            wyswietlPlansze();
            wykonajRuch(graczCzlowiek);
            zwyciezca = sprawdzZwyciezce();
            if (zwyciezca == graczCzlowiek) {
                break;
            }

            if (czyPlanszaPelna()) {
                wyswietlPlansze();
                System.out.println("Remis!");
                return;
            }

            najlepszyRuch();
            zwyciezca = sprawdzZwyciezce();

            if (zwyciezca == puste) {
                if (czyPlanszaPelna()) {
                    wyswietlPlansze();
                    System.out.println("Remis!");
                    return;
                }
            }
        } while (zwyciezca == puste);

        wyswietlPlansze();
        System.out.println("Gracz " + zwyciezca + " przegrał!");
    }
    public static char[][] najlepszyRuch() {
        int najlepszyRuchWiersz = -1;
        int najlepszyRuchKolumna = -1;
        int najlepszyWynik = Integer.MIN_VALUE;
        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 3; i++) {
                if (plansza[i][j] == '-') {
                    plansza[i][j] = graczAI;
                    int wynik = minimax(plansza,  false);
                    plansza[i][j] = '-';
                    if (wynik > najlepszyWynik) {
                        najlepszyWynik = wynik;
                        najlepszyRuchWiersz = i;
                        najlepszyRuchKolumna = j;
                    }
                }
            }
        }
        plansza[najlepszyRuchWiersz][najlepszyRuchKolumna] = graczAI;
        return plansza;
    }

    private static int minimax(char[][] plansza,  boolean maksymalizacja) {
        char zwyciezca = sprawdzZwyciezce();

            if(zwyciezca == graczCzlowiek){
                int wynik = 1;
                return wynik;
            }else if(zwyciezca == graczAI) {
                int wynik = -1;
                return wynik;
            }else{
            if (czyPlanszaPelna()) {
                int wynik = 0;
                return wynik;
            }
        }

        List<Integer> wyniki = new ArrayList<>();

        for (int j = 0; j < rozmiar; j++) {
            for (int i = 0; i < rozmiar; i++) {
                if (plansza[i][j] == '-') {
                    if(maksymalizacja) {
                        plansza[i][j] = graczAI;
                    }else {
                        plansza[i][j] = graczCzlowiek;
                    }
                    int wynik = minimax(plansza,  !maksymalizacja);
                    wyniki.add(wynik);
                    plansza[i][j] = '-';
                }
            }
        }
        if (maksymalizacja) {
            return Collections.max(wyniki);
        } else {
            return Collections.min(wyniki);
        }
    }
}