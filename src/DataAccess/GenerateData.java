package DataAccess;

import Model.Event;
import Model.Person;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.util.*;

import static DataAccess.Gender.FEMALE;
import static DataAccess.Gender.MALE;

public class GenerateData {
    private final Connection connection;
    private int generationsTotal;
    private List<Person> listOfPeople = new LinkedList<>();
    private Queue<Integer> generationNum = new LinkedList<>();
    private int numPeople;
    EventDao addEvents;

    public GenerateData(Model.User user, int generation, Connection connection) throws FileNotFoundException, DataAccessException {
        this.connection = connection;
        addEvents = new EventDao(connection);
        generationsTotal = generation;
        Gender gender;
        if (user.getGender().equals("m")){
            gender = MALE;
        }
        else {
            gender = FEMALE;
        }
        generatePerson(gender, generation, user);
    }

    public Person generatePerson(Gender gender, int generations, Model.User user) throws DataAccessException, FileNotFoundException {
        Person mother = null;
        Person father = null;

        if (generations > 1 ) {
            mother = generatePerson(FEMALE, generations -1, user);
            father = generatePerson(MALE, generations -1, user);
            //Set Person ID
            // mother.setPersonID(UUID.randomUUID().toString());
            //father.setPersonID(UUID.randomUUID().toString());
            //Set Spouse ID
            mother.setSpouseID(father.getPersonID());
            father.setSpouseID(mother.getPersonID());
            listOfPeople.add(mother);
            listOfPeople.add(father);
            //Add the rest of the info

            User marriageLocation = getRandomLocation();
            int marriageYear = generateYear(generations, "Married");
            //Insert Father Marraige
            Event marEventFather = new Event(UUID.randomUUID().toString(), user.getUsername(), father.getPersonID(), marriageLocation.getLatitude(), marriageLocation.getLongitude(),
                    marriageLocation.getCountry(), marriageLocation.getCity(), "Married", marriageYear);
            addEvents.insert(marEventFather);
            //Insert Mother Marriage
            Event marEventMother = new Event(UUID.randomUUID().toString(), user.getUsername(), mother.getPersonID(), marriageLocation.getLatitude(), marriageLocation.getLongitude(),
                    marriageLocation.getCountry(), marriageLocation.getCity(), "Married", marriageYear);
            addEvents.insert(marEventMother);


            //  Event marriageMom = new Event(UUID.randomUUID().toString(), mother.getUsername(), mother.getPersonID(), )

        }
        if (generations == 1){
            //For the oldest generation
            Person personSingle = new Person(UUID.randomUUID().toString(), user.getUsername(), getRandomName("first"), getRandomName("last"),gender.toString().toLowerCase(), "none", "none", null);
            generationNum.add(generations);
            User location = getRandomLocation();
            Event birth = new Event(UUID.randomUUID().toString(), user.getUsername(), personSingle.getPersonID(), location.getLatitude(), location.getLongitude(),
                    location.getCountry(), location.getCity(), "Born", generateYear(generations, "Birth"));
            addEvents.insert(birth);
            location = getRandomLocation();
            Event death = new Event(UUID.randomUUID().toString(), user.getUsername(), personSingle.getPersonID(), location.getLatitude(), location.getLongitude(),
                    location.getCountry(), location.getCity(), "Died", generateYear(generations, "Death"));
            addEvents.insert(death);

            return personSingle;

        } else if (((generations == 2) && (generationsTotal == 2))|| ((generations == 3) && (generationsTotal == 3)) || ((generations == 4) && (generationsTotal == 4)) || (generations == 5) && (generationsTotal == 5)) {
            Person personSingle = new Person(UUID.randomUUID().toString(), user.getUsername(), user.getFirstName(), user.getLastName(),gender.toString().toLowerCase(), father.getPersonID(), mother.getPersonID(), null);
            listOfPeople.add(personSingle);
            generationNum.add(generations);
            User location = getRandomLocation();
            Event birth = new Event(UUID.randomUUID().toString(), user.getUsername(), personSingle.getPersonID(), location.getLatitude(), location.getLongitude(),
                    location.getCountry(), location.getCity(), "Born", generateYear(generations, "Birth"));
            addEvents.insert(birth);
            return personSingle;

        } else {
            Person person = new Person(UUID.randomUUID().toString(), user.getUsername(), getRandomName("first"), getRandomName("last"), gender.toString().toLowerCase(), father.getPersonID(), mother.getPersonID(),
                    mother.getSpouseID());
            // listOfPeople.add(person);
            generationNum.add(generations);
            User location = getRandomLocation();
            Event birth = new Event(UUID.randomUUID().toString(), user.getUsername(), person.getPersonID(), location.getLatitude(), location.getLongitude(),
                    location.getCountry(), location.getCity(), "Born", generateYear(generations, "Birth"));
            addEvents.insert(birth);
            location = getRandomLocation();
            Event death = new Event(UUID.randomUUID().toString(), user.getUsername(), person.getPersonID(), location.getLatitude(), location.getLongitude(),
                    location.getCountry(), location.getCity(), "Died", generateYear(generations, "Death"));
            addEvents.insert(death);
            return person;
        }

    }


