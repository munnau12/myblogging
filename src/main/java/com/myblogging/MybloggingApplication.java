package com.myblogging;

import com.myblogging.entity.Role;
import com.myblogging.repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MybloggingApplication implements CommandLineRunner {

	public static void main (String[] args) {
        SpringApplication.run(MybloggingApplication.class, args);
	}
    @Autowired
    private RoleRepository roleRepository;
	@Bean
	public ModelMapper modelMapper(){
		 return new ModelMapper();
	}

        @Override
        public void run(String... args) throws Exception {
            Role adminRole = new Role();
            adminRole.setName("ROLE_ADMIN");
            roleRepository.save(adminRole);

            Role userRole = new Role();
            userRole.setName("ROLE_USER");
            roleRepository.save(userRole);
        }
    }
