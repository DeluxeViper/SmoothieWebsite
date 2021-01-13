import {html, PolymerElement} from '@polymer/polymer/polymer-element.js';
import '@vaadin/vaadin-ordered-layout/src/vaadin-vertical-layout.js';
import '@vaadin/vaadin-button/src/vaadin-button.js';
import '@polymer/iron-icon/iron-icon.js';

class HomeView extends PolymerElement {

    static get template() {
        return html`
<style include="shared-styles">
                :host {
                    display: block;
                    height: 100%;
                }
            </style>
<vaadin-vertical-layout theme="spacing" style="width: 100%; height: 600px; align-items: center; justify-content: center;">
 <div style="justify-content: center; align-self:center; height: 600px;">
  <h2 style="margin: var(--lumo-space-m);">Create Your Own Masterpiece.</h2>
  <h3 style="margin: var(--lumo-space-m);">Make your own custom Smoothies or Shakes and let us provide your tailored Nutritional &amp; Workout breakdown. For Free.</h3>
  <div style="align-self: center; justify-content: space-between; margin: var(--lumo-space-xl);">
   <vaadin-button id="createSmoothieButton" style="margin: var(--lumo-space-xl);">
    <iron-icon icon="lumo:arrow-right" slot="suffix"></iron-icon>CREATE SMOOTHIE 
   </vaadin-button>
   <vaadin-button theme="primary" id="loginHomeButton" style="margin: var(--lumo-space-s); margin-left: var(--lumo-space-xl); margin-right: var(--lumo-space-xl);">
     LOGIN 
   </vaadin-button>
  </div>
 </div>
</vaadin-vertical-layout>
<div style="height: 500px; flex-grow: 1; flex-shrink: 0;">
  Div2 
</div>
<div id="panel2" style="height: 600px;">
  Wassah 
</div>
<div style="height: 500px; flex-grow: 1; flex-shrink: 0;">
  Div2 
</div>
`;
    }

    static get is() {
        return 'home-view';
    }

    static get properties() {
        return {
            // Declare your properties here.
        };
    }
}

customElements.define(HomeView.is, HomeView);
