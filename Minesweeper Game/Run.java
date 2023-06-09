import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.server.LogStream;
import java.util.Scanner;

public class Run {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String choice;

        int line, column, process = 0, flag_counter = 0, restart;
        String action;

        System.out.println("Mayin tarlasi oyununa hosgeldiniz.");

        Minesweeper_Game minesweeper_Game = new Minesweeper_Game();
        minesweeper_Game.alter_table_creater();
        minesweeper_Game.table_initializer();

        System.out.println("Eski bir save dosyaniz mevcut load etmek icin l'ye basin.");
        System.out.println();
        choice=scanner.nextLine();
        if (choice.equals("l")) {
            minesweeper_Game.GameLoader();
            minesweeper_Game.GameLoader2();
            // minesweeper_Game.GameLoader2();
            System.out.println("Oyun yüklendi.");
        }

        System.out.println("Tus takimini ogrenmek icin 1'e basin.");
        int check = scanner.nextInt();

        if (check == 1) {
            System.out.println("Tus takimi:");
            System.out.println("o = open the point");
            System.out.println("f = put a flag");
            System.out.println("r = remove a flag");
            System.out.println();
        }

        do {

            System.out.println("Oyunu kaydetmek için s'ye basin. Dikkat oyunu save ettiginiz takdirde ilerlemeniz kaybolacaktir.");
            System.out.println("Oyunu yüklemek için l'ye basin. Dikkat load ettiginiz takdirde şuanki ilerlemeniz kaybolacaktir.");
            System.out.println("Devam etmek icin c'ye basin.");

            choice = scanner.nextLine();

            if (choice.equals("s")) {
                minesweeper_Game.GameSaver();
                minesweeper_Game.GameSaver2();
                // minesweeper_Game.GameSaver2();
                System.out.println("Oyun basariyla kaydedildi.");
                continue;
            } 

            else if (choice.equals("l")) {
                minesweeper_Game.GameLoader();
                minesweeper_Game.GameLoader2();
                // minesweeper_Game.GameLoader2();
                System.out.println("Oyun yüklendi.");
            }

            minesweeper_Game.table_shower();
            System.out.println();

            System.out.println();
            System.out.println();
            System.out.println("Lutfen su formatta giriniz line,column,o veya line,column,f veya line,column,r");

            line = scanner.nextInt();
            scanner.nextLine();
            column = scanner.nextInt();
            scanner.nextLine();
            action = scanner.nextLine();

            if (action.equals("o")) {
                process = minesweeper_Game.opener(line, column);

            } else if (action.equals("f")) {
                if (minesweeper_Game.mine_count > flag_counter) {
                    flag_counter++;
                    minesweeper_Game.table[line][column] = 'f';
                    process = minesweeper_Game.verify_match_gagner();

                }

            } else if (action.equals("r")) {
                if (flag_counter > 0) {
                    flag_counter--;
                }
                minesweeper_Game.table[line][column] = '#';
            }

            if (process == minesweeper_Game.lost) {
                System.out.println("BUM!!! Oyunu kaybettiniz.");
                minesweeper_Game.table_shower();
                System.out.println("Yeniden baslamak ister misiniz? \n 1-Evet\n0-Hayir");
                restart = scanner.nextInt();
                switch (restart) {
                    case 0:
                        System.out.println("Gorusmek uzere.");
                        return;
                    case 1:
                        flag_counter = 0;
                        process = minesweeper_Game.CONTİNUE;
                        minesweeper_Game.alter_table_creater();
                        minesweeper_Game.table_initializer();
                        break;
                    default:
                        System.out.println("Yanlis deger girdiniz.");
                        break;
                }

            }
            if (process == minesweeper_Game.win) {
                System.out.println("TEBRİKLER!!! Oyunu kazandiniz.");
                minesweeper_Game.alter_table_shower();
                System.out.println("Yeniden baslamak ister misiniz? \n 1-Evet\n0-Hayir");
                restart = scanner.nextInt();
                switch (restart) {
                    case 0:
                        System.out.println("Gorusmek uzere.");
                        return;
                    case 1:
                        flag_counter = 0;
                        process = minesweeper_Game.CONTİNUE;
                        minesweeper_Game.alter_table_creater();
                        minesweeper_Game.table_initializer();
                        break;
                    default:
                        System.out.println("Yanlis deger girdiniz.");
                        break;
                }

            }

        } while (true);

    }

}
