package com.ceruleansource.SmoothieWebsite.UI.ForumView;

import com.ceruleansource.SmoothieWebsite.UI.MainView.MainView;
import com.ceruleansource.SmoothieWebsite.backend.Models.Post;
import com.ceruleansource.SmoothieWebsite.backend.Services.PostService;
import com.github.appreciated.css.grid.GridLayoutComponent;
import com.github.appreciated.css.grid.sizes.Flex;
import com.github.appreciated.css.grid.sizes.Length;
import com.github.appreciated.css.grid.sizes.MinMax;
import com.github.appreciated.css.grid.sizes.Repeat;
import com.github.appreciated.layout.FlexibleGridLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.page.ExtendedClientDetails;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.vaadin.klaudeta.PaginatedGrid;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@PageTitle("Forum")
@Route(value = "forum", layout = MainView.class)
public class ForumView extends Div {

    private Pageable paging;

    private TextField searchPostsField = new TextField();

    private PaginatedGrid<Post> postsGrid;

    // TODO: Implement Pagination for gridLayout
    private FlexibleGridLayout gridLayout;

    private int windowWidth;

    /**
     * Creates a new ForumView.
     */
    @Autowired
    public ForumView(PostService postService) {
        H1 title = new H1("Public Forum");
        title.setId("title");

        searchPostsField.setPlaceholder("Filter by smoothie name...");
        searchPostsField.setClearButtonVisible(true);
        searchPostsField.setValueChangeMode(ValueChangeMode.EAGER);
        searchPostsField.setId("search-field");
        postsGrid = new PaginatedGrid<>();
        postsGrid.addColumn(Post::getTitle).setHeader("Post Name").setAutoWidth(true);
        postsGrid.addColumn(Post::getDescription).setHeader("Description").setAutoWidth(true);
        postsGrid.addColumn(Post::getPostImage).setHeader("Image").setAutoWidth(true);
        postsGrid.addColumn(Post::getDateString).setHeader("Date Posted").setAutoWidth(true);

        postsGrid.setItems(postService.retrieveAllPosts());
        postsGrid.setHeightByRows(true);
        postsGrid.setWidthFull();

        postsGrid.addSelectionListener(selectionEvent -> {
            if (selectionEvent.getFirstSelectedItem().isPresent()) {
                System.out.println(selectionEvent.getFirstSelectedItem().get());
                getUI().ifPresent(ui -> ui.navigate(PostView.class, selectionEvent.getFirstSelectedItem().get().getId()));
            }
        });

//        add(title, postsGrid);
//        Page page;
//        Optional<UI> ui = this.getUI();
//        if (ui.isPresent()){
//            page = ui.get().getPage();
//            page.addBrowserWindowResizeListener(browserWindowResizeEvent -> {
//
//            });
//            page.retrieveExtendedClientDetails(extendedClientDetails -> {
//                width.set(extendedClientDetails.getScreenWidth());
//            });
//        }
//
//        H1 h1 = new H1("Forum View");
//        System.out.println(width.get());
        UI.getCurrent().getPage().retrieveExtendedClientDetails(new Page.ExtendedClientDetailsReceiver() {
            @Override
            public void receiveDetails(ExtendedClientDetails extendedClientDetails) {
                windowWidth = extendedClientDetails.getScreenWidth();
                System.out.println("Windowwidth: " + windowWidth);
                windowWidth = extendedClientDetails.getWindowInnerWidth();
                System.out.println("Windowwidth: " + windowWidth);
                System.out.println(extendedClientDetails.getBodyClientWidth());
            }
        });

        List<SmoothiePostCard> postCards = populateSmoothiePostCards(postService, null);
        gridLayout = new FlexibleGridLayout()
//                .withAutoFlow(GridLayoutComponent.AutoFlow.toAutoFlow("row | column | row dense | column dense"))
                .withColumns(Repeat.RepeatMode.AUTO_FILL, new MinMax(new Length("500px"), new Flex(1)))
                .withAutoRows(new Length("800px"))
                .withItems(
                        postCards.toArray(new Component[0])
                )
                .withPadding(true)
                .withSpacing(true)
                .withAutoFlow(GridLayoutComponent.AutoFlow.ROW_DENSE)
                .withOverflow(GridLayoutComponent.Overflow.AUTO);
        gridLayout.setSizeFull();

        searchPostsField.addValueChangeListener(e -> {
            gridLayout.removeAll();
            List<SmoothiePostCard> smoothiePostCards = populateSmoothiePostCards(postService, e.getValue());
            gridLayout.withItems(smoothiePostCards.toArray(new Component[0]));
        });
        setSizeFull();
        add(title, searchPostsField, gridLayout);
    }

    private List<SmoothiePostCard> populateSmoothiePostCards(PostService postService, String filter) {
        List<SmoothiePostCard> postCards;
        if (filter == null) {
            Set<Post> postsList = new HashSet<>(postService.retrieveAllPosts());
            postCards = new ArrayList<>();
            for (Post post : postsList) {
                postCards.add(new SmoothiePostCard(post));
            }
        } else {
            List<Post> postsList = new ArrayList<>(postService.getPostsByFilter(filter));
            postCards = new ArrayList<>();
            for (Post post : postsList) {
                postCards.add(new SmoothiePostCard(post));
            }
        }

        return postCards;
    }
}
