var Basket = function (balance, data) {
    this.balance = balance;
    this.data = data;
};

Basket.prototype.open = function (self) {
    var parent = self.balance.table;
    var signal = self.data.name;
    parent.find('tr#basket_title').append('<th colspan="2">' + signal + '</th>').children('th').editable(function (value, settings) {
        self.data.name = value;
        Balance.request(self, 'http://localhost:8080/sum-service/rest/balance/basket', 'POST', self.data);
        return value;
    }, {
        width: "100%"
    });
    var place = parent.find('tr#basket_table');
    place.append('<td><input id="insert_' + signal + '" type="image" src="images/add.png"/>'
        + '<input id="remove_' + signal + '" type="image" src="images/remove.png"/></td>');
    $('tr#basket_table input#insert_' + signal).on('click', function () {
        Basket.prototype.insert(self);
    });
    $('tr#basket_table input#remove_' + signal).attr('disabled', 'disabled').css('opacity', 0.5).on('click', function () {
        Basket.prototype.remove(self);
    });

    place.append('<td><table id="basket_' + signal + '"><thead><tr><th></th><th></th><th></th><th></th><th></th></tr></thead>'
        + '<tbody><tr><td></td><td></td><td></td><td></td><td></td></tr></tbody></table></td>');

    /* Init DataTables */
    self.table = $('table#basket_' + signal).dataTable({
        aaData: [],
        aoColumns: [
            {sTitle: 'date', mData: 'event', sClass: 'date', mRender: function (data, type, row) {
                return (!!data) ? $.datepicker.formatDate('yy-mm-dd', new Date(data.date)) : '';
            }},
            {sTitle: 'category', mData: 'event', sClass: 'category', mRender: function (data, type, row) {
                return (!!data) ? data.category.name : '';
            }},
            {sTitle: 'money', mData: 'money', sClass: 'money', mRender: function (data, type, row) {
                return (!!data) ? data.value : '';
            }},
            {sTitle: 'currency', mData: 'money', sClass: 'currency', mRender: function (data, type, row) {
                return (!!data) ? data.currency.name : '';
            }},
            {sTitle: 'comment', mData: 'event', sClass: 'comment', mRender: function (data, type, row) {
                return (!!data) ? data.comment : '';
            }}
        ]
    });
    Basket.prototype.load(self);
};

Basket.prototype.load = function (self) {
    var data = {
        from: 0,
        till: new Date().getTime() / (24 * 60 * 60 * 1000) + 1,
        basket: self.data
    };
    Balance.request(self, 'http://localhost:8080/sum-service/rest/balance/list', 'POST', data, self.show);
};

Basket.prototype.show = function (self, data) {
    var oSettings = self.table.fnSettings();

    self.table.fnClearTable(this);

    for (var i = 0; i < data.length; i++) {
        self.table.oApi._fnAddData(oSettings, data[i]);
    }

    oSettings.aiDisplay = oSettings.aiDisplayMaster.slice();
    self.table.fnDraw();

    var signal = self.data.name;
    $('table#basket_' + signal + ' tr').on('click', function () {
        Basket.prototype.select(self, this);
    });

    Basket.prototype.editable(self, 'date', {type: 'datepicker', datepicker: {firstDay: 1, dateFormat: 'yy-mm-dd', changeMonth: false, changeYear: true}}, function (prev, next) {
        prev.date = $.datepicker.parseDate('yy-mm-dd', next).getTime();
    });
    var category = self.balance.category;
    var catnames = category.names(category);
    Basket.prototype.editable(self, 'category', {type: 'select', data: catnames}, function (prev, next) {
        prev.category = category.entry(category, catnames[next]);
    });
    Basket.prototype.editable(self, 'money', {type: 'text'}, function (prev, next) {
        prev.value = parseInt(next.replace(/[^0-9]/g, ''));
    });
    var currency = self.balance.currency;
    var curnames = currency.names(currency);
    Basket.prototype.editable(self, 'currency', {type: 'select', data: curnames}, function (prev, next) {
        prev.currency = currency.entry(currency, curnames[next]);
    });
    Basket.prototype.editable(self, 'comment', {type: 'text'}, function (prev, next) {
        prev.comment = next;
    });
};

Basket.prototype.editable = function (self, column, options, update) {
    self.table.$('td.' + column).editable(function (value, settings) {
        var data = self.table.fnGetData(this);
        update(data, value);
        var aPos = self.table.fnGetPosition(this);
        self.table.fnUpdate(data, aPos[0], aPos[1]);
        var deal = self.table.fnGetData(aPos[0]);
        Balance.request(self, 'http://localhost:8080/sum-service/rest/balance/deal', 'POST', deal);
    }, $.extend(options, {
        event: "dblclick",
        width: "100%"
    }));
};

Basket.prototype.select = function (self, row) {
    var signal = self.data.name;
    $(row).toggleClass('row_selected');
    if (Basket.prototype.selected(self).length > 0) {
        $('tr#basket_table input#remove_' + signal).removeAttr('disabled', '').css('opacity', 1.0);
    } else {
        $('tr#basket_table input#remove_' + signal).attr('disabled', 'disabled').css('opacity', 0.5);
    }
}

Basket.prototype.selected = function (self) {
    var aReturn = new Array();
    var aTrs = self.table.fnGetNodes();

    for (var i = 0; i < aTrs.length; i++) {
        if ($(aTrs[i]).hasClass('row_selected')) {
            aReturn.push(aTrs[i]);
        }
    }
    return aReturn;
}

Basket.prototype.insert = function (self) {
    var category = self.balance.category;
    var catnames = category.names(category);
    var currency = self.balance.currency;
    var curnames = currency.names(currency);
    var deal = {
        event: {date: Date.now(), category: category.entry(category, catnames[0]), comment: ''},
        basket: self.data,
        money: {value: 0, currency: currency.entry(currency, curnames[0])}
    };
    deal = Balance.request(self, 'http://localhost:8080/sum-service/rest/balance/deal', 'POST', deal, self.load);
}

Basket.prototype.remove = function (self) {
    var select = Basket.prototype.selected(self);
    for (var i = 0; i < select.length; i++) {
        console.log(select[i]);
    }
}
