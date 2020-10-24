package griezma.jeetest.cdi;

import javax.enterprise.context.RequestScoped;

public class StaticClassBean {

    @RequestScoped
    public static class Managed {

    }
    
}
