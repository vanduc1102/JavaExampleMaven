import java.util.Properties;

/** @author nvduc */
public class SystemProperties {
  public static void main(String[] args) {
    Properties props = System.getProperties();
    props.list(System.out);
  }
}
