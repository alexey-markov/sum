var Category = function () {
    this.data = new Array();
};

Category.prototype.load = function (self) {
    Balance.request(this, 'http://localhost:8080/sum-service/rest/balance/graph', 'POST', '{}', function(self, data) {
        for (var i = 0; i < data.length; i++) {
            var category = data[i];
            self.data[category['name']] = category;
        }
    });
};

Category.prototype.names = function (self) {
    var names = new Array();
    for (var name in self.data) {
            names.push(name);
    }
    return names;
};

Category.prototype.entry = function (self, name) {
    return self.data[name];
};