package DataAccess;

import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

 public class FirstNameList {
     public static class firstNameStatic {

         private static List<String> fNameList = new ArrayList<String>();


         public firstNameStatic(){}


         public firstNameStatic(JsonArray list) {
             for (int i = 0; i < list.size(); i++) {
                 fNameList.add(list.get(i).toString());
             }
         }

         public String getRandomFirstName() {
             int listSize = fNameList.size();
             Random rand = new Random();

             int randomInt = rand.nextInt(listSize);

             return fNameList.get(randomInt);

         }
     }


}
