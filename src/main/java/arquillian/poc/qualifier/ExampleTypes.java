package arquillian.poc.qualifier;

import java.util.stream.Stream;

public enum ExampleTypes {

    FIRST, SECOND;

    public static ExampleTypes getFromString(final String exampleType) {
        return Stream.of(ExampleTypes.values())
                .filter(example -> exampleType.equalsIgnoreCase(example.name()))
                .findFirst().get();
    }

}
