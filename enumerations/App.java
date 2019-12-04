/**
 * Enumerations are a list of named constants and define the legal values that an enum can have. They
 * are typically used to define a collection of values for some purpose. For example an app might
 * have an enum specifying RUNNING, PAUSED, STOPPED, CRASHED etc.
 *
 * An enumeration is actually a class so it can be instatiated. It can have constructors, methods and
 * instance variables.
 *
 * All enumerations contain two methods:
 *      values() - returns an array of the enumeration constants
 *      valueOf(String) - returns the enumeration constant that corresponds to the String arg
 *
 * An enumeration cannot explicitly inherit a superclass but implicitly inherits a java.lang.Enum. So
 * all enumerations have access to the methods in Enum.
 *
 * Conventionally uppercase is used for the enumeration constants.
 */

public class App {

    //==============================================================================================
    // Basic enum
    //==============================================================================================
    enum Fruit {
        APPLE, ORANGE, BANANA
    }

    // enum Fruit extends Integer {} // compile-time error

    //==============================================================================================
    // This enum has constructors, instance variables and methods.
    // Just like a regular class, an enum can have overloaded constructors and methods. If an
    // enumeration constant does not supply an argument then the default constructor is called. Otherwise
    // the appropriate constructor is called and the enumeration constant's arg is passed to the constructor.
    //==============================================================================================
    enum Creature {
        CAT(4), SPIDER(8), KANGAROO(2), WHALE; // notice WHALE has no arg, default constructor is called

        private int numLegs;

        Creature() {
            this.numLegs = 0;
        }

        Creature(int numLegs) {
            this.numLegs = numLegs;
        }

        int getNumberOfLegs() {
            return this.numLegs;
        }
    }

    App() {
        //==========================================================================================
        // Enums are not instatiated with 'new'.
        // The only legal values that can be assigned are the enumeration constants in Fruit.
        //==========================================================================================
        Fruit fruit = Fruit.APPLE;

        //==========================================================================================
        // Enums can be compared with == and equals(thatEnum).
        // Using == we can only compare enumeration constants of the same type.
        // Using equals(thatEnum) we can compare enumeration constants of any type.
        //==========================================================================================
        boolean areEqual = Fruit.APPLE == Fruit.BANANA; // false
        areEqual = Fruit.APPLE == Fruit.APPLE; // true
        // areEqual = Fruit.BANANA == Creature.CAT; // compile-time error

        areEqual = Fruit.APPLE.equals(Fruit.APPLE); // true
        areEqual = Fruit.APPLE.equals(Fruit.BANANA); // false
        areEqual = Fruit.BANANA.equals(Creature.CAT); // false

        //==========================================================================================
        // Enums can be used as control in a switch statement.
        // Qualifying the constant name with enum type in the case statement is not necessary as the
        // compiler infers which enum is being used from the switch parameter. In fact specifying the
        // enum type causes a compile time error
        //==========================================================================================
        switch (fruit) {
            case APPLE: break;
            case ORANGE: break;
            case BANANA: break;
            // case Fruit.APPLE: break; // compile-time error
        }

        //==========================================================================================
        // Printing the enum outputs the enum's name
        //==========================================================================================
        System.out.println(fruit); // output: APPLE

        //==========================================================================================
        // values() and valueOf()
        // values() returns an array of the enumeration constants.
        // The string arg to valueOf() is case sensitive so if the enumeration constant is defined
        // as APPLE in the enum then the string value must match exactly i.e. "APPLE" and not "apple".
        //==========================================================================================
        Fruit[] fruits = Fruit.values(); // [ APPLE, ORANGE, BANANA ]
        Fruit apple = Fruit.valueOf("APPLE"); // APPLE
        // Fruit apple = Fruit.valueOf("apple"); // compile-time error

        //==========================================================================================
        // Using the more complicated enum
        //==========================================================================================
        Creature.WHALE.getNumberOfLegs(); // value: 0
        Creature.SPIDER.getNumberOfLegs(); // value: 8

        Creature cat = Creature.CAT;
        cat.getNumberOfLegs(); // value: 4

        for (Creature c : Creature.values())
            System.out.println(c.getNumberOfLegs());

        //==========================================================================================
        // Methods inherited from Enum.
        // ordinal() returns the enumeration constant's position in the enum.
        // compareTo(enum-type) compares the ordinal value of the enum arg to the invoking enum. If
        // the invoking enum has an ordinal value less than the enum arg then a negative value is
        // returned. If the invoking enum and the enum arg have the same ordinal value then 0 is returned.
        // If the invoking enum has an ordinal value greater than the enum arg then a positive number
        // is returned.
        //==========================================================================================
        int position = Fruit.APPLE.ordinal(); // value: 0
        position = Fruit.ORANGE.ordinal(); // value: 1
        position = Fruit.BANANA.ordinal(); // value: 2

        int comparison = Fruit.ORANGE.compareTo(Fruit.APPLE); // value: positive number
        comparison = Fruit.ORANGE.compareTo(Fruit.ORANGE); // value: 0
        comparison = Fruit.ORANGE.compareTo(Fruit.BANANA); // value: negative number

    }

    public static void main(String[] args) {
        new App();
    }

}
