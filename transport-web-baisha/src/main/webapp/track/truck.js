
function ApplicationModel(stompClient, L) {
  var self = this;

  self.username = ko.observable();
  self.driverMontior = ko.observable(new DriverMonitorModel());
  self.notifications = ko.observableArray();
  self.leaf = L;

  self.connect = function() {
    stompClient.connect('', '', function(frame) {
      console.log('Connected XXXX' + frame);
      self.username(frame.headers['user-name']);
      
      stompClient.subscribe("/topic/all_signal", function(message) {
   	   console.log('msg from /topic/all_signal: ' + message);
              self.driverMontior().processAllSignal(JSON.parse(message.body));
          });
      
    }, function(error) {
      console.log("STOMP protocol error " + error);
    });
  }

  self.pushNotification = function(text) {
    self.notifications.push({notification: text});
    if (self.notifications().length > 5) {
      self.notifications.shift();
    }
  }

  self.logout = function() {
    stompClient.disconnect();
    window.location.href = "../logout.html";
  }
}

function DriverMonitorModel() {
  var self = this;
  
  self.rows = ko.observableArray();

  var rowLookup = {};
  
  self.loadAllSignalEvents = function(positions) {
	  for ( var i = 0; i < positions.length; i++) {
    	
    	self.loadAllSignalEvent(positions[i]);
    }
  };

  self.loadAllSignalEvent = function (position) {
	var row = new TrackRow(position);
	 self.rows.push(row);
	 rowLookup[row.routeId] = row;	  
  };	  
	  
 self.processAllSignal = function(driverEvent) {
		 	if (rowLookup.hasOwnProperty(driverEvent.routeId)) {
		 		rowLookup[driverEvent.routeId].updateEvent(driverEvent);		 		
		    } else {
		    	self.loadAllSignalEvent(driverEvent);
		    }
		  }; 
}//end DriverMonitorModel;


function TrackRow(data) {
  var self = this;

  self.routeId = data.routeId;
  self.destPlace = data.destPlace;  
  self.carId = data.carId;
  self.carLicense = data.carLicense;
  self.driverId = data.driverId;
  self.driverName = data.driverName;
    
  self.timestamp = ko.observable(data.timestamp);
  self.longitude = ko.observable(data.longitude);
  self.latitude = ko.observable(data.latitude);
  self.speed = ko.observable(data.speed);
  self.events = ko.observable(data.events);

  self.rowClass=ko.observable("");
  
  self.updateEvent = function(routeTrack) {
	  	
	    self.timestamp(routeTrack.timestamp);
	    self.longitude(routeTrack.longitude);
	    self.latitude(routeTrack.latitude);
	    self.speed(routeTrack.speed);
	    self.events(routeTrack.events);
  };  
}; //end TrackRow()




