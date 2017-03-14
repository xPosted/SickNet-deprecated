package com.jubaka.sors.managed;

        import javax.annotation.PostConstruct;
        import javax.enterprise.context.ApplicationScoped;
        import javax.inject.Named;
        import javax.inject.Singleton;

/**
 * Created by root on 09.02.17.
 */
@Named
@Singleton
public class ServerConstructor {

    @PostConstruct
    public void init() {
        System.out.println("create app scoped bean");
    }
}
