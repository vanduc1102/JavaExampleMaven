package my.example.velocity;

/**
 *
 * @author nvduc
 */
import java.io.StringWriter;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
public class VelocityTest
{
    public static void main( String[] args )
        throws Exception
    {
        System.out.println("-------------- VelocityTest is running -----------------");
        /*  first, get and initialize an engine  */
        VelocityEngine ve = new VelocityEngine();
        ve.init();
        /*  next, get the Template  */
        Template t = ve.getTemplate( "helloworld.vm" );
        /*  create a context and add data */
        VelocityContext context = new VelocityContext();
        context.put("name", "World");
        /* now render the template into a StringWriter */
        StringWriter writer = new StringWriter();
        t.merge( context, writer );
        /* show the World */
        System.out.println( writer.toString() );     
    }
}