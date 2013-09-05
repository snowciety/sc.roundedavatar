// This is a test harness for your module
// You should do something interesting in this harness 
// to test out the module and to provide instructions 
// to users on how to use it by example.


// open a single window
var win = Ti.UI.createWindow({
	backgroundColor:'white'
});

var avatar = Ti.UI.createImageView();
var ra = require('sc.roundedavatar');

win.add(avatar);

//Adding a 200x200 avatar and resizing it to 64x64
avatar.image = ra.getRoundedAvatar("http://graph.facebook.com/zuck/picture?width=200&height=200", 64);

win.open();