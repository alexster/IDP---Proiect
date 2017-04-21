'use strict';


var mongoose = require('mongoose'),
  wifiData = mongoose.model('WifiData');

exports.list_all_data = function(req, res) {
  wifiData.find({}, function(err, task) {
    if (err)
      res.send(err);
    res.json(task);
  });
};


exports.add_data = function(req, res) {
  var new_data = new wifiData(req.body);
  new_data.save(function(err, task) {
    if (err)
      res.send(err);
    res.json(task);
  });
};

exports.read_data = function(req, res) {
  wifiData.findById(req.params.dataId, function(err, task) {
    if (err)
      res.send(err);
    res.json(task);
  });
};


exports.update_data = function(req, res) {
  wifiData.findOneAndUpdate(req.params.dataId, req.body, {new: true}, function(err, task) {
    if (err)
      res.send(err);
    res.json(task);
  });
};


exports.delete_data = function(req, res) {


  wifiData.remove({
    _id: req.params.dataId
  }, function(err, task) {
    if (err)
      res.send(err);
    res.json({ message: 'Task successfully deleted' });
  });
};


