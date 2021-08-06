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
<div draggable="true" vaadin-dnd-layout-item="true" style="text-align: center; width: 100%; margin: auto; background-image: url(&quot;https://cdn.pixabay.com/photo/2016/03/10/18/44/top-view-1248949_960_720.jpg&quot;); background-repeat: no-repeat; background-size: cover; -webkit-clip-path: polygon(50% 100%, 100% 80%, 100% 0, 0 0, 0 80%); height: 1000px; z-index: 3; position: relative;" id="welcomeDiv">
 <h1 style="font-family: &quot;Times New Roman&quot;, Times, serif; margin-top: 0px; font-size: 80px; padding-right: var(--lumo-space-xl); padding-left: var(--lumo-space-xl); color: white; text-shadow: 8px 8px 70px black; display: flex; justify-content: center; align-items: center; text-align: center; height: 800px;">Welcome to the Smoothie Maker's Club!</h1>
</div>
<div style="z-index = 100; width: 5px; height: 80px; background: rgb(46, 70, 57); margin: auto; z-index: inherit; position: relative; z-index: 6; margin-top: -25px;"></div>
<div style="justify-content: center; height: 1200px; background-image: url(&quot;https://cdn.pixabay.com/photo/2017/07/07/12/31/lime-2481346_1280.jpg&quot;); background-size: cover; z-index:1; margin-top: -260px; position: relative; -webkit-clip-path: polygon(0 0, 100% 0, 100% 100%, 0 80%);">
 <vaadin-vertical-layout theme="spacing" style="padding-top: 260px; width: 100%; flex-wrap: wrap; color: rgb(46, 70, 57); text-shadow: 8px 8px 70px black;">
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
<div style="height: 1000px; background-image: url(&quot;https://cdn.pixabay.com/photo/2015/09/06/01/00/surfing-926822_1280.jpg&quot;); background-repeat: no-repeat; background-size: cover; margin-top: -300px; position: relative; z-index = -1; image-rendering: auto; width: 100%; background-position: center; text-shadow: 8px 8px 70px black;">
 <h1 style="font-weight: 900; text-align: center; width: 100%; font-size: 50px; padding-top: 300px; position: relative; z-index: 5; color: #213041;">Calculate A Workout Plan.</h1>
 <h3 style="margin-top: 50px; font-weight: 300; text-align: center; align-self: center; position: relative; z-index: 5; color: #213041;">Get your AUTO-GENERATED calorie workout plan after creating your smoothie!</h3>
</div>
<div id="panel2" style="height: 1000px; background-image: url(&quot;https://images.unsplash.com/photo-1461354464878-ad92f492a5a0?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&amp;ixlib=rb-1.2.1&amp;auto=format&amp;fit=crop&amp;w=1650&amp;q=80&quot;); background-size: cover; background-position: center; -webkit-clip-path: polygon(0 15%, 100% 0, 100% 100%, 0 100%); margin-top: -200px; position: relative; z-index: 10;">
 <h2 style="font-weight: 900; text-align: center; width: 100%; font-size: 50px; position: relative; z-index: 5; padding-top: 300px; text-shadow: 10px 10px 100px black; color: #e1e1e1;">Get Together and Start Engaging </h2>
 <h3 style="margin-top: 50px; font-weight: 300; text-align: center; align-self: center; position: relative; z-index: 5; color: white;">Share your smoothies with others!</h3>
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
