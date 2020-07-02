package de.example.boot;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@SpringBootApplication
public class BootApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootApplication.class, args);
    }

}

@Entity
@NoArgsConstructor
@ToString
@Getter
class Cat {
    @Id
    @GeneratedValue()
    private Long id;
    private String name;

    public Cat(String name) {
        this.name = name;
    }
}

@RepositoryRestResource
interface CatRepository extends JpaRepository<Cat, Long> {

}
