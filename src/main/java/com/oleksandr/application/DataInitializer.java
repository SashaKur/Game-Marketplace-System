package com.oleksandr.application;


import com.oleksandr.application.data.entity.*;
import com.oleksandr.application.data.entity.employee.Employee;
import com.oleksandr.application.data.entity.employee.EmployeeType;
import com.oleksandr.application.data.entity.enums.ESRB;
import com.oleksandr.application.data.entity.enums.Genre;
import com.oleksandr.application.data.entity.enums.PostReaction;
import com.oleksandr.application.data.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final ClientRepository clientRepository;
    private final EmployeeRepository employeeRepository;
    private final GameDeveloperRepository gameDeveloperRepository;
    private final GameRepository gameRepository;
    private final TournamentRepository tournamentRepository;
    private final SupportTicketRepository supportTicketRepository;
    private final MessageRepository messageRepository;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final PaymentRepository paymentRepository;

    @EventListener
    public void atStart(ContextRefreshedEvent ev){

        Client client1 = Client.builder()
                .accountName("john.doe")
                .email("johndoe@example.com")
                .profilePictureURL("https://api.dicebear.com/6.x/bottts-neutral/svg?seed=Loki")
                .preferredGenres(Set.of(Genre.Horror, Genre.RPG))
                .build();

        Client client2 = Client.builder()
                .accountName("jane.smith")
                .profilePictureURL("https://api.dicebear.com/6.x/open-peeps/svg?seed=Milo")
                .email("janesmith@example.com")
                .preferredGenres(Set.of(Genre.RPG, Genre.Stealth))
                .build();

        Client client3 = Client.builder()
                .accountName("alice.jones")
                .email("alicejones@example.com")
                .profilePictureURL("https://api.dicebear.com/6.x/bottts-neutral/svg?seed=Loki")
                .preferredGenres(Set.of(Genre.Stealth, Genre.Horror))
                .build();

        Client client4 = Client.builder()
                .accountName("bob.wilson")
                .email("bob.wilson@example.com")
                .profilePictureURL("https://api.dicebear.com/6.x/bottts-neutral/svg?seed=Loki")
                .preferredGenres(Set.of(Genre.Arcade, Genre.RPG))
                .build();

        client1.getReferencesTo().add(client2);
        client1.getReferencesTo().add(client4);
        client2.getReferencesTo().add(client3);
        client3.getReferencesTo().add(client4);

        clientRepository.save(client4);
        clientRepository.save(client3);
        clientRepository.save(client2);
        clientRepository.save(client1);



        // Create a new message
        Message message1 = new Message();
        message1.setSender(client1);
        message1.setReceiver(client2);
        message1.setMessage("Hello Jane, how are you?");
        message1.setTimeSent(LocalDateTime.now());

        Message message2 = new Message();
        message2.setSender(client2);
        message2.setReceiver(client1);
        message2.setMessage("Hi John, I'm doing well. How about you?");
        message2.setTimeSent(LocalDateTime.now());




        GameDeveloper gameDeveloper1 = GameDeveloper.builder()
                .studioName("DVD Projekt RED")
                .email("contact@examplestudios.com")
                .website("http://www.examplestudios.com")
                .countryOfOrigin("Poland")
                .build();


        GameDeveloper gameDeveloper2 = GameDeveloper.builder()
                .studioName("Another Studio")
                .email("info@anotherstudio.com")
                .website("http://www.anotherstudio.com")
                .countryOfOrigin("Canada")
                .build();

        GameDeveloper gameDeveloper3 = GameDeveloper.builder()
                .studioName("GameDev Co.")
                .email("contact@gamedevco.com")
                .website("http://www.gamedevco.com")
                .countryOfOrigin("United Kingdom")
                .build();


        EnumSet<EmployeeType> types1 = EnumSet.of(EmployeeType.Moderator);
        EnumSet<EmployeeType> types2 = EnumSet.of(EmployeeType.Orginizer);
        EnumSet<EmployeeType> types3 = EnumSet.of(EmployeeType.Moderator, EmployeeType.Support);

        Employee moderator = new Employee(types1, 1L, "John", "Doe", LocalDate.of(1990, 5, 15), 4000, null, null, 4.5);
        Employee orginizer = new Employee(types2, 2L, "Alice", "Smith", LocalDate.of(1988, 8, 25), 12000, null, Set.of(Genre.Arcade), null);
        Employee moderatorAndSupport = new Employee(types3, 3L, "Michael", "Johnson", LocalDate.of(1992, 3, 10), 7000, Set.of("English", "Polish"), null, 4.5);

        moderator.changeRole(EnumSet.of(EmployeeType.Support), Set.of("Polish"), null, null);

        Game game2 = Game.builder()
                .title("Hello World")
                .description("Game 2 description")
                .releaseDate(LocalDate.of(2023, 2, 15))
                .genre(Genre.RPG)
                .platforms(Set.of("Linux", "Windows"))
                .esrb(ESRB.T)
                .requiresVR(false)
                .price(49.99)
                .developer(gameDeveloper1)
                .build();

        Game game3 = Game.builder()
                .title("Very fun game")
                .description("Game 3 description")
                .releaseDate(LocalDate.of(2017, 3, 20))
                .genre(Genre.Arcade)
                .platforms(Set.of("PS5", "XBox"))
                .esrb(ESRB.A)
                .requiresVR(false)
                .price(39.99)
                .developer(gameDeveloper2)
                .build();

        Game game4 = Game.builder()
                .title("Hard Puzzle Game")
                .description("Game 4 description")
                .releaseDate(LocalDate.of(2020, 4, 25))
                .genre(Genre.Puzzle)
                .platforms(Set.of("Nintendo Switch", "PS5"))
                .esrb(ESRB.RP)
                .requiresVR(false)
                .price(59.99)
                .developer(gameDeveloper1)
                .build();


        Post post1 = Post.builder()
                .title("Sample Post")
                .text("This is the content of the post.")
                .imagesURL(Arrays.asList("image1.jpg", "image2.jpg"))
                .author(client1)
                .build();

        Post post2 = Post.builder()
                .title("Sample Post2")
                .text("This is the content of the post.")
                .imagesURL(Arrays.asList("image1.jpg", "image2.jpg"))
                .author(client1)
                .build();


        Post post3 = Post.builder()
                .title("Sample Post3")
                .text("This is the content of the post.")
                .imagesURL(Arrays.asList("image1.jpg", "image2.jpg"))
                .author(client2)
                .build();

        Post post4 = Post.builder()
                .title("Sample Post4")
                .text("This is the content of the post.")
                .imagesURL(Arrays.asList("image1.jpg", "image2.jpg"))
                .author(client3)
                .build();


        SupportTicket supportTicket1 = SupportTicket.builder().title("Issue 1").description("12345").issuer(client1).customerSupport(moderatorAndSupport).build();

        Comment comment = Comment.builder().commentator(client2).post(post1).postReaction(PostReaction.Funny).text("Very funny").build();


        messageRepository.save(message1);
        messageRepository.save(message2);

        gameDeveloperRepository.save(gameDeveloper1);
        gameDeveloperRepository.save(gameDeveloper2);
        gameDeveloperRepository.save(gameDeveloper3);

        employeeRepository.save(moderator);
        employeeRepository.save(orginizer);
        employeeRepository.save(moderatorAndSupport);


        gameRepository.save(game2);
        gameRepository.save(game3);
        gameRepository.save(game4);


        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.save(post3);
        postRepository.save(post4);

        supportTicketRepository.save(supportTicket1);
        commentRepository.save(comment);



        System.out.println("Context has been refreshed");
    }
}
