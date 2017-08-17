import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by sasha on 17.07.17.
 */
@ApplicationPath("rest")
public class RestConfig extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        HashSet h = new HashSet<Class<?>>();
        h.add( OperationManagerBean.class );
        return h;
    }
}
