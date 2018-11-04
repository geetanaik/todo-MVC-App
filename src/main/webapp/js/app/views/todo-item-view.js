define([
  'marionette',
  'hbs!../../../templates/todo-item'
], function(Marionette, TodoItemTemplate) {
  'use strict';

  return Marionette.ItemView.extend({
    tagName: 'li',
    template: TodoItemTemplate,
    className: function() {
      return this.model.get('completed') ? 'completed' : 'active';
    },
    ui: {
      edit: '.edit',
      destroy: '.destroy',
      label: 'label',
      toggle: '.toggle'
    },
    events: {
      'dblclick @ui.label': 'onEditClick',
      'keydown @ui.edit': 'onEditKeypress',
      'focusout @ui.edit': 'onEditFocusout',
      'click @ui.toggle': 'toggle',
      'click @ui.destroy' : 'onDestroy'   // gd
    },
    modelEvents: {
      change: 'render'
    },
    toggle: function() {
      this.model.toggle().save();
    },
    onEditClick: function() {
      this.$el.addClass('editing');
      this.ui.edit.focus();
      this.ui.edit.val(this.ui.edit.val());
    },
    onEditFocusout: function() {
      var todoText = this.ui.edit.val().trim();

      if (todoText) {
        this.model.set('description', todoText).save();
        this.$el.removeClass('editing');
      } else {
        this.destroy();
      }
    },
    
    onDestroy : function() { //gd
    //	this.destroy();      /
    	this.model.destroy();     //model object destroy and refresh
    },
    
    onEditKeypress: function(e) {
      var ENTER_KEY = 13;
      var ESC_KEY = 27;

      if (e.which === ENTER_KEY) {
        this.onEditFocusout();
        return;
      }

      if (e.which === ESC_KEY) {
        this.ui.edit.val(this.model.get('description'));
        this.$el.removeClass('editing');
      }
    }
  });
});
