package DataAccess;

import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LastNameList {
    public static class lastNameStatic {

        private static List<String> lNameList = new ArrayList<String>();


        public lastNameStatic(){}


        public lastNameStatic(JsonArray list) {
            for (int i = 0; i < list.size(); i++) {
                lNameList.add(list.get(i).toString());
            }
        }

        public String getRandomFirstName() {
            int listSize = lNameList.size();
            Random rand = new Random();

            int randomInt = rand.nextInt(listSize);

            return lNameList.get(randomInt);

        }
    }


}
