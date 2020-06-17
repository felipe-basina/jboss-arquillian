package arquillian.poc.qualifier;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.inject.Inject;
import java.util.EnumMap;

@Singleton
public class ExampleServiceFactory {

    @Inject @ExampleQualifier(value = ExampleTypes.FIRST)
    private IExample first;

    @Inject @ExampleQualifier(value = ExampleTypes.SECOND)
    private IExample second;

    private EnumMap<ExampleTypes, IExample> exampleMap = new EnumMap<>(ExampleTypes.class);

    @PostConstruct
    public void init() {
        if (this.exampleMap.isEmpty()) {
            this.exampleMap.put(ExampleTypes.FIRST, this.first);
            this.exampleMap.put(ExampleTypes.SECOND, this.second);
        }
    }

    public IExample getServiceImplementation(final String exampleType) {
        return this.exampleMap.get(ExampleTypes.getFromString(exampleType));
    }

}
