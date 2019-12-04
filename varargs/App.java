/**
 * Varargs allow zero or more arguments to passed to a method.
 *
 * The compiler implicitly implements varargs as an array so array syntax is used to access the args.
 */

public class App {

    //==============================================================================================
    // Using array syntax to access args
    //==============================================================================================
    App(int ... args) {
        for (int i = 0; i < args.length; ++i) {
            int arg = args[i];
        }
    }

    //==============================================================================================
    // Regular args can passed too as long as the varargs is passed last.
    // The first arg passed will be assigned to the boolean whilst each succeeding args will be assigned
    // to the varargs array.
    //==============================================================================================
    App(boolean isTrue, int ... args) {
        if (isTrue) {
            for (int arg : args) {}
        }
    }

    // App(int ... args, boolean isTrue) {} // compile-time error

    //==============================================================================================
    // Vararg methods can be overloaded although can cause ambiguity.
    // Does a call to InnerApp() with zero args mean to call InnerApp(String ...) or InnerApp(int ...)?
    // Does a call to InnerApp(5) mean to call InnerApp(int ...) or InnerApp(int, int ...)?
    // There is no built in way to overcome this ambiguity. Perhaps the overloading will have to be
    // abandoned and differently named methods used instead.
    //==============================================================================================
    class InnerApp {
        InnerApp(int ... args) {}
        InnerApp(String ... args) {}
        InnerApp(int i, int ... args) {}
    }

    public static void main(String[] args) {
        new App();
        new App(1);
        new App(1, 2);
        new App(1, 2, 3);

        new App(true, 1, 2, 3);
    }
}
