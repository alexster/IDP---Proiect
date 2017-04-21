'use strict';
var mongoose = require('mongoose');
var Schema = mongoose.Schema;


var WifiDataSchema = new Schema({
  ssid: {
    type: String,
    Required: 'ssid'
  },
  bssid: {
    type: String,
    Required: 'bssid'
  },
  freq: {
    type: String,
    Required: 'freq'
  },
  level: {
    type: String,
    Required: 'level'
  },
  time: {
    type: String,
	Required: 'date'
  }
  
});

module.exports = mongoose.model('WifiData', WifiDataSchema);