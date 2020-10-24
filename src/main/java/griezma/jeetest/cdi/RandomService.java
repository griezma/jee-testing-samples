package griezma.jeetest.cdi;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class RandomService {
    public double getRandom() {
        return Math.random();
    }
}
