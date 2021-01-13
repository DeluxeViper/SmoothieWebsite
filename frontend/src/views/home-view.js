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
<div draggable="true" vaadin-dnd-layout-item="true" style="text-align: center; width: 100%; margin: auto; background-image: url(&quot;https://cdn.pixabay.com/photo/2016/03/10/18/44/top-view-1248949_960_720.jpg&quot;); background-repeat: no-repeat; background-size: cover; -webkit-clip-path: polygon(50% 100%, 100% 80%, 100% 0, 0 0, 0 80%); height: 600px; z-index: 3; position: relative;">
 <h1 style="font-family: &quot;Times New Roman&quot;, Times, serif; margin-top: 0px; font-size: 60px; padding-right: var(--lumo-space-xl); padding-left: var(--lumo-space-xl); color: white; text-shadow: 8px 8px 70px black; display: flex; justify-content: center; align-items: center; text-align: center; height: 600px;">Welcome to Smoothie Makers Club!</h1>
</div>
<div style="z-index = 100; width: 5px; height: 80px; background: rgb(46, 70, 57); margin: auto; z-index: inherit; position: relative; z-index: 6; margin-top: -25px;"></div>
<div style="justify-content: center; height: 1100px; background-image: url(&quot;https://cdn.pixabay.com/photo/2017/07/07/12/31/lime-2481346_1280.jpg&quot;); background-size: cover; z-index:1; margin-top: -240px; position: relative; -webkit-clip-path: polygon(0 0, 100% 0, 100% 100%, 0 80%);">
 <vaadin-vertical-layout theme="spacing" style="padding-top: 100px; width: 100%; flex-wrap: wrap; color: rgb(46, 70, 57); text-shadow: 8px 8px 70px black;">
  <h2 style="font-weight: 900; text-align: center; width: 100%; margin: auto; font-size: 50px; padding-top: var(--lumo-space-xl); margin-top: 150px;">Create Your Own Masterpiece.</h2>
  <h3 style="margin-top: 50px; font-weight: 300; text-align: center; align-self: center;">Make your own custom Smoothies or Shakes and let us provide your tailored Nutritional &amp; Workout breakdown. For Free.</h3>
  <div style="margin-top: 30px; width: 100%; flex-grow: 0; display: flex; justify-content: center; align-self: stretch; padding: var(--lumo-space-xs); flex-wrap: wrap;">
   <vaadin-button id="createSmoothieButton" style="margin: var(--lumo-space-xs); width: 300px; height: 50px; background: rgb(106, 194, 147);" theme="primary">
    <iron-icon icon="lumo:arrow-right" slot="suffix"></iron-icon>CREATE SMOOTHIE 
   </vaadin-button>
   <vaadin-button theme="primary" id="loginHomeButton" style="margin: var(--lumo-space-xs); width: 300px; height: 50px;">
     LOGIN 
   </vaadin-button>
  </div>
 </vaadin-vertical-layout>
</div>
<div style="height: 800px; background-image: url(&quot;https://cdn.pixabay.com/photo/2015/09/06/01/00/surfing-926822_1280.jpg&quot;); background-repeat: no-repeat; background-size: cover; margin-top: -300px; position: relative; z-index = -1; image-rendering: auto; width: 100%; background-position: center; text-shadow: 8px 8px 70px black;">
 <h1 style="font-weight: 900; text-align: center; width: 100%; font-size: 50px; padding-top: var(--lumo-space-xl); position: relative; z-index: 5;">Calculate A Workout Plan.</h1>
 <h3 style="margin-top: 50px; font-weight: 300; text-align: center; align-self: center; position: relative; z-index: 5;">Get your AUTO-GENERATED calorie workout plan after creating your smoothie!</h3>
 <h2 style="font-weight: 900; text-align: center; width: 100%; font-size: 50px; position: relative; z-index: 5; margin-top: 200px;">Get Together and Start Engaging </h2>
</div>
<div id="panel2" style="height: 600px; background-image: url(&quot;https://cdn.pixabay.com/photo/2018/08/21/23/29/fog-3622519_1280.jpg&quot;); background-size: cover; background-position: center; -webkit-clip-path: polygon(0 30%, 100% 0, 100% 100%, 0 100%); margin-top: -210px; position: relative; z-index: 10;"></div>
<div style="height: 500px; margin: var(--lumo-space-xl);">
 <h2 style="font-weight: 900; text-align: center; margin: auto;">About Us</h2>
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
