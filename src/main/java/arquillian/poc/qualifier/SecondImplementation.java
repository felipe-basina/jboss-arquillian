package arquillian.poc.qualifier;

@ExampleQualifier(value = ExampleTypes.SECOND)
public class SecondImplementation implements IExample {

    @Override
    public void printOrderNumber() {
        System.out.println("The second implementation");
    }

}
