package DataAccess;

import java.util.Random;

public class LocationList {
    public static class LocationListStatic {

        private EventLocation[] data;
        private static EventLocation[] dataStatic;

        //Using a static class so that the GSON imports correctly
        public LocationListStatic() {}

        public EventLocation[] getData() {

            return data;
        }

        public void setData(EventLocation[] data) {

            this.data = data;
        }

        public EventLocation getRandom() {
            int listSize = dataStatic.length;
            Random rand = new Random();

            int randomInt = rand.nextInt(listSize);

            return dataStatic[randomInt];

        }

        public void setStatic(){

            dataStatic = data;
        }
    }
}
