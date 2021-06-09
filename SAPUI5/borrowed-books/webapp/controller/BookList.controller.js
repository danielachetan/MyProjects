sap.ui.define([
    "sap/ui/core/mvc/Controller",
    "sap/ui/model/Filter",
    "sap/ui/model/Sorter",
    "sap/ui/model/FilterOperator"
], function (Controller, Filter, FilterOperator) {
    "use strict";
    return Controller.extend("org.ubb.books.controller.BookList", {
        onFirstNameFilter : function () {
            var aFilter = [];
            //var sQuery = oEvent.getParameter("query");
            //if (sQuery) {
                //aFilter.push(new Filter("First name", FilterOperator.Contains, sQuery));
            //}
            var oTable = this.byId("idBooksTable");
            var searchValue = this.getView().byId("firstNameSearchField").getValue();
            aFilter.push(new Filter("FirstName", FilterOperator.Contains, searchValue));
            //aFilter.push(new Filter("LastName", FilterOperator.Contains, searchValue));
            /**
           var filter = new Filter({
               filters: [
                   new Filter({
                       path: 'FirstName',
                       operator: FilterOperator.Contains,
                       value1: searchValue
                   }),
                   new Filter({
                       path: 'LastName',
                       operator: FilterOperator.Contains,
                       value1: searchValue
                   })
               ]
           });
             **/

            /**
            var filter = [
                new Filter("FirstName", FilterOperator.Contains, searchValue),
                new Filter("LastName", FilterOperator.Contains, searchValue),
                new Filter("Title", FilterOperator.Contains, searchValue),
                new Filter("Author", FilterOperator.Contains, searchValue)
            ];
             **/

            var oBinding = oTable.getBinding("items");
            oBinding.filter(aFilter);
        },

        onLastNameFilter: function() {
            var aFilter = [];
            var oTable = this.byId("idBooksTable");
            var searchValue = this.getView().byId("lastNameSearchField").getValue();
            aFilter.push(new Filter("LastName", FilterOperator.Contains, searchValue));
            var oBinding = oTable.getBinding("items");
            oBinding.filter(aFilter);
        },

        onTitleFilter: function() {
            var aFilter = [];
            var oTable = this.byId("idBooksTable");
            var searchValue = this.getView().byId("titleSearchField").getValue();
            aFilter.push(new Filter("Title", FilterOperator.Contains, searchValue));
            var oBinding = oTable.getBinding("items");
            oBinding.filter(aFilter);
        },

        onAuthorFilter: function() {
            var aFilter = [];
            var oTable = this.byId("idBooksTable");
            var searchValue = this.getView().byId("authorSearchField").getValue();
            aFilter.push(new Filter("Author", FilterOperator.Contains, searchValue));
            var oBinding = oTable.getBinding("items");
            oBinding.filter(aFilter);
        }

        /**
        onSort: function() {
            var oTable = this.byId("idBooksTable");
            var oSorter = new Sorter({
                path: "FirstName",
                descending: true,
                group: true
            });
            oTable.getBinding("items").sort([oSorter]);
        }
         **/
    });
});