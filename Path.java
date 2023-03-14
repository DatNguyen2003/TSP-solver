package AI_project3_DatNguyen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Path {
  private ArrayList<Integer> cities;
  private int dis;
  private static int[][] distances;
  private static String[] cityNames;

  public static void setCityNames(String[] cityNames) {
    Path.cityNames = cityNames;
  }

  public static void setDistances(int[][] distances){
    Path.distances = distances;
  }

  public int getDis() {
    return dis;
  }

  public ArrayList<Integer> getCities() {
    return cities;
  }

  public int calDis(){//Calculate the distance of the Path
    int result=0;
    for(int i=0;i<this.cities.size()-1;i++){
      result+=distances[cities.get(i)][cities.get(i+1)];
    }
    result+=distances[cities.get(cities.size()-1)][cities.get(0)];
    return result;
  }

  public Path(int numsCities){//Create a random Path
    cities = new ArrayList<Integer>(numsCities);
    for(int i=0;i<numsCities;i++){
      cities.add(i);
    }
    Collections.shuffle(cities);
    dis = calDis();
  }

  public Path(ArrayList<Integer> cities){
    this.cities = cities;
    dis = calDis();
  }

  public static Path crossOver(Path parent1, Path parent2){//Generate a Path using information of 2 Path
    int childSize = parent1.getCities().size();
    ArrayList<Integer> child = new ArrayList<Integer>(childSize);
    Random rand = new Random();
    int parentIndex1 = rand.nextInt(childSize);
    int parentIndex2 = rand.nextInt(childSize);
    int start = Math.min(parentIndex1,parentIndex2);
    int end = Math.max(parentIndex1,parentIndex2);
    ArrayList<Integer> cross = new ArrayList<Integer>(end-start+1);

    for(int i=0;i<childSize;i++){//Fill the children with information from Path parent1
      child.add(parent1.cities.get(i));
    }

    for(int i=start;i<end+1;i++){//Get Path parent2 indexes of cities in the chosen range
      cross.add(parent2.cities.indexOf(parent1.cities.get(i)));
    }

    Collections.sort(cross);

    for(int i=start;i<end+1;i++){//Replace cities of children in the chosen range by the order appear in parent 2
      child.set(i,parent2.cities.get(cross.get(i-start)));
    }

    return new Path(child);
  }

  public void mutate(){//Mutate a Path by swiching the index of 2 random cities
    Random rand = new Random();
    int index1 = rand.nextInt(cities.size());
    int index2 = rand.nextInt(cities.size());
    int temp= cities.get(index1);
    cities.set(index1,cities.get(index2));
    cities.set(index2,temp);
  }

  public void print(){
    for(int i:cities){
      System.out.print(cityNames[i]+" ");
    }
    System.out.println();
  }
  
}
