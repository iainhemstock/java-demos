//==============================================================================================
// Nested classes are divided into two categories: static and non-static. A nested class that is
// declared as static is called a static nested class and a nested class that is non-static is
// called an inner class.
// A nested class is not independent of its enclosing class.
// A nested class can be declared as a member of its enclosing class or within any block scope such
// as a for loop etc.
//==============================================================================================
public class App {
    static String question = "Ultimate question of life, the universe, and everything?";
    int answer = 42;

    App() {
        //==========================================================================================
        // Classes can be defined within any block scope. For example, method, for loop and if block
        // scopes amongst others.
        //==========================================================================================
        // Inner class declared inside method (constructor)
        class MethodBlockInnerClass {}
        MethodBlockInnerClass mbic = new MethodBlockInnerClass();

        // Inner class declared inside for loop
        for (int i = 0; i < 5; ++i) {
            class ForLoopBlockInnerClass {}
            ForLoopBlockInnerClass flic = new ForLoopBlockInnerClass();
        }

        // Inner class declared inside if statement
        if (true) {
            class IfBlockInnerClass {}
            IfBlockInnerClass iic = new IfBlockInnerClass();
        }

        //==========================================================================================
        // Enclosing class instantiating inner classes
        //==========================================================================================
        // static nested class needs reference to enclosing class to access non-static memebers
        StaticNestedClass staticClass = new StaticNestedClass(this);
        // innerString.toUpperCase(); // compile-time error: cannot directly access member of inner class
        staticClass.innerString.toUpperCase(); // access public members through object instead

        InnerClass innerClass = new InnerClass();
        // innerInt += 0; // compile-time error: cannot directly access member of inner class
        innerClass.innerInt += 0; // access public members through object instead

        Runnable myRunnable = new MyRunnable();
        myRunnable.run();
    }

    static void askQuestion() {
        System.out.println(question);
    }

    void giveAnswer() {
        System.out.println(answer);
    }

    //==============================================================================================
    // A static nested class can only access static members of the enclosing directly. To access
    // non-static members it has to be done through an object.
    // Static nested classes are seldom used due to this restriction.
    //==============================================================================================
    static class StaticNestedClass {
        String innerString = "inner string";

        StaticNestedClass(App app) {
            // answer += 0; // compile-time error: cannot access non static member in enclosing class directly
            app.answer += 0; // non-static member accessed through object
            question = question.toUpperCase(); // can access static member in enclosing class directly

            askQuestion(); // static member
            app.giveAnswer(); // non-static member accessed through object
        }
    }

    //==============================================================================================
    // Non-static nested class (inner class) has direct access to static and non-static members of
    // its enclosing class.
    // An inner class cannot have static members.
    // An inner class cannot exist independently of its outer class. The outer class must be created
    // before the inner class can be created. 
    //==============================================================================================
    class InnerClass {
        int innerInt = 84;
        // static String not_allowed = ""; // compile-time error

        InnerClass() {
            answer += 0; // non-static member
            question = question.toUpperCase(); // static member

            askQuestion(); // static member
            giveAnswer(); // non-static member
        }
    }

    //==============================================================================================
    // Interfaces and abstract classes be be implemented as inner classes instead of in separate
    // files.
    //==============================================================================================
    class MyRunnable implements Runnable {
        @Override
        public void run() {
            askQuestion();
            giveAnswer();
        }
    }

    //==============================================================================================
    // Runnable is an anonymous class here because it isn't assigned to a variable.
    // Instead a class implementation is passed directly to Thread's constructor.
    //==============================================================================================
    class AnonymousInnerClassDemo {
        void someMethod() {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    askQuestion();
                    giveAnswer();
                }
            });
        }
    }

    public static void main(String[] args) {
        App app = new App();

        // instantiating static nested class
        // accessed through enclosing class type
        App.StaticNestedClass staticClass = new App.StaticNestedClass(app);

        // instantiating non-static nested class
        // accessed though instance variable
        App.InnerClass innerClass = app.new InnerClass();
    }
}
