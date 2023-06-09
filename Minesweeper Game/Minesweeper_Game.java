import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

public class Minesweeper_Game {

    public static int table_cut;
    public static int mine_count;
    public static int win;
    public static int lost;
    public static int CONTİNUE;
    public static int[][] alter_table;
    public static char[][] table;
    public static int flag_counter = 0;

    public Minesweeper_Game() {
        table_cut = 8;
        mine_count = 2;
        win = 1;
        lost = -1;
        CONTİNUE = 0;
        alter_table = new int[table_cut + 2][table_cut + 2];
        table = new char[table_cut + 2][table_cut + 2];
    }

    public static void GameSaver() {

        FileWriter writer = null;

        try {
            writer = new FileWriter("saveFile.txt");
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            System.out.println("dosya acma hatasi olsutu.");
            e1.printStackTrace();
        }

        for (int i = 1; i <= table_cut; i++) {

            for (int j = 1; j <= table_cut ; j++) {
                try {
                    String s = Integer.toString(alter_table[i][j]);
                    writer.write(s);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    System.out.println("Yazma hatasi olustu.");
                    e.printStackTrace();

                }
            }

        }

        try {
            writer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println("Dosya kapama hatasi olustu.");
            e.printStackTrace();
        }
    }

    /* */
    public static void GameSaver2() {

        FileWriter writer = null;

        try {
            writer = new FileWriter("saveFile2.txt");
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            System.out.println("dosya acma hatasi olsutu.");
            e1.printStackTrace();
        }

        for (int i = 1; i <= table_cut; i++) {

            for (int j = 1; j <= table_cut ; j++) {
                try {
                    char s = table[i][j];
                    writer.write(s);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    System.out.println("Yazma hatasi olustu.");
                    e.printStackTrace();

                }
            }

        }

    try {
        writer.close();
    } catch (IOException e) {
        // TODO Auto-generated catch block
        System.out.println("Dosya kapama hatasi olustu.");
        e.printStackTrace();
    }
}

    public static void GameLoader() throws IOException {

        BufferedReader r = new BufferedReader(new FileReader("saveFile.txt"));
        int ch;
        int sayac=0;
        String toplam="";

        for (int i = 1; i <= table_cut ; i++) {

            for (int j = 1; j <= table_cut ; j++) {
                try {
                    ch = r.read();
                    int n = Character.getNumericValue(ch);
                    if(n==-1)
                    {
                        ch = r.read();
                    }
                    alter_table[i][j] = n;
                    sayac++;
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    System.out.println("Yazma hatasi olustu.");
                    e.printStackTrace();

                }
            }

        }
    }

    
    public static void GameLoader2() throws IOException {

        BufferedReader r = new BufferedReader(new FileReader("saveFile2.txt"));
        int ch;

        for (int i = 1; i <= table_cut ; i++) {

            for (int j = 1; j <= table_cut ; j++) {
                try {
                    ch = r.read();
                    char n=(char)ch;
                    table[i][j] = n;
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    System.out.println("Yazma hatasi olustu.");
                    e.printStackTrace();

                }
            }

        }

    }

    public static void alter_table_creater() {

        for (int i = 0; i <= table_cut + 1; i++) {
            for (int j = 0; j <= table_cut + 1; j++) {
                alter_table[i][j] = 0;
            }
        }

        int j = 0;
        for (int i = 0; i <= table_cut + 1; i++) {
            alter_table[i][j] = 1;
            alter_table[j][i] = 1;
            alter_table[table_cut + 1][i] = 1;
            alter_table[i][table_cut + 1] = 1;
        }

        mine_placer();

    };

    public static void table_initializer() {

        for (int i = 0; i <= table_cut + 1; i++) {
            for (int j = 0; j <= table_cut + 1; j++) {
                table[i][j] = '#';
            }
        }

        for (int i = 0; i <= table_cut + 1; i++) {
            table[i][0] = '*';
            table[i][table_cut + 1] = '*';
        }

        for (int j = 0; j <= table_cut + 1; j++) {
            table[0][j] = '*';
            table[table_cut + 1][j] = '*';
        }

    };

    public static void alter_table_shower() {
        for (int i = 1; i <= table_cut; i++) {
            System.out.print("   " + i);
        }
        System.out.println();

        for (int i = 1; i <= table_cut; i++) {
            for (int j = 1; j <= table_cut; j++) {
                System.out.print("   " + alter_table[i][j]);
            }
            System.out.println("  |" + (i));

        }

        for (int i = 0; i < table_cut; i++) {
            System.out.print("----");
        }
    };

    public static void table_shower() {

        for (int i = 1; i <= table_cut; i++) {
            System.out.print("   " + i);
        }
        System.out.println();

        for (int i = 1; i <= table_cut; i++) {
            for (int j = 1; j <= table_cut; j++) {
                System.out.print("   " + table[i][j]);
            }
            System.out.println("  |" + (i));

        }

        for (int i = 0; i < table_cut; i++) {
            System.out.print("----");
        }

    };

    public static void mine_placer() {
        int i, j, column, line, k, p;

        for (i = 1; i <= mine_count; i++) {
            line = (int) (Math.random() * 8 + 1);
            column = (int) (Math.random() * 8 + 1);
            if (alter_table[line][column] == -1)
                i--;
            alter_table[line][column] = -1;

            for (k = -1; k <= 1; k++) {
                for (p = -1; p <= 1; p++) {
                    if (alter_table[line][column] == -1) {
                        if (alter_table[line + k][column + p] != -1) {
                            alter_table[line + k][column + p]++;
                        }
                    }
                }

            }
        }

    };

    public static int verify_match_gagner() {

        int i, j;
        int status = CONTİNUE;
        flag_counter = 0;
        for (i = 1; i <= table_cut; i++) {
            for (j = 1; j <= table_cut; j++) {
                if (table[i][j] == 'f' && alter_table[i][j] == -1) {

                    flag_counter++;
                    System.out.println(flag_counter);

                }

            }
        }

        if (flag_counter == mine_count) {
            status = 1;
        }

        return status;
    };

    public static void explorer(int line, int column) {
        int i = 0, j = 0;

        table[line + i][column + j] = (char) (alter_table[line + i][column + j] + '0');

        for (i = -1; i <= 1; i++) {
            for (j = -1; j <= 1; j++) {
                if (alter_table[line + i][column + j] > 0 && table[line + i][column + j] == '#') {
                    table[line + i][column + j] = (char) (alter_table[line + i][column + j] + '0');
                } else if (alter_table[line + i][column + j] == 0 && table[line + i][column + j] == '#') {
                    explorer(line + i, column + j);
                }
            }
        }

    };

    public static int opener(int line, int column) {

        int x = 0;
        if (alter_table[line][column] == -1) {
            x = lost;
        } else if (alter_table[line][column] > 0) {

            table[line][column] = (char) (alter_table[line][column] + '0');

            x = verify_match_gagner();
            x = CONTİNUE;
        } else {
            explorer(line, column);
        }
        return x;
    };

}
