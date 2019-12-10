/**
 * Any class that contains one or more abstract methods must be marked as abstract itself.
 *
 * Sometimes a behaviour is required for all general types but it doesn't make sense to implement
 * it in a superclass. For example, a Shape cannot calculate an area for all shape subclasses.
 * Therefore the contract is specified in the abstract Shape class and each subclass can implement
 * it in a way that makes sense for its type.
 *
 * Abstract classes can have regular instance variables and methods too. Since an abstract class
 * cannot be instantiated these members are accessed through inheritance in a subclass.
 *
 * The use of 'final' on a class declaration means that a class cannot be subclassed.
 * The use of 'final' on a method means that a subclass cannot override that method.
 */

abstract class Shape {
    private int color;

    abstract double calculateArea();

    // note that this method is marked as final
    final int getColor() {
        return this.color;
    }
}

class Square extends Shape {
    int length = 8;

    double calculateArea() {
        return length * length;
    }

    // compile-time error: cannot override final method in superclass
    // @Override
    // int getColor() {
    //     return 0;
    // }
}

// Note that Circle is marked as final. This class cannot be subclassed.
final class Circle extends Shape {
    double PI = 3.14;
    double radius = 5;

    double calculateArea() {
        return PI * radius * radius;
    }
}

// class BigCircle extends Circle {} // compile-time error: final class cannot be subclassed

public class Abstract {
    public static void main(String[] args) {
        new Square();
        new Circle();
    }
}
