package DataAccess;

import Model.Event;
import Model.Person;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.util.*;

import static DataAccess.Gender.F;
import static DataAccess.Gender.M;

public class GenerateData {
    private final Connection connection;
    private int generationsTotal;
    private List<Person> listOfPeople = new LinkedList<>();
    private Queue<Integer> generationNum = new LinkedList<>();
    private int numPeople;
    EventDao addEvents;

    //Pull in the data to start the generation process
    public GenerateData(Model.User user, int generation, Connection connection) throws FileNotFoundException, DataAccessException {
        this.connection = connection;
        addEvents = new EventDao(connection);
        generationsTotal = generation;
        Gender gender;
        if (user.getGender().equalsIgnoreCase("m")){
            gender = M;
        }
        else {
            gender = F;
        }
        generatePerson(gender, generation, user);
    }

    public Person generatePerson(Gender gender, int generations, Model.User user) throws DataAccessException, FileNotFoundException {
        Person mother = null;
        Person father = null;

        if (generations > 1 ) {
            mother = generatePerson(F, generations -1, user);
            father = generatePerson(M, generations -1, user);
            //Set Spouse ID
            mother.setSpouseID(father.getPersonID());
            father.setSpouseID(mother.getPersonID());
            listOfPeople.add(mother);
            listOfPeople.add(father);

            //Add the rest of the info
            EventLocation marriageLocation = getRandomLocation();
            int marriageYear = generateYear(generations, "marriage");
            //Insert Father Marriage
            Event marEventFather = new Event(UUID.randomUUID().toString(), user.getUsername(), father.getPersonID(), marriageLocation.getLatitude(), marriageLocation.getLongitude(),
                    marriageLocation.getCountry(), marriageLocation.getCity(), "marriage", marriageYear);
            addEvents.insert(marEventFather);
            //Insert Mother Marriage
            Event marEventMother = new Event(UUID.randomUUID().toString(), user.getUsername(), mother.getPersonID(), marriageLocation.getLatitude(), marriageLocation.getLongitude(),
                    marriageLocation.getCountry(), marriageLocation.getCity(), "marriage", marriageYear);
            addEvents.insert(marEventMother);


        }
        if (generations == 1){
            //For the oldest generation
            Person personSingle = new Person(UUID.randomUUID().toString(), user.getUsername(), getRandomName("first"), getRandomName("last"),gender.toString().toLowerCase(), null, null, null);
            generationNum.add(generations);
            EventLocation location = getRandomLocation();
            Event birth = new Event(UUID.randomUUID().toString(), user.getUsername(), personSingle.getPersonID(), location.getLatitude(), location.getLongitude(),
                    location.getCountry(), location.getCity(), "birth", generateYear(generations, "birth"));
            addEvents.insert(birth);
            location = getRandomLocation();
            Event death = new Event(UUID.randomUUID().toString(), user.getUsername(), personSingle.getPersonID(), location.getLatitude(), location.getLongitude(),
                    location.getCountry(), location.getCity(), "death", generateYear(generations, "death"));
            addEvents.insert(death);

            return personSingle;

        }
        //For the user person object
        else if (((generations == 2) && (generationsTotal == 2))|| ((generations == 3) && (generationsTotal == 3)) || ((generations == 4) && (generationsTotal == 4)) || (generations == 5) && (generationsTotal == 5) || (generations == 6) && (generationsTotal == 6)) {
            Person personSingle = new Person(user.getPersonID(), user.getUsername(), user.getFirstName(), user.getLastName(),gender.toString().toLowerCase(), father.getPersonID(), mother.getPersonID(), null);
            listOfPeople.add(personSingle);
            generationNum.add(generations);
            EventLocation location = getRandomLocation();
            Event birth = new Event(UUID.randomUUID().toString(), user.getUsername(), personSingle.getPersonID(), location.getLatitude(), location.getLongitude(),
                    location.getCountry(), location.getCity(), "birth", generateYear(generations, "birth"));
            addEvents.insert(birth);
            return personSingle;

        }
        //Anyone else not either the user or the final generation
        else {
            Person person = new Person(UUID.randomUUID().toString(), user.getUsername(), getRandomName("first"), getRandomName("last"), gender.toString().toLowerCase(), father.getPersonID(), mother.getPersonID(),
                    mother.getSpouseID());
            // listOfPeople.add(person);
            generationNum.add(generations);
            EventLocation location = getRandomLocation();
            Event birth = new Event(UUID.randomUUID().toString(), user.getUsername(), person.getPersonID(), location.getLatitude(), location.getLongitude(),
                    location.getCountry(), location.getCity(), "birth", generateYear(generations, "birth"));
            addEvents.insert(birth);
            location = getRandomLocation();
            Event death = new Event(UUID.randomUUID().toString(), user.getUsername(), person.getPersonID(), location.getLatitude(), location.getLongitude(),
                    location.getCountry(), location.getCity(), "death", generateYear(generations, "death"));
            addEvents.insert(death);
            return person;
        }

    }

