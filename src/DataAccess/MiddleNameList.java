package DataAccess;

import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MiddleNameList {
    public static class middleNameStatic {

        private static List<String> mNameList = new ArrayList<String>();


        public middleNameStatic(){}


        public middleNameStatic(JsonArray list) {
            for (int i = 0; i < list.size(); i++) {
                mNameList.add(list.get(i).toString());
            }
        }

        public String getRandomFirstName() {
            int listSize = mNameList.size();
            Random rand = new Random();

            int randomInt = rand.nextInt(listSize);

            return mNameList.get(randomInt);

        }
    }


}
