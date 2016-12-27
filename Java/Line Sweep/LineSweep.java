import java.util.TreeSet;
import java.util.Scanner;
import java.io.*;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Comparator;

class Point{
  double x;
  double y;

  boolean isEndPoint;
  Point otherPoint; // End-Point knows startPoint and vice-versa.

  public Point(double x, double y, boolean isEP, Point op){
    this.x = x;
    this.y = y;

    isEndPoint = isEP;
    otherPoint = op;
  }

  public void setOtherPoint(Point op){
    otherPoint = op;
  }
}

class PointsComparator implements Comparator<Point>{
  @Override
  public int compare(Point p1, Point p2){
    if(p1.x < p2.x) return -1;
    if(p1.x > p2.x) return 1;
    else {
      return p1.y < p2.y ? -1 : 1;
    }
  }
}

class TreeNodeComparator implements Comparator<Point>{
  @Override
  public int compare(Point p1, Point p2){
    // p1 is existing node.
    if(p1 == null) return 0;
    if(p1 == null) System.out.println("P1 NULL");
    if(p2 == null) System.out.println("P2 NULL");

    Point sp1 = p1.isEndPoint ? p1.otherPoint : p1;
    Point ep1 = p1.isEndPoint ? p1 : p1.otherPoint;
    if(sp1 == null) System.out.println("SP1 NULL");
    if(ep1 == null) System.out.println("EP1 NULL");
    return ((sp1.x - ep1.x) * (sp1.y - p2.y) - (sp1.y - ep1.y) * (sp1.x - p2.x)) > 0 ? 1 : -1;
  }
}

public class LineSweep{
  public static void main(String[] args) {
    if(args.length != 1){
      System.out.println("Please enter name of the file.");
      return;
    }
    File input = new File(args[0]);
    Scanner sc;
    try{
      sc = new Scanner(input);

      int n = sc.nextInt();

      Point [] points = new Point[2 * n];

      for(int i = 0 ; i < n ; i++){
        double spx = sc.nextDouble();
        double spy = sc.nextDouble();
        double epx = sc.nextDouble();
        double epy = sc.nextDouble();

        if(spx > epx){// Interchange
          double temp = spx;
          spx = epx;
          epx = spx;

          temp = spy;
          spy = epy;
          epy = spy;
        }

        points[2 * i] = new Point(spx, spy, false, null); // Start Point of line
        points[2 * i + 1] = new Point(epx, epy, true, points[2 * i]); // End Point of line

        points[2 * i].setOtherPoint(points[2 * i + 1]);
      }

      // printPoints(points);
      Arrays.sort(points, new PointsComparator());
      // printPoints(points);

      if(isAnyIntersecting(points)){
        // System.out.println("Intersecting");
        return;
      }System.out.println("NOT Intersecting");
    }catch(FileNotFoundException e){
      System.out.println("File Not Found!");
    }
  }

  private static void printPoints(Point [] points){
    for(Point point : points){
      System.out.println("(" + + point.x + ", " + point.y + ")");
    }
    System.out.println();
  }

  private static boolean isAnyIntersecting(Point[] points){
    TreeSet<Point> tree = new TreeSet<>(new TreeNodeComparator());
    for(Point point : points){
      if(!point.isEndPoint){
        tree.add(point);
        if(tree.higher(point) != null && areIntersecting(tree.higher(point), point)){
          return true;
        }
        if(tree.lower(point) != null && areIntersecting(tree.lower(point), point)){
          return true;
        }
      }else{
        if(tree.higher(point) != null && tree.lower(point) != null && areIntersecting(tree.higher(point), tree.lower(point))){
          return true;
        }
        tree.remove(point.otherPoint);
      }
    }
    return false;
  }

  private static String getPointRepresentation(Point p){
    return "(" + p.x + ", " + p.y + ")";
  }

  private static boolean areIntersecting(Point p1, Point p2){
    if(areCounterClockWise(p1, p2, p2.otherPoint) == areCounterClockWise(p1.otherPoint, p2, p2.otherPoint)) return false;
    else if(areCounterClockWise(p1, p1.otherPoint, p2) == areCounterClockWise(p1, p1.otherPoint, p2.otherPoint)) return false;
    // These lines are Intersecting.

    System.out.println("These points intersect: " + getPointRepresentation(p1) + ", " + getPointRepresentation(p1.otherPoint));
    System.out.println(getPointRepresentation(p2) + ", " + getPointRepresentation(p2.otherPoint));

    double denom = (p2.otherPoint.y - p2.y) * (p1.otherPoint.x - p1.x) - (p2.otherPoint.x - p2.x) * (p1.otherPoint.y - p1.y);
    double numerator = (p2.otherPoint.x - p2.x) * (p1.y - p2.y) - (p2.otherPoint.y - p2.y) * (p1.x - p2.x);

    if(denom != 0){
      System.out.println("Intersecting at (" + (p1.x + (numerator / denom) * (p1.otherPoint.x - p1.x)) + ", " + (p1.y + (numerator / denom) * (p1.otherPoint.y - p1.y)) + ")");
    }
    return true;
  }

  private static boolean areCounterClockWise(Point a, Point b, Point c){
    return ((a.x - b.x) * (a.y - c.y) - (a.y - c.y) * (a.x - c.x)) > 0 ? true : false;
  }
}
