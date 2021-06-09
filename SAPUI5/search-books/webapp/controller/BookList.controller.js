sap.ui.define([
    "sap/ui/core/mvc/Controller",
    "sap/ui/model/Filter",
    "sap/ui/model/FilterOperator"
], function (Controller, Filter, FilterOperator) {
    "use strict";
    return Controller.extend("org.ubb.books.controller.BookList", {
        onFilter: function () {
            //var aFilter = [];
            //var sQuery = oEvent.getParameter("query");
            //if (sQuery) {
            //aFilter.push(new Filter("First name", FilterOperator.Contains, sQuery));
            //}
            var oTable = this.byId("idBooksTable");
            var isbn = this.getView().byId("isbn").getValue();
            var title = this.getView().byId("title").getValue();
            var author = this.getView().byId("author").getValue();
            var publishingDate = this.getView().byId("publishingDate").getValue();
            var language = this.getView().byId("language").getValue();
            //aFilter.push(new Filter("FirstName", FilterOperator.Contains, searchValue));
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


            var filter = [
                new Filter("Isbn", FilterOperator.EQ, isbn),
                new Filter("Title", FilterOperator.Contains, title),
                new Filter("Author", FilterOperator.Contains, author),
                new Filter("PublishingDate", FilterOperator.EQ, publishingDate),
                new Filter("Language", FilterOperator.EQ, language)
            ];


            var oBinding = oTable.getBinding("items");
            oBinding.filter([new Filter([filter], true)]);
        }
    });
});