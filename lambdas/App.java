/**
 * A Lambda expression contains three parts:
 *      the left hand side specifies zero or more parameters to be used inside the expression
 *      the lambda operator (or arrow operator)
 *      the right side specifies the lambda body (either a single statement or multiple statements defined in a block)
 *
 * The lambda expression forms the implementation of the abstract method defined in the functional interface
 * So the expression () -> 123 is the implementation of getValue() i.e. the method returns 123
 *
 * A lambda can only be specified in a context where a target type is defined. So a lambda can be assigned
 * to a variable whose type is the same type as the functional interface:
 *      MyNumber num = () -> 123;
 * or passed to a method that accepts the functional interface as an argument:
 *      doSomething(() -> 123);
 *      ...
 *      private void doSomething(MyNumber num);
 *
 * A functional interface is just a standard interface with the restriction that it only contains
 * a single abstract method.
 *
 * In general a functional interface could be quite general meaning that multiple and varying lambda
 * implementations can be written for that interface
 */

public class App {

    interface MyNumber {
        double getValue();
    }

    interface NumberTest {
        boolean test(int n);
    }

    interface NumberTest2 {
        boolean test(int n, int d);
    }

    interface NumberFunc {
        int func(int n);
    }

    interface StringFunc {
        String func(String s);
    }

    interface MyConstructorFunc {
        MyClass func(int i);
    }

    public static void main(String[] args) {
        new App();
    }

    public App () {

        /**
         * [0] lambda with no params
         */
        MyNumber test = () -> 123;
        System.out.println(test.getValue());


        /**
         * [1] lambda with single param
         * for single param lambdas the param parentheses are optional
         */
        NumberTest isEven = n -> (n % 2) == 0;
        System.out.println(isEven.test(10));
        System.out.println(isEven.test(9));

        // lambda paramter types do not need to be specified as it is inferred from the interface's
        // method signature. It doesn't cause an error to explicitly specify it though.
        NumberTest isNeg = (int n) -> n < 0;
        System.out.println(isNeg.test(10));
        System.out.println(isNeg.test(-5));


        /**
         * [2] multiple params
         * note that parentheses are necessary for multiple params
         */
        NumberTest2 isFactor = (n, d) -> (n % d) == 0;
        System.out.println(isFactor.test(2, 5));
        System.out.println(isFactor.test(15, 5));


        /**
         * [3] a block lambda
         */
        NumberFunc factorial = n -> {
            int result = 1;
            for (int i = 1; i <= n; ++i) {
                result = i * result;
            }
            return result;
        };
        System.out.printf("Factorial of 3 is %d %n", factorial.func(3));
        System.out.printf("Factorial of 5 is %d %n", factorial.func(5));


        /**
         * [4] passing lambda as argument
         */
        String inString = "hello world!";
        String outString = processString(s -> s.toUpperCase(), inString);
        System.out.println(outString);


        /**
         * [5] static method references
         * refer to a static method of a class with class name and double colon syntax (::)
         *      ClassName::staticMethod
         * A reference to the static MyStringOps::reverse method is passed to the processString()
         * function expecting a functional interface. This works because the reverse method and the
         * functional interface are compatible with each other (i.e. have the same signature).
         */
         outString = processString(MyStringOps::reverse, inString);
         System.out.println(outString);


         /**
          * [6] instance method references
          * refer to an instance method of a class with object reference and double colon syntax (::)
          *     objRef::instanceMethod
          */
          MyStringOps ops = new MyStringOps();
          outString = processString(ops::reverseString, "this will get reversed");
          System.out.println(outString);


          /**
           * [7] constructor references
           * refer to a class constructor with the form:
           *    ClassName::new
           */
           MyConstructorFunc myClassConstructor = MyClass::new;
           MyClass myClass = myClassConstructor.func(100);
           System.out.println(myClass.toString());
    }

    /**
     * A general string processing function that accepts a lambda as an argument.
     * It knows to invoke the single method of the interface but doesn't know or care what it does.
     * This way multiple different string processing routines could be supplied to this function.
     */
    private String processString(StringFunc stringFunc, String s) {
        return stringFunc.func(s);
    }
}
