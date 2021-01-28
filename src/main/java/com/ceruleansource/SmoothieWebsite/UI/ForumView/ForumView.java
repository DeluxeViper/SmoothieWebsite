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
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.vaadin.klaudeta.PaginatedGrid;

import javax.annotation.PostConstruct;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A Designer generated component for the forum-view template.
 * <p>
 * Designer will add and remove fields with @Id mappings but
 * does not overwrite or otherwise change this file.
 */
//@Tag("forum-view")
//@JsModule("./src/views/forum-view.js")
@PageTitle("Forum")
@Route(value = "forum", layout = MainView.class)
public class ForumView extends Div {

    private Pageable paging;

    private PaginatedGrid<Post> postsGrid;

    private FlexibleGridLayout gridLayout;

    /**
     * Creates a new ForumView.
     */
    @Autowired
    public ForumView(PostService postService) {
        H2 title = new H2("Forum View");
        title.getStyle().set("margin", "15px");

        postsGrid = new PaginatedGrid<>();
        postsGrid.addColumn(Post::getTitle).setHeader("Post Name").setAutoWidth(true);
        postsGrid.addColumn(Post::getDescription).setHeader("Description").setAutoWidth(true);
        postsGrid.addColumn(Post::getPostImage).setHeader("Image").setAutoWidth(true);
        postsGrid.addColumn(Post::getDateString).setHeader("Date Posted").setAutoWidth(true);

        postsGrid.setItems(postService.retrieveAllPosts());
        postsGrid.setHeightByRows(true);
        postsGrid.setWidthFull();

        postsGrid.addSelectionListener(selectionEvent -> {
            if (selectionEvent.getFirstSelectedItem().isPresent()){
//                System.out.println(selectionEvent.getFirstSelectedItem().get());
                getUI().ifPresent(ui -> ui.navigate(PostView.class, selectionEvent.getFirstSelectedItem().get().getId()));
            }
        });

        add(title, postsGrid);
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
//        gridLayout = new FlexibleGridLayout()
//                .withColumns(Repeat.RepeatMode.AUTO_FILL, new MinMax(new Length("1000px"), new Flex(1)))
//                .withAutoRows(new Length("300px"))
//                .withItems(
//                        new ExampleCard(), new ExampleCard(), new ExampleCard(), new ExampleCard(), new ExampleCard(),
//                        new ExampleCard(), new ExampleCard(), new ExampleCard(), new ExampleCard(), new ExampleCard(),
//                        new ExampleCard(), new ExampleCard(), new ExampleCard(), new ExampleCard(), new ExampleCard(), new ExampleCard(), new ExampleCard(), new ExampleCard()
//                )
//                .withPadding(true)
//                .withSpacing(true)
//                .withAutoFlow(GridLayoutComponent.AutoFlow.ROW_DENSE)
//                .withOverflow(GridLayoutComponent.Overflow.AUTO);
//        gridLayout.setSizeFull();
//        setSizeFull();
//        add(h1, gridLayout);
    }

}
