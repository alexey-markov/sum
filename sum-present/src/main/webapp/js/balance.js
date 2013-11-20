var Balance = function (table) {
    this.table = table;
};

Balance.request = function (self, user, url, type, data, callback) {
    $.ajax({
        url: url,
        contentType: 'application/json; charset=UTF-8',
        type: type,
        data: $.toJSON(data),
        cache: false,
        success: function (data, textStatus, jqXHR) {
            if (!!callback) callback(self, user, data);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert(errorThrown);
        }
    });
};

Balance.prototype.load = function (self, user) {
    self.category = new Category();
    self.category.load(self.category, user);

    self.currency = new Currency();
    self.currency.load(self.currency, user);

    Balance.request(this, user, 'http://localhost:8080/sum-service/rest/balance/' + user + '/total', 'POST', '{}', self.show);
};

Balance.prototype.show = function (self, user, data) {
    for (var i = 0; i < data.length; i++) {
        var basket = new Basket(self, data[i]);
        basket.open(basket, user);
    }
};
