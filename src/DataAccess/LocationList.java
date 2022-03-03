package DataAccess;

import java.util.Random;

public class LocationList {
    public static class LocationListStatic {

        private User[] data;
        private static User[] dataStatic;

        public LocationListStatic() {

        }

        public User[] getData() {

            return data;
        }

        public void setData(User[] data) {
            this.data = data;
        }

        public User getRandom() {
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
