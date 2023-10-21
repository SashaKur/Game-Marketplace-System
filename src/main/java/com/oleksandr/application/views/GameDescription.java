package com.oleksandr.application.views;

import com.oleksandr.application.data.entity.Game;
import com.oleksandr.application.data.service.ClientService;
import com.oleksandr.application.data.service.GameService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.Optional;

@Route(value = "game-description/:id", layout = Navbar.class)
@PageTitle("Game description")
public class GameDescription extends VerticalLayout implements HasUrlParameter<Integer> {

    Long loggedInUserId = 2L;

    GameService service;
    ClientService clientService;

    Paragraph title = new Paragraph();
    Paragraph developer = new Paragraph();
    Paragraph description = new Paragraph();
    Paragraph releaseDate  = new Paragraph();
    Paragraph genre = new Paragraph();
    Paragraph esrb  = new Paragraph();
    Paragraph requiresVR  = new Paragraph();
    Paragraph price  = new Paragraph();

    Button yes = new Button("Yes");
    Button no = new Button("No");

    public GameDescription(GameService service, ClientService clientService){
        this.service = service;
        this.clientService = clientService;

        setAlignItems(FlexComponent.Alignment.CENTER);
        setSpacing(true);
        setPadding(true);
        getStyle().set("padding-left", "10%");
        getStyle().set("padding-right", "10%");
        setWidthFull();

        VerticalLayout descriptionLayout = new VerticalLayout();
        descriptionLayout.setWidth("65%");
        descriptionLayout.setMinWidth("240px");
        descriptionLayout.setSpacing(true);
        descriptionLayout.setPadding(false);


        descriptionLayout.getElement().getStyle().set("flex-grow", "1");


        H1 header = new H1();
        header.setText("Game description");
        header.setWidthFull();
        header.getStyle().set("text-align", "center");
        header.getStyle().set("font-weight", "bold");

        Hr upHR = new Hr();
        descriptionLayout.add(upHR);

        Div content = new Div();
        content.getElement().setProperty("innerHTML", "<h2 style=\"font-size: 24;\">Title</h2></br>");
        descriptionLayout.add(content);

        descriptionLayout.add(title);

        Hr upHR1 = new Hr();
        descriptionLayout.add(upHR1);

        Div content1 = new Div();
        content1.getElement().setProperty("innerHTML", "<h2 style=\"font-size: 24;\">Developer</h2></br>");
        descriptionLayout.add(content1);

        descriptionLayout.add(developer);

        Hr upHR2 = new Hr();
        descriptionLayout.add(upHR2);

        Div content2 = new Div();
        content2.getElement().setProperty("innerHTML", "<h2 style=\"font-size: 24;\">Description</h2></br>");
        descriptionLayout.add(content2);

        descriptionLayout.add(description);

        Hr upHR3 = new Hr();
        descriptionLayout.add(upHR3);

        Div content3 = new Div();
        content3.getElement().setProperty("innerHTML", "<h2 style=\"font-size: 24;\">Release date</h2></br>");
        descriptionLayout.add(content3);

        descriptionLayout.add(releaseDate);

        Hr upHR4 = new Hr();
        descriptionLayout.add(upHR4);

        Div content4 = new Div();
        content4.getElement().setProperty("innerHTML", "<h2 style=\"font-size: 24;\">Genre</h2></br>");
        descriptionLayout.add(content4);

        descriptionLayout.add(genre);

        Hr upHR6 = new Hr();
        descriptionLayout.add(upHR6);

        Div content6 = new Div();
        content6.getElement().setProperty("innerHTML", "<h2 style=\"font-size: 24;\">Age rating</h2></br>");
        descriptionLayout.add(content6);

        descriptionLayout.add(esrb);

        Hr upHR7 = new Hr();
        descriptionLayout.add(upHR7);

        Div content7 = new Div();
        content7.getElement().setProperty("innerHTML", "<h2 style=\"font-size: 24;\">VR requirement</h2></br>");
        descriptionLayout.add(content7);

        descriptionLayout.add(requiresVR);

        Hr upHR8 = new Hr();
        descriptionLayout.add(upHR8);

        Div content8 = new Div();
        content8.getElement().setProperty("innerHTML", "<h2 style=\"font-size: 24;\">Price</h2></br>");
        descriptionLayout.add(content8);

        descriptionLayout.add(price);

        Hr upHR9 = new Hr();
        descriptionLayout.add(upHR9);


        Button buyButton = new Button("Purchase the game",
                new Icon(VaadinIcon.ARROW_RIGHT));
        buyButton.addThemeVariants(ButtonVariant.LUMO_LARGE,
                ButtonVariant.LUMO_PRIMARY);

        // Aligns button to the right
        buyButton.getStyle().set("margin-left", "auto");

        buyButton.addClickListener(ev ->  {
            Dialog dialog = new Dialog();

            dialog.setHeaderTitle("Do you want to gift this game to your friend?");

            yes.addThemeVariants(ButtonVariant.LUMO_PRIMARY,
                    ButtonVariant.LUMO_SUCCESS);

            no.addThemeVariants(ButtonVariant.LUMO_PRIMARY,
                    ButtonVariant.LUMO_ERROR);

            dialog.getFooter().add(yes);
            dialog.getFooter().add(no);

            add(dialog);

            dialog.open();

            getStyle().set("position", "fixed").set("top", "0").set("right", "0")
                    .set("bottom", "0").set("left", "0").set("display", "flex")
                    .set("align-items", "center").set("justify-content", "center");

        });

        add(header, descriptionLayout, buyButton);
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, Integer id) {
        if(id != null && id >=0){
            Optional<Game> gameOptional = service.findGameById(id);

            if(gameOptional.isPresent()){
                Game game = gameOptional.get();
                title.setText(game.getTitle());
                description.setText(game.getDescription());
                developer.setText(game.getDeveloper().getStudioName());
                releaseDate.setText(game.getReleaseDate().toString());
                requiresVR.setText(game.isRequiresVR() ? "YES" : "NO");
                esrb.setText(game.getEsrb().label);
                price.setText(game.getPrice()+"");
                genre.setText(game.getGenre().toString());

                no.addClickListener(e -> no.getUI().ifPresent(ui -> ui.navigate("checkout-form/"+loggedInUserId+"/"+game.getId())));
                yes.addClickListener(e -> yes.getUI().ifPresent(ui -> ui.navigate("friend-list/id/"+game.getId())));
            }
        }

    }
}
