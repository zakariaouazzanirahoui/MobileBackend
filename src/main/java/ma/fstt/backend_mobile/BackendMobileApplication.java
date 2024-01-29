package ma.fstt.backend_mobile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication

@ComponentScan(basePackages = {"ma.fstt.backend_mobile.Services"})
@ComponentScan(basePackages = {"ma.fstt.backend_mobile.Repositories"})
@ComponentScan(basePackages = {"ma.fstt.backend_mobile.Controllers"})
@ComponentScan(basePackages = {"ma.fstt.backend_mobile.Models"})
public class BackendMobileApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendMobileApplication.class, args);
    }

}
