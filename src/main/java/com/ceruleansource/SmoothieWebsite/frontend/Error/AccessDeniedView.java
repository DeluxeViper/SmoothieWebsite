package com.ceruleansource.SmoothieWebsite.frontend.Error;

import com.ceruleansource.SmoothieWebsite.frontend.MainView;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.router.*;
import com.vaadin.flow.templatemodel.TemplateModel;

import javax.servlet.http.HttpServletResponse;
import java.nio.file.AccessDeniedException;

@ParentLayout(MainView.class)
@Tag("access-denied-view")
public class AccessDeniedView extends PolymerTemplate<TemplateModel> implements HasErrorParameter<AccessDeniedException> {

    @Override
    public int setErrorParameter(BeforeEnterEvent beforeEnterEvent, ErrorParameter<AccessDeniedException> errorParameter) {
        return HttpServletResponse.SC_FORBIDDEN;
    }
}
