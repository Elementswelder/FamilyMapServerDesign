package DataAccess;

import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LastNameList {
    public static class lastNameStatic {

        private static List<String> lNameList = new ArrayList<>();

        //Using static so the GSON imports correctly
        public lastNameStatic(){}

        //Add all the last names to a list
        public lastNameStatic(JsonArray list) {
            for (int i = 0; i < list.size(); i++) {
                lNameList.add(list.get(i).toString());
            }
        }
        //Get a random last name for people
        public String getRandomLastName() {
            int listSize = lNameList.size();
            Random rand = new Random();

            int randomInt = rand.nextInt(listSize);

            return lNameList.get(randomInt);

        }
    }


}
