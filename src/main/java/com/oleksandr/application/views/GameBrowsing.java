package com.oleksandr.application.views;


import com.oleksandr.application.data.entity.Game;
import com.oleksandr.application.data.entity.GameDeveloper;
import com.oleksandr.application.data.service.GameDeveloperService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.selection.MultiSelect;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.List;
import java.util.Set;

@Route(value = "browse", layout = Navbar.class)
@PageTitle("Game browsing")
public class GameBrowsing extends VerticalLayout{

    private Grid<GameDeveloper> developerGrid = new Grid<>(GameDeveloper.class);
    private ListBox<Game> gameListBox = new ListBox<>();
    GameDeveloperService service;

    protected GameBrowsing(GameDeveloperService service){
        this.service = service;

        VerticalLayout layout = new VerticalLayout();

        developerGrid.setColumns("studioName", "email", "website", "countryOfOrigin");
        developerGrid.getColumns().forEach(col -> col.setAutoWidth(true));

        layout.add(developerGrid);
        developerGrid.setSelectionMode(Grid.SelectionMode.MULTI);

        List<GameDeveloper> gameDeveloperList = service.findAll();
        developerGrid.setItems(gameDeveloperList);

        MultiSelect<Grid<GameDeveloper>, GameDeveloper> multiSelect = developerGrid.asMultiSelect();

        Button showGames = new Button("Show games");
        Button showDescription = new Button("Show game description");

        showGames.addClickListener(e -> {
           Set<GameDeveloper> gameDevelopers = multiSelect.getSelectedItems();

           Set<Game> games = service.findAllGamesByDevelopers(gameDevelopers);
           gameListBox.setItems(games);
           gameListBox.setRenderer(new ComponentRenderer<Component, Game>(game -> new Text(game.getTitle())));
        });

        showDescription.addClickListener(e -> {
            Game game = gameListBox.getValue();
           if(game!=null){
               String route = "game-description/id/" + game.getId();
               showDescription.getUI().ifPresent(ui -> ui.navigate(route));
           }
        });


        gameListBox.setSizeFull();
        add(layout, showGames, gameListBox, showDescription);
        setSizeFull();
    }
}
