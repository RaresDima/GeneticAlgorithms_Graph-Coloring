import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class graph {

    static final int default_vertex = 100;

    int     vertex;
    int[][] edge;
    int[]   color;

    graph()                                                                    {
        this.vertex = default_vertex;
        this.edge   = new int[this.vertex][this.vertex];
        this.color  = new int[this.vertex];

        for (int i = 0; i < this.vertex; ++i) {
            this.color[i] = 0;
            for (int j = 0; j < this.vertex; ++j)
                this.edge[i][j] = 0;
        }
    }
    graph(graph source) /* COLORS REMAIN 0(ZERO) */                            {
        this.vertex = source.vertex;
        this.edge   = new int[this.vertex][this.vertex];
        this.color  = new int[this.vertex];

        for (int i = 0; i < this.vertex; ++i) {
            this.color[i] = 0;
            for (int j = 0; j < this.vertex; ++j)
                this.edge[i][j] = source.edge[i][j];
        }
    }
    graph(int vertex)                                                          {
        this.vertex = vertex;
        this.edge   = new int[this.vertex][this.vertex];
        this.color  = new int[this.vertex];

        for (int i = 0; i < this.vertex; ++i) {
            this.color[i] = 0;
            for (int j = 0; j < this.vertex; ++j)
                this.edge[i][j] = 0;
        }
    }
    graph(int[][] edge)                                                        {
        this.vertex = edge.length;
        this.edge   = new int[this.vertex][this.vertex];
        this.color  = new int[this.vertex];

        for (int i = 0; i < this.vertex; ++i) {
            this.color[i] = 0;
            for (int j = 0; j < this.vertex; ++j)
                this.edge[i][j] = edge[i][j];
        }
    }
    graph(int vertex, int[][] edge)                                            {
        this.vertex = vertex;
        this.edge   = new int[this.vertex][this.vertex];
        this.color  = new int[this.vertex];

        for (int i = 0; i < this.vertex; ++i) {
            this.color[i] = 0;
            for (int j = 0; j < this.vertex; ++j)
                this.edge[i][j] = edge[i][j];
        }
    }
    graph(int vertex, int[][] edge, int[] color)                               {
        this.vertex = vertex;
        this.edge   = new int[this.vertex][this.vertex];
        this.color  = new int[this.vertex];

        for (int i = 0; i < this.vertex; ++i) {
            this.color[i] = color[i];
            for (int j = 0; j < this.vertex; ++j)
                this.edge[i][j] = edge[i][j];
        }
    }
    graph(String file) throws IOException                                      {

        Scanner fd = new Scanner(new File(file));

        this.vertex = fd.nextInt();
        this.edge   = new int[this.vertex][this.vertex];
        this.color  = new int[this.vertex];

        for (int i = 0; i < this.vertex; ++i) {
            this.color[i] = 0;
            for (int j = 0; j < this.vertex; ++j)
                edge[i][j] = 0;
        }

        while (fd.hasNextInt())
            edge[fd.nextInt()-1][fd.nextInt()-1] = 1;
    }
    graph(String file, Random random_gen, int colors) throws IOException       {

        Scanner fd = new Scanner(new File(file));

        this.vertex = fd.nextInt();
        this.edge   = new int[this.vertex][this.vertex];
        this.color  = new int[this.vertex];

        for (int i = 0; i < this.vertex; ++i) {
            this.color[i] = random_gen.nextInt(colors);
            for (int j = 0; j < this.vertex; ++j)
                edge[i][j] = 0;
        }

        while (fd.hasNextInt())
            edge[fd.nextInt()-1][fd.nextInt()-1] = 1;
    }

    void copy_from(graph source) /* COLORS ARE COPIED */                       {
        this.vertex = source.vertex;
        this.edge   = new int[this.vertex][this.vertex];
        this.color  = new int[this.vertex];

        for (int i = 0; i < this.vertex; ++i) {
        this.color[i] = source.color[i];
        for (int j = 0; j < this.vertex; ++j)
            this.edge[i][j] = source.edge[i][j];
    }
}

    void display()                                                             {

        System.out.print("\t|\t");

        for (int i = 0; i < this.vertex; ++i)
            System.out.print(i + "\t");

        System.out.println();

        for (int i = 0; i < this.vertex + 2; ++i)
            System.out.print("----");

        System.out.println();

        for (int i = 0; i < this.vertex; ++i) {
            System.out.print(i + "\t|\t");
            for (int j = 0; j < this.vertex; ++j)
                if (edge[i][j] == 1)
                    System.out.print(edge[i][j] + "\t");
                else
                    System.out.print(" \t");
            System.out.println();
        }
    }
    void display_colors()                                                      {

        for (int i = 0; i < this.vertex; ++i){
            switch (this.color[i]) {
                case 0:
                    System.out.print("A ");
                    break;
                case 1:
                    System.out.print("B ");
                    break;
                case 2:
                    System.out.print("C ");
                    break;
                case 3:
                    System.out.print("D ");
                    break;
                case 4:
                    System.out.print("E ");
                    break;
                case 5:
                    System.out.print("F ");
                    break;
                case 6:
                    System.out.print("G ");
                    break;
                case 7:
                    System.out.print("H ");
                    break;
                case 8:
                    System.out.print("I ");
                    break;
                case 9:
                    System.out.print("J ");
                    break;
                case 10:
                    System.out.print("K ");
                    break;
                case 11:
                    System.out.print("L ");
                    break;
                case 12:
                    System.out.print("M ");
                    break;
                case 13:
                    System.out.print("N ");
                    break;
                case 14:
                    System.out.print("P ");
                    break;
                case 15:
                    System.out.print("Q ");
                    break;
                case 16:
                    System.out.print("R ");
                    break;
                case 17:
                    System.out.print("S ");
                    break;
                case 18:
                    System.out.print("T ");
                    break;
                case 19:
                    System.out.print("U ");
                    break;
                case 20:
                    System.out.print("V ");
                    break;
                case 21:
                    System.out.print("W ");
                    break;
                case 22:
                    System.out.print("X ");
                    break;
                case 23:
                    System.out.print("Y ");
                    break;
                case 24:
                    System.out.print("Z ");
                    break;
                default:
                    break;
            }
        }
    }

    int conflicts()                                                            {

        int conflicts = 0;

        for (int i = 0; i < this.vertex; ++i)
            for (int j = 0; j < this.vertex; ++j)
                if (this.edge[i][j] == 1)
                    if (this.color[i] == this.color[j])
                        conflicts++;

        return conflicts / 2;
    }

    void spin(int index, int colors)                                           {

        Random random_color = new Random(System.currentTimeMillis());

        color[index] = random_color.nextInt(colors);
    }
}
