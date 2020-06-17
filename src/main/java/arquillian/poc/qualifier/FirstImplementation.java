package arquillian.poc.qualifier;

@ExampleQualifier(value = ExampleTypes.FIRST)
public class FirstImplementation implements IExample {

    @Override
    public void printOrderNumber() {
        System.out.println("The first implementation");
    }

}
