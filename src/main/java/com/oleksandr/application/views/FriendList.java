package com.oleksandr.application.views;

import com.oleksandr.application.data.entity.Client;
import com.oleksandr.application.data.service.ClientService;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "friend-list/:id", layout = Navbar.class)
@PageTitle("Friend list")
public class FriendList extends VerticalLayout implements HasUrlParameter<Integer> {
    private final ClientService service;
    private Button checkout = new Button("Go to checkout");
    private Button browse = new Button("Go back to browsing");
    ListBox<Client> listBox = new ListBox<>();

    private Long gameId;
    private Long clientId = 1L;

    public FriendList(ClientService service) {
        this.service = service;

        listBox.setSizeFull();
        listBox.setItems(service.getFriends(clientId));
        listBox.setRenderer(new ComponentRenderer<>(person -> {
            HorizontalLayout row = new HorizontalLayout();
            row.setAlignItems(FlexComponent.Alignment.CENTER);

            Avatar avatar = new Avatar();
            avatar.setName(person.getAccountName());
            avatar.setImage(person.getProfilePictureURL());

            Span name = new Span(person.getAccountName());
            Span profession = new Span(String.valueOf(person.getId()));
            profession.getStyle()
                    .set("color", "var(--lumo-secondary-text-color)")
                    .set("font-size", "var(--lumo-font-size-s)");

            VerticalLayout column = new VerticalLayout(name, profession);
            column.setPadding(false);
            column.setSpacing(false);

            row.add(avatar, column);
            row.getStyle().set("line-height", "var(--lumo-line-height-m)");
            return row;
        }));
        add(listBox, checkout, browse);
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, Integer integer) {
        if(integer != null && integer >= 0) {
            this.gameId = Long.valueOf(integer);

            checkout.addClickListener(e -> {
                Long id = listBox.getValue().getId();
               checkout.getUI().ifPresent(ui -> ui.navigate("checkout-form/"+id+"/"+gameId));
            });
            browse.addClickListener(e -> browse.getUI().ifPresent(ui -> ui.navigate("browse")));
        }
    }
}
