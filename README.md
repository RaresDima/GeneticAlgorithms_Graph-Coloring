# GeneticAlgorithms_Graph-Coloring


The implementation of a genetic algorithm that determines the minimum number of colors needed for a valid graph coloring for a given graph. 

The algorithm runs 15 iterations of the algorithm, each having 250 generations on a sample size of 30. 

The fitness function is defines as the conflicts for a particular coloring, the number of edges that connect nodes with the same color. Obviously a valid coloring would have no conflicts at all.

The genetic operators used are the basic mutation and uniform crossover, and a more advanced random crossover(a demonstration of it in a main function is also included) which randomizes both the number, placement and size of cutoff points.

The algorithm takes a .txt file as an input, containing a classic graph representation: a number n, the sieze of the graph, and then integer pairs representing the edges, and it outputs the colorings it finds into the output.txt file, repeating the best one it found at the end of the file for ease of access.
