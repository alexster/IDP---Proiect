'use strict';
module.exports = function(app) {
  var wifiData = require('../controllers/idpController');


  // wifiData Routes
  app.route('/data')
    .get(wifiData.list_all_data)
    .post(wifiData.add_data);


  app.route('/data/:dataId')    
    .put(wifiData.update_data)
	.get(wifiData.read_data)
    .delete(wifiData.delete_data);
};