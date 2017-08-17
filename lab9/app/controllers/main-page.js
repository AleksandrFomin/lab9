import Ember from 'ember';

export default Ember.Controller.extend({
  init:function(){
    this._super();
  },
  points:[],
  usl:true,
  xVal: 1,
  yVal: '',
  rVal: 2,
  img:'',
  isValid : Ember.computed.match('yVal',/^[-+]?[0-9]+([\.,]?[0-9]+)?$/),
  isDisabled: Ember.computed.not('isValid'),
  requestURL: 'http://localhost:8080/lab9backend_war_exploded',
  ajax: Ember.inject.service(),

  onload:function() {
    Ember.$.ajax({
      type: "GET",
      url: "http://localhost:8080/lab8Maven_war_exploded/rest/point/img",
      dataType: "json",
      crossDomain: true,
      success: function (data) {
        this.set("img",data.responseText)
        //this.set("img",JSON.parse(data));
      }.bind(this),
      error:function(data){
        this.set("img",data.responseText);
      }.bind(this)
    });
    Ember.$.ajax({
      type: "GET",
      url: "http://localhost:8080/lab8Maven_war_exploded/rest/point/getPoints",
      dataType: "json",
      crossDomain: true,
      success: function (data) {
        this.set("points",data);
      }.bind(this),
      error:function(data){
        this.set("points",data);
      }.bind(this)
    });
  }.on('init'),

  actions: {
    setX: function (value) {
      this.set("xVal", value);
      //Ember.$('.initbtn').trigger('click');
    },
    setR: function (value) {
      this.set("rVal", value)
    },
    setY: function (value) {
      this.set("yVal", value)
    },

    clearButton(){
      Ember.$.ajax({
        type: "GET",
        url: "http://localhost:8080/lab8Maven_war_exploded/rest/point/reset",
        dataType: "json",
        crossDomain: true,
        success: function () {
          //alert(data);
          this.onload();
        }.bind(this)
      });
    }
  }
});
