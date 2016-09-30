package my.example.java.core;

import java.util.Objects;

/**
 *
 * @author nvduc
 */
public class EnumExample {

    public static void main(String[] args) {
        System.out.println(" $$$$$$$$$$ "+ EnergyTypeEnum.ELECTRICITE.getTypeAsString());
        //System.out.println("========== "+ EnergyTypeEnum.valueOf("1"));
    }

    public enum EnergyTypeEnum {

        ELECTRICITE("1"), MAZOUT("2"), GAZ("3");

        private final String text;

        /**
         * @param text
         */
        private EnergyTypeEnum(final String text) {
            this.text = text;
        }

        /* (non-Javadoc)
         * @see java.lang.Enum#toString()
         */
        @Override
        public String toString() {
            return text;
        }

        public String getTypeAsString() {
            return text;
        }

    }

}
