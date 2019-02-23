package my.example.bruceeckel;

import java.util.Arrays;
import java.util.List;

interface Pet {
  void speak();
}

class Rat implements Pet {

  @Override
  public void speak() {
    System.out.println("Squeak!!!!");
  }
}

class Frog implements Pet {

  @Override
  public void speak() {
    System.out.println("Rabbit");
  }
}

public class InternalVsExternalIteration {

  public static void main(String[] args) {
    List<Pet> pets = Arrays.asList(new Rat(), new Frog());

    System.out.println("Outer loop");
    for (Pet p : pets) {
      p.speak();
    }

    System.out.println("Inner loop");
    pets.forEach(Pet::speak);
  }
}
