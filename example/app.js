// This is a test harness for your module
// You should do something interesting in this harness 
// to test out the module and to provide instructions 
// to users on how to use it by example.


// open a single window
var win = Ti.UI.createWindow({
	backgroundColor:'white'
});
var clearbutton = Ti.UI.createButton({
	title : 'clear',
	height : '7%',
	width : '35%',
	top : '90%',
	left : '65%'
});
var startbutton = Ti.UI.createButton({
	title : 'start',
	height : '7%',
	width : '35%',
	top : '80%',
	left : '65%'
});
var langpicker = Ti.UI.createPicker({
	height : '7%',
	width : '35%',
	top : '80%',
	left : '5%'
});
var actionpicker = Ti.UI.createPicker({
	height : '7%',
	width : '35%',
	top : '90%',
	left : '5%'
});

var conTextField = Ti.UI.createTextArea({
	title : 'results',
	editable : false,
	verticalAlign : 'top',
	enableReturnKey : false,
	height : '70%',
	width : '90%',
	top : '5%',
	left : '5%'
});
win.add(clearbutton);
win.add(startbutton);
win.add(conTextField);
win.add(langpicker);
win.add(actionpicker);

var lastselectedRow;
var lastselectedActionRow;

var actions = [
        Ti.UI.createPickerRow({title:'Free form',actionid:"1"}),
        Ti.UI.createPickerRow({title:'Web search',actionid:"2"}),
        Ti.UI.createPickerRow({title:'Hands free search',actionid:"3"})
     ];
actionpicker.add(actions);
lastselectedActionRow = actions[0];

function clearPicker(picker) {
	for (var i = 0, l = picker.columns.length; i < l; ++i) {
		var _col = picker.columns[0];
		if(_col)　{
			var len = _col.rowCount;
			for( var x = len - 1; x >= 0; x-- ) {
			　　　var _row = _col.rows[x];
			　　　_col.removeRow(_row);
			}
			//picker.reloadColumn(_col);
		}
	}
}

// TODO: write your module tests here
var speechrecognizer = require('jp.isisredirect.speechrecognizer');
Ti.API.info("module is => " + speechrecognizer);

conTextField.value = "isRecognitionAvailable:" + speechrecognizer.isRecognitionAvailable() + "\n";
conTextField.value += "isVoiceSearchHandsFreeAvailable:" + speechrecognizer.isVoiceSearchHandsFreeAvailable() + "\n";
var proxy = speechrecognizer.createSpeechRecognizer({});

clearbutton.addEventListener("click", function(e) {
	conTextField.value = "";
});
startbutton.addEventListener("click", function(e) {
	startbutton.enable = false;
	var selectedlang = lastselectedRow.title;
	var selectedaction = lastselectedActionRow.actionid;
	proxy.setLangtag(selectedlang);
	proxy.setAction(selectedaction);
	proxy.start();
});

langpicker.addEventListener('change', function(e) {
    lastselectedRow = e.row;
});
actionpicker.addEventListener('change', function(e) {
    lastselectedActionRow = e.row;
});

var firstselect = 0;

speechrecognizer.addEventListener(speechrecognizer.LANGUAGEDETAILS, function(e) {
	clearPicker(langpicker);
	var langpref = e[speechrecognizer.LANGUAGE_PREFERENCE];
	var langs = e[speechrecognizer.SUPPORTED_LANGUAGES];
	firstselect = 0;
	lastselectedRow = null;
	var pickerdata = [];
	for (var i =0,l = langs.length; i < l; ++i) {
		pickerdata.push(Ti.UI.createPickerRow({
			title : langs[i]
		}));
		if (langs[i] == langpref) {
			firstselect = i;
			lastselectedRow = pickerdata[i];
		}
	}
	if (lastselectedRow == null) {
		lastselectedRow = pickerdata[0];
	}
	langpicker.add(pickerdata);
	
	if (firstselectDone) {
		langpicker.setSelectedRow(0, firstselect);
	}

	startbutton.enable = true;

}); 

var firstselectDone = false;
win.addEventListener('postlayout', function(e){
	if (!firstselectDone) {
		langpicker.setSelectedRow(0, firstselect);
		firstselectDone = true;
	}
});

proxy.addEventListener(speechrecognizer.READYFORSPEECH, function(e) {
	conTextField.value += e.type +"\n";
	startbutton.enable = true;
	
});
proxy.addEventListener(speechrecognizer.BEGINNINGOFSPEECH, function(e) {
	conTextField.value += e.type +"\n";
	startbutton.enable = true;
	
});
proxy.addEventListener(speechrecognizer.BUFFERRECEIVED, function(e) {
	conTextField.value += e.type +"\n";
	startbutton.enable = true;
	
});
/* too many logs
proxy.addEventListener(speechrecognizer.RMSCHANGED, function(e) {
	conTextField.value += e.type +"\n";
	startbutton.enable = true;
	
});
*/
proxy.addEventListener(speechrecognizer.ENDOFSPEECH, function(e) {
	conTextField.value += e.type +"\n";
	startbutton.enable = true;
	
});
proxy.addEventListener(speechrecognizer.ERROR, function(e) {
	conTextField.value += e.type + ":" + e.error + "\n";
	startbutton.enable = true;
	
});
proxy.addEventListener(speechrecognizer.EVENT, function(e) {
	conTextField.value += e.type + "\n";
	startbutton.enable = true;
	
});
proxy.addEventListener(speechrecognizer.PARTIALRESULTS, function(e) {
	conTextField.value += e.type +"\n";
	conTextField.value += e.results +"\n";
	conTextField.value += e.confidence_scores +"\n";
	startbutton.enable = true;
	
});

proxy.addEventListener(speechrecognizer.RESULTS, function(e) {
	conTextField.value += e.type +"\n";
	conTextField.value += e.results +"\n";
	conTextField.value += e.confidence_scores +"\n";
	startbutton.enable = true;
	
});

speechrecognizer.getLanguageDetails();

win.open();


