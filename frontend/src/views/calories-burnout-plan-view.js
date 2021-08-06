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
<vaadin-vertical-layout theme="spacing" style="width: 100%; background-image: url(&quot;https://images.unsplash.com/photo-1584735935682-2f2b69dff9d2?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&amp;ixlib=rb-1.2.1&amp;auto=format&amp;fit=crop&amp;w=1651&amp;q=80&quot;); background-repeat: no-repeat; background-size: cover; height: 100%;">
 <div style="width: 100%; align-self: center; position: absolute; top: 50%; transform: translateY(-70%);">
  <h2 style="width: 100%; flex-grow: 0; align-self: center; text-align: center; justify-content: center; align-items: center; color: white; font-size: 100px;">Calories Burnout Plan</h2>
  <h1 style="width: 100%; text-align: center; height: 100%; align-self: center; font-weight: 1000; color: white; font-size: 50px;">Coming Soon</h1>
 </div>
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
