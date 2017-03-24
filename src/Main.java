import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Main {

    public static void main(String[] args) throws IOException{

        graph test = new graph("input1.txt");

        test.display();

        Random random_gen = new Random();

        int[] test_color = new int[test.vertex];

        System.out.println("\n");

        for (int i = 0; i < test.vertex; ++i){
            test_color[i] = random_gen.nextInt(4);
            test.color[i] = test_color[i];
        }

        System.out.print("Random 4-coloring:\t");

        test.display_colors();

        System.out.println();

        System.out.println("\nConflicts:\t\t\t" + test.conflicts());

        //================================================================

        FileWriter fd = new FileWriter("output.txt");

        fd.write("\n\n");

        fd.write("\t|\t");

        for (int i = 0; i < test.vertex; ++i)
            fd.write(i + "\t");

        fd.write("\n");

        for (int i = 0; i < test.vertex + 2; ++i)
            fd.write("----");

        fd.write("\n");

        for (int i = 0; i < test.vertex; ++i) {
            fd.write(i + "\t|\t");
            for (int j = 0; j < test.vertex; ++j)
                if (test.edge[i][j] == 1)
                    fd.write(test.edge[i][j] + "\t");
                else
                    fd.write(" \t");

            fd.write("\n");
        }

        fd.flush();
        fd.close();
    }
}
