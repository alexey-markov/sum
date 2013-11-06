var Currency = function () {
    this.data = new Array();
};

Currency.prototype.load = function (self) {
    Balance.request(this, 'http://localhost:8080/sum-service/rest/balance/money', 'POST', '{}', function (self, data) {
        for (var i = 0; i < data.length; i++) {
            var currency = data[i];
            self.data[currency['name']] = currency;
        }
    });
};

Currency.prototype.names = function (self) {
    var names = new Array();
    for (var name in self.data) {
        names.push(name);
    }
    return names;
};

Currency.prototype.entry = function (self, name) {
    return self.data[name];
};
