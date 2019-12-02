/**
 * Parameterized types can only be reference types so primitive types such as int, boolean etc cannot
 * be used. In those cases just use the primitive wrapper classes such as Integer and Boolean.
 *
 * There is no inheritance relationship between two generic classes where their parameterized types
 * do have an inheritance relationship. A List<Number> is not a supertype of a List<Integer> even though
 * a Number is a supertype of an Integer. Therefore a method that defines List<Number> as a parameter
 * cannot accept a List<Integer> as an argument due to the fact that it could invalidate type safety.
 *
 * It is not possible to instantiate a parameterized type i.e. new T() due to type erasure.
 *
 * It is also not possible to create arrays of generic objects:
 *      List<String>[] stringListArray = new List<String>[1]; // compile-time eror
 *
 * General form of a generic class:
 *      class class-name<parameterized-type-list> {}
 *
 * General form of reference to generic class:
 *      class-name<parameterized-type-list> var-name = new class-name<>(constructor-arg-list);
 *
 * General form of upper bounded wildcard:
 *      <? extends super-class>
 *
 * General form of lower bounded wildcard:
 *      <? super sub-class>
 *
 * General form of generic constructor:
 *      <parameterized-type-list> constructor-name(args...) {}
 *
 * General form of generic method:
 *      <parameterized-type-list> return-type methodName(args...) {}
 */

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class App {

    //==============================================================================================
    // Customary to name parameterized type as T
    //==============================================================================================
    class Generic<T> {}

    //==============================================================================================
    // Can have multiple parameterized types
    //==============================================================================================
    class MultiParamGeneric<T, U> {}

    //==============================================================================================
    // It is possible to restrict which types are valid for T by using an upper bound.
    // In this example using the 'extends' keyword forces T to be upper bounded as Number or any
    // subclass of Number. Anything higher than Number in the inheritance chain is disallowed.
    //==============================================================================================
    class UpperBoundGeneric<T extends Number> {}

    //==============================================================================================
    // Wilcards are placeholders for object types.
    // Class declaration cannot contain a wildcard:
    //==============================================================================================
    // class MyClass<?> {} // compile-time error
    // class MyClass<? super Number> {} // compile-time error
    // class MyClass<? extends Number> {} // compile-time error
    class WildcardGeneric<T extends Number> {
        List<T> thisList;

        // This unbounded wildcard matches ANY object type so a list of any type would be allowed as argument.
        //
        // The list's type could be anything and the method body might make assumptions about it and
        // try to call methods that don't exist.
        //
        // In a limited fashion it could be useful if you only need to know that the type is an Object
        // and are not intending to read or write to the object.
        //
        // With an unbounded wildcard it is not possible to write to an object of that type as we don't
        // know what type it is. We can only treat it as an Object.
        void unboundedWilcard(List<?> thatList) {
            Object obj = thatList.get(0); // what is this object's real type?
            // Number obj = thatList.get(0); // compile-time error
            thatList.add(null); // other than null, nothing can be written to this list
            // thatList.add(Integer.valueOf(0)); // compile-time error
        }

        // This wildcard is upper bounded by Number which says that the lists's type can be a Number
        // or any subclass of Number. The method body can now safely treat each element as a Number
        // as the unknown type, possibly through inheritance, is ultimately a Number.
        //
        // Cannot write to this list as we don't know which descendant of Number the unknown type
        // is. If it was an Integer list then it would be wrong if we could add a Float here.
        void upperBoundedWildcard(List<? extends Number> thatList) {
            Number obj = thatList.get(0);
            obj.intValue();

            // Casting is possible if the unknown type is somehow known for definite.
            // ClassCastException will be thrown otherwise.
            Integer i = (Integer) thatList.get(0);

            // Only possible to write null to list
            thatList.add(null);
            // thatList.add(Float.valueOf(3.14f)); // compile-time error
        }

        // This wildcard is lower bounded by Number. Using the 'super' keyword it says that the
        // list's type can be a Number or any of its superclasses. In this case the unknown type
        // is either Number or Object.
        // In the method body we can only safely treat the unknown type as Object as the actual type
        // is either Number or Object in the inheritance chain and we have no way of knowing
        // which one.
        void lowerBoundedWildcard(List<? super Number> thatList) {
            Object obj = thatList.get(0);
            // Number obj = thatList.get(0); // compile-time error

            // Casting is possible if unknown type is definitly known.
            // ClassCastException thrown if in error.
            Integer i = (Integer) thatList.get(0);
            Number n = (Number) thatList.get(0);

            // We can write any type to this list as long as the type appears in the ancestry of Number.
            // Note that any subclass of Number can be added since the list is defined as containing
            // Numbers and the usual inheritance substitution rules apply.
            thatList.add(Integer.valueOf(0));
            thatList.add(Float.valueOf(3.14f));
        }
    }

    //==============================================================================================
    // Generic interfaces are specified just the same as generic classes.
    //==============================================================================================
    interface GenericInterface<T extends Comparable<T>>{}
    class NonGenericClassWithGenericInterface implements GenericInterface<String> {}
    class GenericClassWithGenericInterface<T extends Comparable<T>> implements GenericInterface<T> {}

    //==============================================================================================
    // A non-generic class can contain generic constructors and methods.
    // The parameterized type/s appear before the return type of the method signature.
    //==============================================================================================
    public App() {}
    public <T> App(T t) {}
    public <T> void genericMethod(T t) {}

    public static void main(String[] args) {
        App app;

        // When calling a generic method it is usually not necessary to specify the parameterized type
        // as the compiler can work it out based on the argument being passed. The type must satisfy
        // any bounds placed on it.
        // On those occasions when necessary the parameterized type can be specified.
        app = new App(); // using non-generic constructor
        app.genericMethod(12);
        app.<Integer>genericMethod(12);

        // Calling generic constructor usually doesn't require explicitly specifying the parameterized
        // type but it can be if necessary.
        app = new App(123); // calling generic constructor
        app = new <Integer>App(123); // calling generic constructor

        // Cannot create arrays of generic objects
        // List<String>[] stringLists = new ArrayList<String>[1]; // compile-time error
    }
}
