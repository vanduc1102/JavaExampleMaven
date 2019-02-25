package my.example.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class AnnotationParsing {

  private static final String PACK_NAME = "my.example.annotation.";

  public static void main(String[] args) {
    try {
      for (Method method :
          AnnotationParsing.class
              .getClassLoader()
              .loadClass((PACK_NAME.concat("AnnotationExample")))
              .getMethods()) {
        // checks if MethodInfo annotation is present for the method
        if (method.isAnnotationPresent(my.example.annotation.MethodInfo.class)) {
          System.out.println(
              String.format(
                  "%d================%s==================",
                  System.currentTimeMillis(), method.getName()));
          try {
            // iterates all the annotations available in the method
            for (Annotation anno : method.getDeclaredAnnotations()) {
              System.out.println("Annotations: " + anno);
            }
            MethodInfo methodAnno = method.getAnnotation(MethodInfo.class);
            if (methodAnno.revision() == 1) {
              System.out.println("Revision no 1.");
            }

          } catch (Throwable ex) {
            ex.printStackTrace();
          }
        }
      }
    } catch (SecurityException | ClassNotFoundException e) {
      e.printStackTrace();
    }
  }
}
