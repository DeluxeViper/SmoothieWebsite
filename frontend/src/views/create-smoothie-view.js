import {html, PolymerElement} from '@polymer/polymer/polymer-element.js';
import '@vaadin/vaadin-ordered-layout/src/vaadin-vertical-layout.js';

class CreateSmoothieView extends PolymerElement {

    static get template() {
        return html`
<style include="shared-styles">
                :host {
                    display: block;
                    height: 100%;
                }
            </style>
<vaadin-vertical-layout theme="spacing" style="width: 100%; height: 100%;">
 <h1 style="margin: var(--lumo-space-l); margin-top: var(--lumo-space-xl); margin-bottom: var(--lumo-space-s);">Craft Your Smoothie</h1>
 <h2 style="margin: var(--lumo-space-l);">Add Your Ingredients</h2>
</vaadin-vertical-layout>
`;
    }

    static get is() {
        return 'create-smoothie-view';
    }

    static get properties() {
        return {
            // Declare your properties here.
        };
    }
}

customElements.define(CreateSmoothieView.is, CreateSmoothieView);