    //Get random names from the data generated in the server class
    public String getRandomName(String whichName) {
        if (whichName.equalsIgnoreCase("first")) {
            FirstNameList.firstNameStatic first = new FirstNameList.firstNameStatic();

            return first.getRandomFirstName();
        }
        else if (whichName.equalsIgnoreCase("middle")) {
            MiddleNameList.middleNameStatic middle = new MiddleNameList.middleNameStatic();

            return middle.getRandomMiddleName();
        }
        else if (whichName.equalsIgnoreCase("last")) {
            LastNameList.lastNameStatic last = new LastNameList.lastNameStatic();

            return last.getRandomLastName();
        }
        return null;
    }

    public List<Person> getListOfPeople(){

        return listOfPeople;
    }

    //Generate years for their events for everyone
    public int generateYear(int generation, String eventType) {
        //Year generation algorithm to give the users a good event year
        /* Generation 1: Born 2000-2005, Random Events 2005-2022
            Generation 2: Born 1965-1970, married 1983-1987, death 1995-2000
            Generation 3: Born 1930-1935, married 1938-1952, death 1970-2000
            Generation 4: Born 1890-1900 married 1913-1919 1950-1980
            Generation 5: Born 1860-1865, married 1878-1885, 1920-1930
            Generation 6: Born 1820-1830, married 1847- 1855, 1890-1900

         */
        if (generationsTotal == 6) {
            switch (generation) {
                case 6:
                    if (eventType.equals("birth")) {
                        return Randomizer(2005, 2000);
                    } else {
                        return Randomizer(2022, 2005);
                    }

                case 5:
                    return switch (eventType) {
                        case "birth" -> Randomizer(1970, 1965);
                        case "marriage" -> Randomizer(1987, 1983);
                        case "death" -> Randomizer(2020, 2006);
                        default -> Randomizer(1995, 1970);
                    };

                case 4:
                    return switch (eventType) {
                        case "birth" -> Randomizer(1935, 1930);
                        case "marriage" -> Randomizer(1952, 1938);
                        case "death" -> Randomizer(2000, 1970);
                        default -> Randomizer(1970, 1935);
                    };

                case 3:
                    return switch (eventType) {
                        case "birth" -> Randomizer(1900, 1890);
                        case "marriage" -> Randomizer(1919, 1913);
                        case "death" -> Randomizer(1980, 1950);
                        default -> Randomizer(1950, 1900);
                    };

                case 2:
                    return switch (eventType) {
                        case "birth" -> Randomizer(1865, 1860);
                        case "marriage" -> Randomizer(1885, 1878);
                        case "death" -> Randomizer(1930, 1920);
                        default -> Randomizer(1920, 1865);
                    };
                case 1:
                    return switch (eventType) {
                        case "birth" -> Randomizer(1830, 1820);
                        case "marriage" -> Randomizer(1855, 1847);
                        case "death" -> Randomizer(1900, 1890);
                        default -> Randomizer(1889, 1832);
                    };
            }
        } else if (generationsTotal == 5) {
            switch (generation) {
                case 5:
                    if (eventType.equals("birth")) {
                        return Randomizer(2005, 2000);
                    } else {
                        return Randomizer(2022, 2005);
                    }

                case 4:
                    return switch (eventType) {
                        case "birth" -> Randomizer(1970, 1965);
                        case "marriage" -> Randomizer(1987, 1983);
                        case "death" -> Randomizer(2020, 2006);
                        default -> Randomizer(1995, 1970);
                    };

                case 3:
                    return switch (eventType) {
                        case "birth" -> Randomizer(1935, 1930);
                        case "marriage" -> Randomizer(1952, 1938);
                        case "death" -> Randomizer(2000, 1970);
                        default -> Randomizer(1970, 1935);
                    };

                case 2:
                    return switch (eventType) {
                        case "birth" -> Randomizer(1900, 1890);
                        case "marriage" -> Randomizer(1919, 1913);
                        case "death" -> Randomizer(1980, 1950);
                        default -> Randomizer(1950, 1900);
                    };
                case 1:
                    return switch (eventType) {
                        case "birth" -> Randomizer(1865, 1860);
                        case "marriage" -> Randomizer(1885, 1878);
                        case "death" -> Randomizer(1930, 1920);
                        default -> Randomizer(1920, 1865);
                    };


            }
            return 0;
        }
        else if (generationsTotal == 4){
            switch (generation) {
                case 4:
                    if (eventType.equals("birth")) {
                        return Randomizer(2005, 2000);
                    } else {
                        return Randomizer(2022, 2005);
                    }

                case 3:
                    return switch (eventType) {
                        case "birth" -> Randomizer(1970, 1965);
                        case "marriage" -> Randomizer(1987, 1983);
                        case "death" -> Randomizer(2020, 2006);
                        default -> Randomizer(1995, 1970);
                    };

                case 2:
                    return switch (eventType) {
                        case "birth" -> Randomizer(1935, 1930);
                        case "marriage" -> Randomizer(1952, 1938);
                        case "death" -> Randomizer(2000, 1970);
                        default -> Randomizer(1970, 1935);
                    };
                case 1:
                    return switch (eventType) {
                        case "birth" -> Randomizer(1900, 1890);
                        case "marriage" -> Randomizer(1919, 1913);
                        case "death" -> Randomizer(1980, 1950);
                        default -> Randomizer(1950, 1900);
                    };

            }

        }
        else if (generationsTotal == 3){
            switch (generation) {
                case 3:
                    if (eventType.equals("birth")) {
                        return Randomizer(2005, 2000);
                    } else {
                        return Randomizer(2022, 2005);
                    }

                case 2:
                    return switch (eventType) {
                        case "birth" -> Randomizer(1970, 1965);
                        case "marriage" -> Randomizer(1987, 1983);
                        case "death" -> Randomizer(2020, 2006);
                        default -> Randomizer(1995, 1970);
                    };

                case 1:
                    return switch (eventType) {
                        case "birth" -> Randomizer(1935, 1930);
                        case "marriage" -> Randomizer(1952, 1938);
                        case "death" -> Randomizer(2000, 1970);
                        default -> Randomizer(1970, 1935);
                    };
            }

        }
        else if (generationsTotal == 2){
            switch (generation) {
                case 2:
                    if (eventType.equals("birth")) {
                        return Randomizer(2005, 2000);
                    } else {
                        return Randomizer(2022, 2005);
                    }

                case 1:
                    return switch (eventType) {
                        case "birth" -> Randomizer(1970, 1965);
                        case "marriage" -> Randomizer(1987, 1983);
                        case "death" -> Randomizer(2020, 2006);
                        default -> Randomizer(1995, 1970);
                    };

            }

        }
        else if (generationsTotal == 1){
            if (generation == 1) {
                if (eventType.equals("birth")) {
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
    //Get a random location for each of the person's events
    public EventLocation getRandomLocation() {

        LocationList.LocationListStatic ff = new LocationList.LocationListStatic();

        return ff.getRandom();
    }

    //Get the number of people created from this generated data
    public int getNumPeople() {
        numPeople = listOfPeople.size();
        return numPeople;
    }
}
