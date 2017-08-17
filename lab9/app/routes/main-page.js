import Ember from 'ember';

export default Ember.Route.extend({

  actions:{
    submitButton(x,y,r){
      Ember.$.ajax({
        type: "GET",
        url: "http://localhost:8080/lab8Maven_war_exploded/rest/point/add/" + x + "/" + y + "/" + r,
        dataType: "json",
        crossDomain: true,
        success: function () {
          //this.controllerFor('main-page').set("points",data);
          this.controllerFor('main-page').onload();
        }.bind(this)
      });
    },

    imgClick(){
      var r=this.controllerFor('main-page').get("rVal");
      var x=Number(((event.offsetX-190)*r/136).toFixed(2));
      var y=Number(((190-event.offsetY)*r/136).toFixed(2));
      this.send("submitButton",x,y,r)
    },

    exitButton(){
      this.transitionTo('index');
    }
  }
});
