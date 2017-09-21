MyWebsocket = function(wsurl,handleMassage) {
	this.isConnectWsSuccess = false;
	this.connect_handleMassage = handleMassage;
}

MyWebsocket.prototype = {
		connect:function(){
			var me = this;
			this._closeSocket();
			var host;
			if (window.location.protocol == 'http:') {
					host = 'ws://' + window.location.host + '/568data/forwardWebSocket?from='+accountid + "_" +new Date().getTime();
				} else {
					host = 'wss://' + window.location.host + '/568data/forwardWebSocket?from='+accountid + "_" +new Date().getTime();
				}
				if ('WebSocket' in window) {
					this.ws = new WebSocket(host);
				} else if ('MozWebSocket' in window) {
					this.ws = new MozWebSocket(host);
				} else {
					return;
				}
				
				this.ws.onopen = function() {
					console.log("websocket连接成功!");
					me.isConnectWsSuccess = true;
				};

				this.ws.onclose = function() {
					me.isConnectWsSuccess = false;
				};
				
				this.ws.onmessage = function(message) {
					console.log(message);
					me.connect_handleMassage(message);
					//收到消息后做出处理的方法
					//handleMsg(JSON.parse(message.data));
					//alert(JSON.parse(message.data).name + " 在 " +  JSON.parse(message.data).address+ " 等待上车");
				};
			},
		sendMassage : function(massage){
			this.ws.send(JSON.stringify(massage).replace(/\\n/g,"").replace(/\\t/g,""));
		},
		_closeSocket: function () {
	        if (this.ws) { try {
	            this.ws.close();
	        } catch (e) {} }
	        this.ws = null;
	    }
}

