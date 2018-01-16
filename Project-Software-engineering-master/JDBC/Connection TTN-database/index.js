var ttn = require("ttn")
var mysql = require('mysql');
var con = mysql.createConnection({
  host: "sql11.freemysqlhosting.net",
  user: "sql11215263",
  password: "jhqA3uSrw9",
  database: "sql11215263"
});

var appID = "projectdoner_test"
var accessKey = "ttn-account-v2.oc_8swh3ZxB12fKg-6rPClHdfm-d15TE7_MM-Pqj4MY"

function sleep() {
  var start = new Date().getTime();
  var f = 1;
  while(f) {
	  
    if ((new Date().getTime() - start) > 900000){
		console.log("hoi ")
      f=0;
    }
  }
}

con.connect(function(err){
	
	 if (err) throw err;
  console.log("Connected!");
ttn.data(appID, accessKey)

  .then(function (client) {
    client.on("uplink", function (devID, payload) {
		sleep();
      console.log("Received uplink from ", devID)
      var temp = payload["payload_fields"]["temperature"]
	  var lig = payload["payload_fields"]["light"]
	  var pres = payload["payload_fields"]["pressure"]
	  var humid = payload["payload_fields"]["humidity"]

	  var sql = "INSERT INTO measurements (temperature, light, pressure, humidity) VALUES (" + temp +"," + lig+ "," + pres +"," + humid +")";
	  con.query(sql, function (err, result) {
      if (err) throw err;
      console.log("1 record inserted");
	  
	  
  });
	  
    })
  })
  .catch(function (error) {
    console.error("Error", error)
    process.exit(1)
  })
});
