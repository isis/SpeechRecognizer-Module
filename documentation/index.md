# *speechrecognizer Module*

## Description

*speechrecognizer Module* allows you to use full features of Android SpeechRecognizer API.

You can 

1) use three actions of Free Form, Web search and Hands free.

2) select the language from many languages which SpeechRecognizer service supports.

3) receive all events from SpeechRecognizer service (error, changeRms etc.)

4) check the supported features of Android device that SpeechRecognizer module is running on.

5) do safely everything because of SpeechRecognizer module handles the differrce of API Lavels (currently support API Level 8-16)

6) make your own custom UI design for SpeechRecognize.  *SpeechRecognizer module* uses direct mode of SpeechRecognizer API.

## Requrement

Android min-sdk: Android 2.2 (API Level 8)
Titanium 2.1.0.GA

## Accessing the speechrecognizer Module

To access this module from JavaScript, you would do the following:

	var speechrecognizerModule = require("jp.isisredirect.speechrecognizer");

The speechrecognizerModule variable is a reference to the Module object.	

To create speechrecognizer object from JavaScript, you would do the following:

	var speechrecognizer = speechrecognizerModule.createSpeechRecognizer();

The speechrecognizer variable is a reference to the SpeechRecognizer object.	

## Reference

speechrecognizerModule object is coresponding to the static methods on SpeechRecognizer API.

On the other hand, SpeechRecognizer object is coresponding to the non static methods.

### module methods
#### speechrecognizerModule.isRecognitionAvailable()
Whether Android device supports SpeechRecognizer.

##### Parameters

+ void

##### Returns

+ Boolean

true if Android device supports SpeechRecognizer.

#### speechrecognizerModule.isVoiceSearchHandsFreeAvailable()
Whether Android device supports Voice Search Hands Free feature.

##### Parameters

+ void

##### Returns

+ Boolean

true if Android device supports Voice Search Hands Free feature.

#### speechrecognizerModule.getLanguageDetails() 

Requests the language informations supported by SpeechRecognizer.

This method's result is priovided by *languagedetails* event.

##### Parameters

+ void

##### Returns

+ void

More technical details is here:

http://developer.android.com/reference/android/speech/RecognizerIntent.html#ACTION_GET_LANGUAGE_DETAILS

#### speechrecognizerModule.createSpeechRecognizer([Dictionary\<SpeechRecognizer\> parameters])
create SpeechRecognizer object.

##### Parameters

+ parameters: Dictionary\<SpeechRecognizer\>  (optional)

Properties to set on a new object, including any defined by SpeechRecognizer

##### Returns

+ SpeechRecognizer object

### module events
#### languagedetails
Fired when the results of speechrecognizerModule.getLanguageDetails() are ready after required by speechrecognizerModule.getLanguageDetails().

##### Properties

+ source : Object

Source object that fired the event.

+ type : String

Name of the event fired.

+ language_preference : String

represents the current language preference this user has specified - a locale string like "en-US".

+ supported_languages : Array of Strings

represents the languages supported by this implementation of voice recognition - a Array of strings like "en-US", "cmn-Hans-CN", etc.


### SpeechRecognizer properties
#### action:Number
specifies the action of SpeechRecognizer.

there are three actions 

1: Speech

2: Web search

3: Voice Search Hands Free




More technical details is here:

<http://developer.android.com/reference/android/speech/RecognizerIntent.html#ACTION_RECOGNIZE_SPEECH>

<http://developer.android.com/reference/android/speech/RecognizerIntent.html#ACTION_WEB_SEARCH>

<http://developer.android.com/reference/android/speech/RecognizerIntent.html#ACTION_VOICE_SEARCH_HANDS_FREE>

#### langtag : String
informs the recognizer to perform speech recognition in a language.

You can use one of *supported_languanges* property of *languagedetails* event.

##### default
Android device setting

#### langmodel : String
Informs the recognizer which speech model to prefer when performing action.

available values are :

+ "freeform"	(speechrecognizerModule.FREEFORM predefined)

+ "websearch"	(speechrecognizerModule.WEBSEARCH predefined)

##### default
"freeform"

#### maxresult : Number
specifies limit on the maximum number of results to return when performing action.

##### default
1

#### partialresult : Boolean
indicate whether partial results should be returned by the recognizer as the user speaks.

##### default
false

#### websearchonly : Boolean
be used with action *web search*, to indicate whether to only fire web searches in response to a user's speech.

