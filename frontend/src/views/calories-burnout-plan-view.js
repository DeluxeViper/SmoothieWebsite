import {html, PolymerElement} from '@polymer/polymer/polymer-element.js';
import '@vaadin/vaadin-ordered-layout/src/vaadin-vertical-layout.js';

class CaloriesBurnoutPlanView extends PolymerElement {

    static get template() {
        return html`
<style include="shared-styles">
                :host {
                    display: block;
                    height: 100%;
                }
            </style>
<vaadin-vertical-layout theme="spacing" style="width: 100%; height: 100%;">
 <h2>Calories Burnout Plan View</h2>
</vaadin-vertical-layout>
`;
    }

    static get is() {
        return 'calories-burnout-plan-view';
    }

    static get properties() {
        return {
            // Declare your properties here.
        };
    }
}

customElements.define(CaloriesBurnoutPlanView.is, CaloriesBurnoutPlanView);
