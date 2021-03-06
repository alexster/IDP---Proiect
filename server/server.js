var express = require('express'),
  app = express(),
  port = process.env.PORT || 80,
  mongoose = require('mongoose'),
  Data = require('./api/models/idpModel'),
  bodyParser = require('body-parser');

mongoose.Promise = global.Promise;
mongoose.connect('mongodb://localhost/wifidb');


app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());


var routes = require('./api/routes/idpRoutes');
routes(app);

app.use(function(req, res) {
  res.status(404).send({url: req.originalUrl + ' not found'})
});
  
app.listen(port);

console.log('RESTful API server started on: ' + port);
