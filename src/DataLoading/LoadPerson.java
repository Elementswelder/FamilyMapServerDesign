package DataLoading;

import DataAccess.eventLocation;
import Model.User;

import java.util.Random;

public class LoadPerson {
    public static class LoadPersonStatic {

        private User[] data;
        private static User[] dataStatic;

        public LoadPersonStatic() {

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
