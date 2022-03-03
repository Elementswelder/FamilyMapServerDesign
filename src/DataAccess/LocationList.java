package DataAccess;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LocationList {
    public static class LocationListStatic {

        private eventLocation[] data;
        private static eventLocation[] dataStatic;

        public LocationListStatic() {

        }

        public eventLocation[] getData() {

            return data;
        }

        public void setData(eventLocation[] data) {
            this.data = data;
        }

        public eventLocation getRandom() {
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
