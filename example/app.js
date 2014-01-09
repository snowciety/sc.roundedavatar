// Create Window and views
var win = Ti.UI.createWindow({
	backgroundColor:'white'
});

var avatar = Ti.UI.createImageView();
var roundedAvatar = Ti.UI.createImageView({
    left: 10
});

var container = Ti.UI.createView({
    top: 10,
    left: 10,
    layout: 'horizontal'
});

// Initialize the module
var ra = require('sc.roundedavatar');

// Fetch an image and set it as the references ImageView's image
avatar.image = 'http://graph.facebook.com/ronald.dev.3/picture?width=200&height=200';

// Create a rounded version of the image with the width and height of 64px
// Note how the module requires you to the image as blob!
var imageBlob = ra.getRoundedAvatar(avatar.toBlob(), 64);

roundedAvatar.image = imageBlob;

container.add(avatar);
container.add(roundedAvatar);

win.add(container);
win.open();
