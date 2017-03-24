import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Vector;

@SuppressWarnings("ALL")

public class graph_coloring {

    public static int    colors = 1;
    public static String input  = "input1.txt";
    public static String output = "output.txt";

    /**
     *   FITNESS FUNCTIONS
     */

    public static int compute_fitness(graph individual){
        return 100 - individual.conflicts();
    }

    /**
     *   GENETIC OPERATORS
     */

    public static graph mutation(graph parent)                                    {

        Random random_mutator = new Random(System.currentTimeMillis());

        graph descendant = new graph(parent);

        descendant.spin(random_mutator.nextInt(parent.vertex), colors);

        return descendant;
    }
    public static Vector<graph> random_crossover(graph parent_1, graph parent_2)  {

        Random random_cutoff_generator = new Random(System.currentTimeMillis());

        Vector<graph> descendants = new Vector<graph>();

        descendants.addElement(new graph(parent_1));
        descendants.addElement(new graph(parent_1));

        int gene_counter = 0,
            next_cutoff_size,
            next_parent = 1;

        while(gene_counter <= parent_1.vertex - 1){

            next_cutoff_size = random_cutoff_generator.nextInt(parent_1.vertex - gene_counter + 1);

            if (next_parent == 0) {
                for (int i = gene_counter; i < gene_counter + next_cutoff_size; ++i) {
                    descendants.elementAt(0).color[i] = parent_1.color[i];
                    descendants.elementAt(1).color[i] = parent_2.color[i];
                }

                next_parent = 1;
            }
            else {
                for (int i = gene_counter; i < gene_counter + next_cutoff_size; ++i) {
                    descendants.elementAt(1).color[i] = parent_1.color[i];
                    descendants.elementAt(0).color[i] = parent_2.color[i];
                }

                next_parent = 0;
            }

            gene_counter += next_cutoff_size;
        }

        return descendants;
    }
    public static Vector<graph> uniform_crossover(graph parent_1, graph parent_2) {

        Random next_parent_selector = new Random(System.currentTimeMillis());

        Vector<graph> descendants = new Vector<graph>();

        descendants.addElement(new graph(parent_1));
        descendants.addElement(new graph(parent_1));

        for (int i = 0; i < parent_1.vertex; ++i) {
            if (next_parent_selector.nextInt(2) == 0) {
                descendants.elementAt(0).color[i] = parent_1.color[i];
                descendants.elementAt(1).color[i] = parent_2.color[i];
            }
            else {
                descendants.elementAt(1).color[i] = parent_1.color[i];
                descendants.elementAt(0).color[i] = parent_2.color[i];
            }
        }

        return descendants;
    }

    /**
     *   UTILITY FUNCTIONS
     */

    public static int select_best_individual(Vector<graph> population) {

        if (population.size() == 0)
            return 0;

        int    best_index   = 0;
        double best_fitness = compute_fitness(population.elementAt(0));

        for(int i = 1; i < population.size(); ++i)
            if (compute_fitness(population.elementAt(i)) > best_fitness) {
                best_fitness = compute_fitness(population.elementAt(i));
                best_index = i;
            }

        return best_index;
    }

    /**
     *   MAIN FUNCTION
     */

