package my.example.core;

import java.awt.Point;

/** @author nvduc */
public class PassReference {
  public static void main(String[] args) {
    Point p = new Point();
    //        Point p = null;
    changePoint(p);
    System.out.println("Point " + p);
  }

  public static void changePoint(Point p) {
    if (p == null) {
      p = new Point(1, 2);
    } else {
      p.setLocation(10, 20);
    }
  }
}
