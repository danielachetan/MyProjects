sap.ui.define([
   "sap/ui/core/mvc/Controller",
    "sap/ui/core/Fragment",
    "sap/m/MessageToast"
], function (Controller, MessageToast) {
   "use strict";
   return Controller.extend("org.ubb.books.controller.App", {
      onOpenDialog : function () {
         var oView = this.getView();
         if (!this.pDialog) {
            this.pDialog = Fragment.load({
               id: oView.getId(),
               name: "org.ubb.books.view.AddBookDialog",
               controller: this
            }).then(function(oDialog) {
               oView.addDependent(oDialog);
               return oDialog;
            });
         }
         this.pDialog.then(function(oDialog) {
            oDialog.open();
         });
      },

      onCloseDialog : function () {
         this.byId("addBookDialog").close();
      },

      onSaveBook : function () {
         var oEntry = {};
         oEntry.Isbn = this.getView().byId("isbn").getValue();
         oEntry.Title = this.getView().byId("title").getValue();
         oEntry.Author = this.getView().byId("author").getValue();
         oEntry.PublishingDate = new Date(this.getView().byId("publishingDate").getValue());
         oEntry.Language = this.getView().byId("language").getValue();
         oEntry.AvailableBooks = parseInt(this.getView().byId("availableBooks").getValue());

         var oView = this.getView();
         var oModel = new sap.ui.model.odata.ODataModel("sap/opu/odata/SAP/Z801_LIBRARY_DACH_SRV");
         oModel.create("/Books", oEntry, {
            method: "POST",
            success: function(){
               //MessageToast.show("Book has been added");
            },
            error: function() {
               //MessageToast.show("Ceva o crapat");
            }
         });

      },

      onUpdateBook : function () {
         var oEntry = {};
         oEntry.Isbn = this.getView().byId("isbn").getValue();
         oEntry.Title = this.getView().byId("title").getValue();
         oEntry.Author = this.getView().byId("author").getValue();
         oEntry.PublishingDate = new Date(this.getView().byId("publishingDate").getValue());
         oEntry.Language = this.getView().byId("language").getValue();
         oEntry.AvailableBooks = parseInt(this.getView().byId("availableBooks").getValue());
         var oModel = new sap.ui.model.odata.ODataModel("sap/opu/odata/SAP/Z801_LIBRARY_DACH_SRV");
         oModel.update("/Books('" + oEntry.Isbn + "')", oEntry, {
            method: "PUT",
            success: function(){
               //MessageToast.show("Book has been added");
            },
            error: function() {
               //MessageToast.show("Ceva o crapat");
            }
         });

      },

      onDeleteBook : function () {
         var isbn = this.getView().byId("isbn").getValue();
         var oModel = new sap.ui.model.odata.ODataModel("sap/opu/odata/SAP/Z801_LIBRARY_DACH_SRV");
         oModel.remove("/Books('" + isbn + "')", {
            method: "DELETE",
            success: function(){
               //MessageToast.show("Book has been added");
            },
            error: function() {
               //MessageToast.show("Ceva o crapat");
            }
         });
      }
   });
});