    public static void main(String[] args) throws IOException {

        /**
         *   VARIABLE DECLARATION & INITIALIZATION
         */

        Random random_generator = new Random(System.currentTimeMillis());

        FileWriter fd = new FileWriter(output);



        //  GENERERATE P(t) - INITIAL

        Vector<graph> population = new Vector<>();
        for(int i = 0; i < 30; ++i)
            population.addElement(new graph(input, random_generator, colors));



        //  EVALUATE P(t)   - INITIAL

        Vector<Integer> population_eval = new Vector<>();
        for (int i = 0; i < 30; ++i)
            population_eval.addElement(compute_fitness(population.elementAt(i)));



        //  ROULETTE WHEEL VARIABLES

        int    recombination_method;
        double total_fitness,
               next_selected_individual;

        Vector<Double> individual_selection_probability = new Vector<>();
        Vector<Double> cumulative_selection_probability = new Vector<>();
        Vector<graph>  selected_population              = new Vector<>();



        //   INITIAL STATE DISPLAY

        //System.out.print("COLORS: " + colors + "\t\t");
        //population.elementAt(select_best_individual(population)).display_colors();
        //System.out.println("\t\t" + population.elementAt(select_best_individual(population)).conflicts());

        /**
         *   GENERATION SELECTION & RECOMBINATION
         *   * RANK BASED
         *   * ELITISM = 0
         */


        graph minimum_coloring  = new graph(population.elementAt(0));
        int   minimum_colors    = minimum_coloring.vertex,
              minimum_iteration = 100;

        for(int iteration = 0; iteration < 15; ++iteration) {

            colors = 1;

            population.clear();
            for(int i = 0; i < 30; ++i)
                population.addElement(new graph(input, random_generator, colors));

            population_eval.clear();
            for (int i = 0; i < 30; ++i)
                population_eval.addElement(compute_fitness(population.elementAt(i)));

            individual_selection_probability.clear();
            cumulative_selection_probability.clear();
            selected_population.clear();



            //   ITERATE TROUGH COLORS UNTIL A VALID N-COLORING IS FOUND

            while (population.elementAt(select_best_individual(population)).conflicts() > 0) {

                colors++;



                //  100 GENERATIONS, STARTING AT t = 0

                for (int generation = 0; generation < 250; ++generation) {



                    //  RANK BASED SELECTION AND RECOMBINATION:



                    //    1. FITNESS EVALUATION

                    for (int i = 0; i < population.size(); ++i)
                        population_eval.set(i, compute_fitness(population.elementAt(i)));



                    //    1.1 ORDER BY RANK (a simple bubble sort)

                    for (int i = 0; i < population.size() - 1; ++i)
                        for (int j = i; j < population.size(); ++j)
                            if (population_eval.elementAt(i) > population_eval.elementAt(j)) {

                                population_eval.set(j, population_eval.set(i, population_eval.elementAt(j)));
                                population.set(j, population.set(i, population.elementAt(j)));

                                i = 0;
                            }



                    //    1.2 SET FITNESS ACCORDING TO RANK

                    for (int i = 0; i < population_eval.size(); ++i)
                        population_eval.set(i, (Integer) i);



                    //    2. TOTAL FITNESS

                    total_fitness = 0.0;
                    for (int i = 0; i < population.size(); ++i)
                        total_fitness += population_eval.elementAt(i);



                    //    3. INDIVIDUAL SELECTION PROBABILITY

                    individual_selection_probability.clear();
                    for (int i = 0; i < population.size(); ++i)
                        individual_selection_probability.addElement(population_eval.elementAt(i) / total_fitness);



                    //    4. CUMULATIVE SELECTION PROBABILITY

                    cumulative_selection_probability.clear();
                    cumulative_selection_probability.addElement(0.0);
                    for (int i = 0; i < population.size(); ++i)
                        cumulative_selection_probability.addElement(cumulative_selection_probability.elementAt(i) + individual_selection_probability.elementAt(i));



                    //    5. SELECTION

                    selected_population.clear();
                    for (int i = 0; i < 17; ++i) {

                        //  GENERATE r
                        next_selected_individual = random_generator.nextDouble();
                        if (next_selected_individual == 0)
                            next_selected_individual += 0.000000000001;

                        // SELECT THE CORRESPONDING INDIVIDUAL
                        for (int j = 0; j < cumulative_selection_probability.size() - 1; ++j) {

                            if (cumulative_selection_probability.elementAt(j) < next_selected_individual &&
                                    next_selected_individual <= cumulative_selection_probability.elementAt(j + 1)) {

                                if (selected_population.contains(population.elementAt(j))) {
                                    --i;
                                } else {
                                    selected_population.addElement(population.elementAt(j));
                                    break;
                                }
                            }
                        }
                    }



                    //    6. RECOMBINATION

                    population.clear();
                    for (int i = 0; i < selected_population.size(); ++i)
                        population.addElement(selected_population.elementAt(i));
                    selected_population.clear();

                    while (selected_population.size() < 20) { /* ELITISM ADJUSTMENT */

                        recombination_method = random_generator.nextInt(3);
                        if (population.size() == 1)
                            recombination_method = 0;

                        int parent_1_index, parent_2_index;

                        switch (recombination_method) {

                            case 0:
                                //  MUTATE BEST INDIVIDUAL
                                parent_1_index = select_best_individual(population);

                                selected_population.addElement(mutation(population.elementAt(parent_1_index)));

                                population.remove(parent_1_index);
                                break;

                            case 1:
                                //  UNIFORM CROSSOVER: BEST INDIVIDUAL + RANDOM MATE
                                parent_1_index = select_best_individual(population);
                                parent_2_index = random_generator.nextInt(population.size());

                                selected_population.addAll(uniform_crossover(population.elementAt(parent_1_index),
                                        population.elementAt(parent_2_index)));

                                graph remove_reference_1 = new graph(population.elementAt(parent_2_index));
                                population.remove(parent_1_index);
                                population.remove(remove_reference_1);
                                break;

                            case 2:
                                //  RANDOM CROSSOVER: BEST INDIVIDUAL + RANDOM MATE
                                parent_1_index = select_best_individual(population);
                                parent_2_index = random_generator.nextInt(population.size());

                                selected_population.addAll(random_crossover(population.elementAt(parent_1_index),
                                        population.elementAt(parent_2_index)));

                                graph remove_reference_2 = new graph(population.elementAt(parent_2_index));
                                population.remove(parent_1_index);
                                population.remove(remove_reference_2);
                                break;

                            default:
                                break;
                        }
                    }
                    while (selected_population.size() > 20)  /* ELITISM ADJUSTMENT */
                        selected_population.remove(selected_population.size() - 1);



                    //    7.  RANDOM NEW INDIVIDUALS

                    for (int i = 0; i < 10; ++i)
                        selected_population.addElement(new graph(input, random_generator, colors));



                    //    8. POPULATION VECTOR ACTUATION

                    population.clear();
                    population_eval.clear();
                    for (int i = 0; i < selected_population.size(); ++i) {
                        population.addElement(selected_population.elementAt(i));
                        population_eval.addElement(compute_fitness(population.elementAt(i)));
                    }
                    selected_population.clear();
                }

                /*System.out.print("ITERATION: " + iteration + "\t\tCOLORS: " + colors + "\t\t");
                population.elementAt(select_best_individual(population)).display_colors();
                System.out.println("\t\t" + population.elementAt(select_best_individual(population)).conflicts());*/
            }

            if (colors < minimum_colors) {
                minimum_colors = colors;
                minimum_coloring.copy_from(population.elementAt(select_best_individual(population)));
                minimum_iteration = iteration;
            }

            //   CURRENT ITERATION DISPLAY

            System.out.print("ITERATION " + iteration + " COMPLETE\n");

            fd.write("ITERATION: " + iteration + "\nCOLORS: " + colors + " \n");
            for (int i = 0; i < population.elementAt(select_best_individual(population)).vertex; ++i){
                switch (population.elementAt(select_best_individual(population)).color[i]) {
                    case 0:
                        fd.write("A ");
                        break;
                    case 1:
                        fd.write("B ");
                        break;
                    case 2:
                        fd.write("C ");
                        break;
                    case 3:
                        fd.write("D ");
                        break;
                    case 4:
                        fd.write("E ");
                        break;
                    case 5:
                        fd.write("F ");
                        break;
                    case 6:
                        fd.write("G ");
                        break;
                    case 7:
                        fd.write("H ");
                        break;
                    case 8:
                        fd.write("I ");
                        break;
                    case 9:
                        fd.write("J ");
                        break;
                    case 10:
                        fd.write("K ");
                        break;
                    case 11:
                        fd.write("L ");
                        break;
                    case 12:
                        fd.write("M ");
                        break;
                    case 13:
                        fd.write("N ");
                        break;
                    case 14:
                        fd.write("P ");
                        break;
                    case 15:
                        fd.write("Q ");
                        break;
                    case 16:
                        fd.write("R ");
                        break;
                    case 17:
                        fd.write("S ");
                        break;
                    case 18:
                        fd.write("T ");
                        break;
                    case 19:
                        fd.write("U ");
                        break;
                    case 20:
                        fd.write("V ");
                        break;
                    case 21:
                        fd.write("W ");
                        break;
                    case 22:
                        fd.write("X ");
                        break;
                    case 23:
                        fd.write("Y ");
                        break;
                    case 24:
                        fd.write("Z ");
                        break;
                    default:
                        break;
                }
            }
            fd.write("\t\t" + population.elementAt(select_best_individual(population)).conflicts() + "\n\n");
        }

        fd.write("\n==============================\n" + "BEST ITERATION: " + minimum_iteration + "\nMINIMUM COLORS: " + minimum_colors + " \n");
        for (int i = 0; i < minimum_coloring.vertex; ++i){
            switch (minimum_coloring.color[i]) {
                case 0:
                    fd.write("A ");
                    break;
                case 1:
                    fd.write("B ");
                    break;
                case 2:
                    fd.write("C ");
                    break;
                case 3:
                    fd.write("D ");
                    break;
                case 4:
                    fd.write("E ");
                    break;
                case 5:
                    fd.write("F ");
                    break;
                case 6:
                    fd.write("G ");
                    break;
                case 7:
                    fd.write("H ");
                    break;
                case 8:
                    fd.write("I ");
                    break;
                case 9:
                    fd.write("J ");
                    break;
                case 10:
                    fd.write("K ");
                    break;
                case 11:
                    fd.write("L ");
                    break;
                case 12:
                    fd.write("M ");
                    break;
                case 13:
                    fd.write("N ");
                    break;
                case 14:
                    fd.write("P ");
                    break;
                case 15:
                    fd.write("Q ");
                    break;
                case 16:
                    fd.write("R ");
                    break;
                case 17:
                    fd.write("S ");
                    break;
                case 18:
                    fd.write("T ");
                    break;
                case 19:
                    fd.write("U ");
                    break;
                case 20:
                    fd.write("V ");
                    break;
                case 21:
                    fd.write("W ");
                    break;
                case 22:
                    fd.write("X ");
                    break;
                case 23:
                    fd.write("Y ");
                    break;
                case 24:
                    fd.write("Z ");
                    break;
                default:
                    break;
            }
        }
        fd.write("\t\t" + minimum_coloring.conflicts() + "\n==============================\n");

        fd.flush();
        fd.close();
    }
}

