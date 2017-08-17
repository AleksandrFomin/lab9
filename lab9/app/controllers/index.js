import Ember from 'ember';

export default Ember.Controller.extend({
  login:'',
  password:'',
  repeated:'',
  notValidLogin:Ember.computed.empty('login'),
  notValidPassword:Ember.computed.empty('password'),
  isValidRepeated:Ember.computed.equal('repeated','password'),
  notValidRepeated:Ember.computed.not('isValidRepeated'),
  disableLogin:Ember.computed.or('notValidLogin','notValidPassword'),
  disableRegister:Ember.computed.or('disableLogin','isValidRepeated'),

  actions:{
    btnRegister(login,password){
      Ember.$.ajax({
        type: "GET",
        url: "http://localhost:8080/lab8Maven_war_exploded/rest/point/addUser/" + login + "/" + password,
        dataType: "json",
        crossDomain: true,
        success: function (data) {
          if(data)
            alert("registered");
          else
            alert("login already exists");
        }
      });
    },

    btnLogin(login,password){
      Ember.$.ajax({
        type: "GET",
        url: "http://localhost:8080/lab8Maven_war_exploded/rest/point/checkUser/" + login + "/" + password,
        dataType: "json",
        crossDomain: true,
        success: function (data) {
          if(data){
            this.transitionToRoute('main-page');
          }
          else
            alert("no");
        }.bind(this)
      });
    }

  }
});
