import {html, PolymerElement} from '@polymer/polymer/polymer-element.js';
import '@vaadin/vaadin-ordered-layout/src/vaadin-vertical-layout.js';
import '@vaadin/vaadin-tabs/src/vaadin-tabs.js';
import '@vaadin/vaadin-tabs/src/vaadin-tab.js';
import '@vaadin/vaadin-button/src/vaadin-button.js';

class MainView extends PolymerElement {

    static get template() {
        return html`
<style include="shared-styles">
                :host {
                    display: block;
                    height: 100%;
                }
            </style>
<vaadin-vertical-layout theme="spacing" style="width: 100%; height: 100%;" id="vaadinVerticalLayout">
 <vaadin-tabs id="vaadinTabs" style="align-self: stretch;" orientation="horizontal" selected="0">
  <vaadin-tab id="homeTab" selected class="HomeView.class">
    Home 
  </vaadin-tab>
  <vaadin-tab id="createTab">
   Create Smoothie 
  </vaadin-tab>
  <vaadin-tab id="caloriesBurnoutPlanTab" style="align-self: stretch;">
    Calories Burnout Plan 
  </vaadin-tab>
  <vaadin-tab id="forumTab">
    Forum 
  </vaadin-tab>
  <div style="margin: var(--lumo-space-s); padding: var(--lumo-space-s); flex-grow: 0; align-right; justify-items: end; justify-content: end; space-between; margin-left: auto;">
   <vaadin-button id="signUpButton" style="align-self: center; flex-grow: 0; flex-shrink: 1; margin: var(--lumo-space-s);">
     Sign Up 
   </vaadin-button>
   <vaadin-button id="loginButton" style="flex-grow: 0; flex-shrink: 1;">
     Login 
   </vaadin-button>
  </div>
 </vaadin-tabs>
</vaadin-vertical-layout>
`;
    }

    static get is() {
        return 'main-view';
    }

    static get properties() {
        return {
            // Declare your properties here.
        };
    }
}

customElements.define(MainView.is, MainView);
