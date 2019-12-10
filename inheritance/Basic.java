/**
 * The keyword 'super' id used to indicate the superclass in some capicity. It can be used to call a
 * superclass constructor (as long as it is the first command in the subclass constructor). It can
 * also specify a superclass member (as long as access is permitted through access modifiers). For
 * example, a variable and a method in a superclass can be accessed from a subclass with:
 *      super.varName
 *      super.methodName()
 *
 * super() is useful when subclasses have local variables or methods with the same names as the superclass
 * in which case the superclass versions are hidden. Using 'super' explicitly refers to the superclass'
 * versions instead.
 */

class Pet {
    private int age; // private to this class only
    protected String name; // only this class and subclasses can access

    Pet(int age, String name) {
        this.age = age;
        this.name = name;
    }

    int getAge() {
        return this.age;
    }

    @Override
    public String toString() {
        return "Pet: " + this.age + ", " + this.name;
    }
}

class Cat extends Pet {
    Cat(int age, String name) {

        // call a constructor in superclass, must be first statement in this constructor
        super(age, name);

        // age += 0; // compile-time error: Pet.age has private access
        name.toLowerCase(); // access is fine since this class is a subclass of superclass.
        super.name.toLowerCase(); // can use 'super' to indicate superclass but is redundant

        toString(); // calls this object's implementation of toString()
        super.toString(); // calls the superclass' implementation of toString();
    }

    @Override
    public String toString() {
        return "Cat: " + getAge() + ", " + name;
    }
}

public class App {
    public static void main(String[] args) {
        Cat cat = new Cat(6, "Fluffy");
    }
}
