package metrics;

/**
 *
 * @author Jayson
 */
public class Schwartzberg {

  public Double calculate(double area, double perimeter) {
    // equalAreaRadius = r = sqrt(A/PI)
    double r = Math.sqrt(area / Math.PI);
    // equalAreaPerimeter = C = 2pi * r (Circumference)
    double equalAreaPerimeter = 2 * Math.PI * r;
    // Schwartzberg score = 1 / (Perimeter of district / C )
    double score = 1 / (perimeter / equalAreaPerimeter);
    return score;
  }

}