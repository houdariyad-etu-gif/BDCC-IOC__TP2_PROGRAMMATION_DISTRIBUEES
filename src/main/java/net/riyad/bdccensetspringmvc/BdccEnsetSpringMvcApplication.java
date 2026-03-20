package net.riyad.bdccensetspringmvc;


import net.riyad.bdccensetspringmvc.entities.Product;
import net.riyad.bdccensetspringmvc.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

//@SpringBootApplication(exclude = {securityAutoConfirmation.class})
@SpringBootApplication
public class BdccEnsetSpringMvcApplication{

    public static void main(String[] args) {
        SpringApplication.run(BdccEnsetSpringMvcApplication.class, args);
    }

    @Bean  //pour faire un traitement au demarage de notre application on cree un bean
    public CommandLineRunner start(ProductRepository productRepository){
        return args -> {
            productRepository.save(Product.builder()
                    .name("Computer")
                    .price(5400)
                    .quantity(12)
                    .build());
            productRepository.save(Product.builder()
                    .name("Printer")
                    .price(12)
                    .quantity(11)
                    .build());
            productRepository.save(Product.builder()
                    .name("Smart phone")
                    .price(12000)
                    .quantity(33)
                    .build());
            productRepository.save(Product.builder()
                    .name("Iphone")
                    .price(4400)
                    .quantity(10)
                    .build());
            productRepository.findAll().forEach(p->{
                System.out.println(p.toString());
            });
        };
    }
}