    public String getRandomName(String whichName) throws FileNotFoundException {
        if (whichName.toLowerCase().equals("first")) {
            FirstNameList.firstNameStatic first = new FirstNameList.firstNameStatic();
            String randomFirstName = first.getRandomFirstName();

            return randomFirstName;
        }
        else if (whichName.toLowerCase().equals("middle")) {
            MiddleNameList.middleNameStatic middle = new MiddleNameList.middleNameStatic();
            String randomMiddleName = middle.getRandomFirstName();

            return randomMiddleName;
        }
        else if (whichName.toLowerCase().equals("last")) {
            LastNameList.lastNameStatic last = new LastNameList.lastNameStatic();
            String randomLastName = last.getRandomFirstName();

            return randomLastName;
        }
        return null;
    }

    public List<Person> getListOfPeople(){
        return listOfPeople;
    }
    public Queue<Integer> getGenerationNum(){
        return generationNum;
    }

    public int generateYear(int generation, String eventType) {
        //Year generation algorithm to give the users a good event year
        /* Generation 1: Born 2000-2005, Random Events 2005-2022
            Generation 2: Born 1965-1970, married 1983-1987, Death 1995-2000
            Generation 3: Born 1930-1935, married 1938-1952, Death 1970-2000
            Generation 4: Born 1890-1900 married 1913-1919 1950-1980
            Generation 5: Born 1860-1865, married 1878-1885, 1920-1930
            Generation 6: Born 1820-1830, married 1847- 1855, 1890-1900

         */
        if (generationsTotal == 6) {
            switch (generation) {
                case 6:
                    if (eventType.equals("Birth")) {
                        return Randomizer(2005, 2000);
                    } else {
                        return Randomizer(2022, 2005);
                    }

                case 5:
                    if (eventType.equals("Birth")) {
                        return Randomizer(1970, 1965);
                    } else if (eventType.equals("Married")) {
                        return Randomizer(1987, 1983);
                    } else if (eventType.equals("Death")) {
                        return Randomizer(2020, 2006);
                    } else {
                        return Randomizer(1995, 1970);
                    }

                case 4:
                    if (eventType.equals("Birth")) {
                        return Randomizer(1935, 1930);
                    } else if (eventType.equals("Married")) {
                        return Randomizer(1952, 1938);
                    } else if (eventType.equals("Death")) {
                        return Randomizer(2000, 1970);
                    } else {
                        return Randomizer(1970, 1935);
                    }

                case 3:
                    if (eventType.equals("Birth")) {
                        return Randomizer(1900, 1890);
                    } else if (eventType.equals("Married")) {
                        return Randomizer(1919, 1913);
                    } else if (eventType.equals("Death")) {
                        return Randomizer(1980, 1950);
                    } else {
                        return Randomizer(1950, 1900);
                    }

                case 2:
                    if (eventType.equals("Birth")) {
                        return Randomizer(1865, 1860);
                    } else if (eventType.equals("Married")) {
                        return Randomizer(1885, 1878);
                    } else if (eventType.equals("Death")) {
                        return Randomizer(1930, 1920);
                    } else {
                        return Randomizer(1920, 1865);
                    }
                case 1:
                    if (eventType.equals("Birth")) {
                        return Randomizer(1830, 1820);
                    } else if (eventType.equals("Married")) {
                        return Randomizer(1855, 1847);
                    } else if (eventType.equals("Death")) {
                        return Randomizer(1900, 1890);
                    } else {
                        return Randomizer(1889, 1832);
                    }
            }
        } else if (generationsTotal == 5) {
            switch (generation) {
                case 5:
                    if (eventType.equals("Birth")) {
                        return Randomizer(2005, 2000);
                    } else {
                        return Randomizer(2022, 2005);
                    }

                case 4:
                    if (eventType.equals("Birth")) {
                        return Randomizer(1970, 1965);
                    } else if (eventType.equals("Married")) {
                        return Randomizer(1987, 1983);
                    } else if (eventType.equals("Death")) {
                        return Randomizer(2020, 2006);
                    } else {
                        return Randomizer(1995, 1970);
                    }

                case 3:
                    if (eventType.equals("Birth")) {
                        return Randomizer(1935, 1930);
                    } else if (eventType.equals("Married")) {
                        return Randomizer(1952, 1938);
                    } else if (eventType.equals("Death")) {
                        return Randomizer(2000, 1970);
                    } else {
                        return Randomizer(1970, 1935);
                    }

                case 2:
                    if (eventType.equals("Birth")) {
                        return Randomizer(1900, 1890);
                    } else if (eventType.equals("Married")) {
                        return Randomizer(1919, 1913);
                    } else if (eventType.equals("Death")) {
                        return Randomizer(1980, 1950);
                    } else {
                        return Randomizer(1950, 1900);
                    }
                case 1:
                    if (eventType.equals("Birth")) {
                        return Randomizer(1865, 1860);
                    } else if (eventType.equals("Married")) {
                        return Randomizer(1885, 1878);
                    } else if (eventType.equals("Death")) {
                        return Randomizer(1930, 1920);
                    } else {
                        return Randomizer(1920, 1865);
                    }


            }
            return 0;
        }
        else if (generationsTotal == 4){
            switch (generation) {
                case 4:
                    if (eventType.equals("Birth")) {
                        return Randomizer(2005, 2000);
                    } else {
                        return Randomizer(2022, 2005);
                    }

                case 3:
                    if (eventType.equals("Birth")) {
                        return Randomizer(1970, 1965);
                    } else if (eventType.equals("Married")) {
                        return Randomizer(1987, 1983);
                    } else if (eventType.equals("Death")) {
                        return Randomizer(2020, 2006);
                    } else {
                        return Randomizer(1995, 1970);
                    }

                case 2:
                    if (eventType.equals("Birth")) {
                        return Randomizer(1935, 1930);
                    } else if (eventType.equals("Married")) {
                        return Randomizer(1952, 1938);
                    } else if (eventType.equals("Death")) {
                        return Randomizer(2000, 1970);
                    } else {
                        return Randomizer(1970, 1935);
                    }
                case 1:
                    if (eventType.equals("Birth")) {
                        return Randomizer(1900, 1890);
                    } else if (eventType.equals("Married")) {
                        return Randomizer(1919, 1913);
                    } else if (eventType.equals("Death")) {
                        return Randomizer(1980, 1950);
                    } else {
                        return Randomizer(1950, 1900);
                    }

            }

        }
        else if (generationsTotal == 3){
            switch (generation) {
                case 3:
                    if (eventType.equals("Birth")) {
                        return Randomizer(2005, 2000);
                    } else {
                        return Randomizer(2022, 2005);
                    }

                case 2:
                    if (eventType.equals("Birth")) {
                        return Randomizer(1970, 1965);
                    } else if (eventType.equals("Married")) {
                        return Randomizer(1987, 1983);
                    } else if (eventType.equals("Death")) {
                        return Randomizer(2020, 2006);
                    } else {
                        return Randomizer(1995, 1970);
                    }

                case 1:
                    if (eventType.equals("Birth")) {
                        return Randomizer(1935, 1930);
                    } else if (eventType.equals("Married")) {
                        return Randomizer(1952, 1938);
                    } else if (eventType.equals("Death")) {
                        return Randomizer(2000, 1970);
                    } else {
                        return Randomizer(1970, 1935);
                    }
            }

        }
        else if (generationsTotal == 2){
            switch (generation) {
                case 2:
                    if (eventType.equals("Birth")) {
                        return Randomizer(2005, 2000);
                    } else {
                        return Randomizer(2022, 2005);
                    }

                case 1:
                    if (eventType.equals("Birth")) {
                        return Randomizer(1970, 1965);
                    } else if (eventType.equals("Married")) {
                        return Randomizer(1987, 1983);
                    } else if (eventType.equals("Death")) {
                        return Randomizer(2020, 2006);
                    } else {
                        return Randomizer(1995, 1970);
                    }

            }

        }
        else if (generationsTotal == 1){
            switch (generation) {
                case 1:
                    if (eventType.equals("Birth")) {
                        return Randomizer(2005, 2000);
                    } else {
                        return Randomizer(2022, 2005);
                    }
            }

        }
        return 0;
    }

    private int Randomizer(int high, int low){
        Random r = new Random();
        return r.nextInt(high-low) + low;

    }

    public User getRandomLocation() throws FileNotFoundException {

        LocationList.LocationListStatic ff = new LocationList.LocationListStatic();
        User newLocation = ff.getRandom();

        return newLocation;
    }

    public int getNumPeople() {
        numPeople = listOfPeople.size();
        return numPeople;
    }
}
