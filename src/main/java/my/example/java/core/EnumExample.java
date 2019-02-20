package my.example.java.core;

/** @author nvduc */
public class EnumExample {

  public static void main(String[] args) {
    System.out.println(" $$$$$$$$$$ " + EnergyTypeEnum.ELECTRICITE.getTypeAsString());
    // System.out.println("========== "+ EnergyTypeEnum.valueOf("1"));
  }

  public enum EnergyTypeEnum {
    ELECTRICITE(1),
    MAZOUT(2),
    GAZ(3);

    private final int text;

    /** @param text */
    private EnergyTypeEnum(final int text) {
      this.text = text;
    }

    public int getTypeAsString() {
      return text;
    }
  }
}