(note : this property may have no effect in this time. Because of direct mode recognition used in module, or the implementation of the recognizer service on current Android device.

##### default
false


#### origin : String
can be used with action *web search* to indicate the referer url of a page in which speech was requested.

(note : this property may have no effect in this time. Because of direct mode recognition used in module, or the implementation of the recognizer service on current Android device.

##### default
""

#### secure : Boolean
indicates that a "hands free" voice search was performed while the device was in a secure mode

### SpeechRecognizer methods
#### speechrecognizer.start()
Starts listening for speech.

##### Parameters

+ void

##### Returns

+ void

#### speechrecognizer.cancel()
Cancels the speech recognition.

##### Parameters

+ void

##### Returns

+ void

#### speechrecognizer.stop()
Stops listening for speech.

##### Parameters

+ void

##### Returns

+ void

#### speechrecognizer.release()
release recognizer resources.

##### Parameters

+ void

##### Returns

+ void


#### getErrorMessageFromErrorCode(int error):String
gets error message from error code which is provided in *error* event.

##### Parameters

+ error:Number

error code received in error event.

##### Returns

+ String

error string that is correcponding to error code.

### SpeechRecognizer events
#### readyforspeech

##### Properties

+ source : Object

Source object that fired the event.

+ type : String

Name of the event fired.

#### beginningofspeech
The user has started to speak.

##### Properties
+ source : Object

Source object that fired the event.

+ type : String

Name of the event fired.

#### bufferreceived
More sound has been received. 

The purpose of this function is to allow giving feedback to the user regarding the captured audio. 

There is no guarantee that this method will be called (depend on implementation of the recognizer service).

##### Properties

+ source : Object

Source object that fired the event.

+ type : String

Name of the event fired.


#### rmschanged
The sound level in the audio stream has changed. There is no guarantee that this method will be called.
There is no guarantee that this method will be called (depend on implementation of the recognizer service).

##### Properties

+ source : Object

Source object that fired the event.

+ type : String

Name of the event fired.

+ rmsdb : float

the new RMS dB value

#### endofspeech
Called after the user stops speaking.

##### Properties

+ source : Object

Source object that fired the event.

+ type : String

Name of the event fired.

#### error
A network or recognition error occurred.

##### Properties

+ source : Object

Source object that fired the event.

+ type : String

Name of the event fired.

+ error: Number

error code

#### event
Reserved for adding future events.

##### Properties

+ source : Object

Source object that fired the event.

+ type : String

Name of the event fired.

#### partialresults
Called when partial recognition results are available. 

##### Properties

+ source : Object

Source object that fired the event.

+ type : String

Name of the event fired.

+ results: Array of Strings

the possible recognition results, where the first element is the most likely candidate.

+ confidence_scores : Array of floats

same size array as results, includes the confidence score of each result.

This value is optional and might not be provided.

The confidence score is ranging from 0.0 to 1.0, or -1 to represent an unavailable confidence score.

More technicall details :

<http://developer.android.com/reference/android/speech/SpeechRecognizer.html#CONFIDENCE_SCORES>
 

#### results
Called when recognition results are ready.

##### Properties

+ source : Object

Source object that fired the event.

+ type : String

Name of the event fired.

+ results: Array of Strings

the possible recognition results, where the first element is the most likely candidate.

+ confidence_scores : Array of floats

same size array as results, includes the confidence score of each result.

This value is optional and might not be provided.

The confidence score is ranging from 0.0 to 1.0, or -1 to represent an unavailable confidence score.

More technicall details :

<http://developer.android.com/reference/android/speech/SpeechRecognizer.html#CONFIDENCE_SCORES>
 


## Usage
The most simply example of usage of SpeechRecoginither module is below:

	var speechrecognizerModule = require('jp.isisredirect.speechrecognizer');
	Ti.API.info("module is => " + speechrecognizerModule);
	var speechrecognizer = speechrecognizerModule.createSpeechRecognizer();
	Ti.API.info("proxy is => " + speechrecognizer);
	speechrecognizer.addEventListener(speechrecognizerModule.READYFORSPEECH, function(e) {
		// notify to user that Recognize is ready	
	});
	speechrecognizer.addEventListener(speechrecognizerModule.BEGINNINGOFSPEECH, function(e) {
		// notify to user that Recognizer detected user's voice
	});
	speechrecognizer.addEventListener(speechrecognizerModule.RESULTS, function(e) {
		// recognized results in e.results
	});
	speechrecognizer.start();

For more deep usage, you see /example/app.js
 
## Author

Kastumi ISHIDA (isis re-direct) in k.i.office.

isis.redirect4@gmail.com


## License

Copyright (c) 2013 Katsumi ISHIDA. All rights reserved.

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.