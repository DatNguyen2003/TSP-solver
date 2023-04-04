package AI_project3_DatNguyen;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;

public class AI_project3_DatNguyen {
  private static final int numsGen = 20;
  private static final int numsPathGen = 90;
  private static Random rand = new Random();

  public static void main(String[] args) {
    System.out.println("Welcome to The Traveling Salesman problem (TSP)!");
    System.out.println("1.tsp.txt\n2.tsp9.txt\n3.indiana.txt\n4.tsp21.txt\n5.tsp99.txt\n6.us.txt");
    System.out.println("Choose which file you want to solve:");
    Scanner sc = new Scanner(System.in);
    int path = sc.nextInt();
    String pathname = "";
    switch (path) {
      case 1:
        pathname = "AI_project3_DatNguyen//tsp.txt";
        break;
      case 2:
        pathname = "AI_project3_DatNguyen//tsp9.txt";
        break;
      case 3:
        pathname = "AI_project3_DatNguyen//indiana.txt";
        break;
      case 4:
        pathname = "AI_project3_DatNguyen//tsp21.txt";
        break;
      case 5:
        pathname = "AI_project3_DatNguyen//tsp99.txt";
        break;
      case 6:
        pathname = "AI_project3_DatNguyen//us.txt";
        break;
      default: {
        System.out.println("Error input!");
      }
    }
    sc.close();

    try {
      File x = new File(pathname);
      sc = new Scanner(x);
      int numsCities = Integer.valueOf(sc.nextLine());
      String[] cityNames = new String[numsCities];
      int[][] distances = new int[numsCities][numsCities];
      for (int i = 0; i < numsCities; i++) {
        cityNames[i] = sc.nextLine();
        System.out.println(cityNames[i]);
      }
      for (int i = 0; i < numsCities; i++) {
        String[] inputs = sc.nextLine().split(" ");
        for (int j = 0; j < numsCities; j++) {
          distances[i][j] = Integer.valueOf(inputs[j]);
          System.out.print(distances[i][j] + " ");
        }
        System.out.println();
      }
      sc.close();
      
      Path.setDistances(distances);
      Path.setCityNames(cityNames);
      ArrayList<Path> gens = new ArrayList<Path>(numsPathGen);
      for (int i = 0; i < numsPathGen; i++) {//Generate the first generation using Path random constructor
        Path child = new Path(numsCities);
        if(!gens.contains(child)){
          gens.add(child);
        }
      }

      for (int i = 0; i < numsGen; i++) {
        
        Collections.sort(gens, new Comparator<Path>() {//Sort the generation based on Path's distance in ascending order
          @Override
          public int compare(Path o1, Path o2) {
            return o1.getDis() - o2.getDis();
          }
        });
        System.out.print("Shortest distance of generation "+i+": ");
        System.out.println(gens.get(0).getDis());

        ArrayList<Path> nextGens = new ArrayList<Path>(numsPathGen);//Create the next generation
        nextGens.add(gens.get(0));
        while (nextGens.size() < gens.size()) {//Select Paths to crossover and add their children to the next generation
          int parent1Index = rand.nextInt(numsPathGen/4);
          int parent2Index = rand.nextInt(numsPathGen);
          Path child = Path.crossOver(gens.get(parent1Index), gens.get(parent2Index));
          if(!nextGens.contains(child)){
            nextGens.add(child);
          }
        }

        Collections.sort(nextGens, new Comparator<Path>() {//Sort the next generation base on Path's distance in descending order
          @Override
          public int compare(Path o1, Path o2) {
            return o2.getDis() - o1.getDis();
          }
        });

        int numsMutate = rand.nextInt(numsPathGen);//Mutate a random number of Paths with the highest cost in the next generation 
        for(int j=0;j<numsMutate;j++){
          nextGens.get(j).mutate();
        }
        gens = nextGens;//Replace the old generation with the next one
      }
      
      Collections.sort(gens, new Comparator<Path>() {//Sort the last generation based on Path's distance in ascending order
        @Override
        public int compare(Path o1, Path o2) {
          return o1.getDis() - o2.getDis();
        }
      });
      System.out.print("Shortest distance of generation "+numsGen+": ");
      System.out.println(gens.get(0).getDis());
      System.out.println("Shortest path:");
      gens.get(0).print();
    } catch (Exception e) {

    }
  }
}
