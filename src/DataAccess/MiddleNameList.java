package DataAccess;

import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MiddleNameList {
    public static class middleNameStatic {

        private static List<String> mNameList = new ArrayList<>();


        public middleNameStatic(){}

        //Get the list and add to the middle name list
        public middleNameStatic(JsonArray list) {
            for (int i = 0; i < list.size(); i++) {
                mNameList.add(list.get(i).toString());
            }
        }
        //Get a random middle name
        public String getRandomMiddleName() {
            int listSize = mNameList.size();
            Random rand = new Random();

            int randomInt = rand.nextInt(listSize);

            return mNameList.get(randomInt);

        }
    }


}
