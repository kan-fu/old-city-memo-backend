// package kanfu.old_city_memo;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.boot.CommandLineRunner;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import kanfu.old_city_memo.model.User;
// import kanfu.old_city_memo.repo.UserRepo;

// @Configuration
// public class LoadAdmin {
//     @Value("${kanfu.old_city_memo.adminPassword}")
//     private String adminPassword;

//     @Autowired
//     PasswordEncoder passwordEncoder;

//     @Bean
//     CommandLineRunner initDatabase(UserRepo userRepo) {
//         return args -> {
//             userRepo.save(
//                     new User(null, "kanfu", passwordEncoder.encode(adminPassword), "ROLE_ADMIN"));
//         };
//     }
// }
