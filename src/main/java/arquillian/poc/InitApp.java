package arquillian.poc;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import arquillian.poc.composite.pk.CompoundPKAPI;

@ApplicationPath("/rest")
public class InitApp extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> s = new HashSet<Class<?>>();
        s.add(PessoaAPI.class);
        s.add(CompoundPKAPI.class);
        return s;
    }

}
