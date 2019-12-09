/**
 * Sometimes a method or variable is desired to be independent of a particular instance of an object.
 * Only one copy of this static member resides in memory and all instances share access to it. Therefore
 * a static member belongs to a type rather than to an instance of that type.
 *
 * Things that can be marked as static:
 *      class
 *      nested class
 *      method
 *      variable
 *      static block
 *
 * When a class is first loaded all of the static variables are initialized first and any intialization
 * code in a static block is run.
 *
 * Public members are accessed through the dot notation:
 *      println(App.meaningOfLife);
 *      println(App.getMeaningOfLife());
 * Although it is possible to access static members through an object reference it should be avoided
 * due to causing confusion as to whether it is a static or instance member.
 *
 * Static members can only access other static members. To access non-static members then an instance
 * of the object needs to be created and then access is allowed through that.
 *
 * Because static members are accessed through the type and not an instance it is obvious that it
 * cannot use 'this' or 'super' as there is no object to operate on.
 */
import java.util.List;
import java.util.Arrays;
import java.util.LinkedList;

public class App {
    static int meaningOfLife = 42;

    // static block initializes static variables that haven't been initialized yet or are unable to
    // be initialized at the same time as the declaration
    static double PI;
    static List<Integer> numbers = new LinkedList<>();
    static {
        PI = 3.14;
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
    }

    static String str;  // uninitialized variables are assigned their default value:
                        // 0 for numbers, false for booleans, null for objects

    static int getMeaningOfLife() {
        return meaningOfLife;
    }

    static class StaticNestedClass {}

    public static void main(String[] args) {
        App.StaticNestedClass clazz = new App.StaticNestedClass();
    }
}
