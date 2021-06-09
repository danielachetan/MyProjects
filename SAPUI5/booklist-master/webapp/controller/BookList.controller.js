sap.ui.define([
    "sap/ui/core/mvc/Controller",
    "sap/ui/model/Filter",
    "sap/ui/model/FilterOperator"
], function (Controller, Filter, FilterOperator) {
    "use strict";
    return Controller.extend("org.ubb.books.controller.BookList", {
        onFilter : function (oEvent) {
            var aFilter = [];
            var sQuery = oEvent.getParameter("query");
            if (sQuery) {
                aFilter.push(new Filter("First name", FilterOperator.Contains, sQuery));
            }
            var oTable = this.byId("idBooksTable");
            var oBinding = oTable.getBinding("rows");
            oBinding.filter(aFilter);
        }
    });
});