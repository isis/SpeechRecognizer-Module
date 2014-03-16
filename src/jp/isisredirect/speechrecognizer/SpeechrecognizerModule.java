/*
Copyright (c) 2013 Katsumi ISHIDA. All rights reserved.

Permission is hereby granted, free of charge, to any person obtaining a copy of 
this software and associated documentation files (the "Software"), to deal in the 
Software without restriction, including without limitation the rights to use, copy, 
modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, 
and to permit persons to whom the Software is furnished to do so, subject to the 
following conditions:

The above copyright notice and this permission notice shall be included in all copies
 or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE 
 LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, 
 TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE 
 OR OTHER DEALINGS IN THE SOFTWARE.
 */
package jp.isisredirect.speechrecognizer;

import java.util.ArrayList;
import java.util.List;

import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.KrollModule;
import org.appcelerator.kroll.annotations.Kroll;

import org.appcelerator.titanium.TiApplication;
import org.appcelerator.titanium.TiC;
import org.appcelerator.kroll.common.Log;
import org.appcelerator.kroll.common.TiConfig;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;

@Kroll.module(name = "Speechrecognizer", id = "jp.isisredirect.speechrecognizer")
public class SpeechrecognizerModule extends KrollModule {
	// Standard Debugging variables
	private static final String LCAT = "SpeechrecognizerModule";
	private static final boolean DBG = TiConfig.LOGD;

	// You can define constants with @Kroll.constant, for example:
	// @Kroll.constant public static final String EXTERNAL_NAME = value;

	@Kroll.constant
	public static final String ACTION = "action";
	@Kroll.constant
	public static final String PROMPT = "prompt";
	@Kroll.constant
	public static final String LANGTAG = "langtag";
	@Kroll.constant
	public static final String MAXRESULT = "maxresult";
	@Kroll.constant
	public static final String LANGMODEL = "langmodel";
	@Kroll.constant
	public static final String FREEFORM = "freeform";
	@Kroll.constant
	public static final String WEBSEARCH = "webseach";
	@Kroll.constant
	public static final String PARTIALRESULT = "partialresult";
	@Kroll.constant
	public static final String WEBSEARCHONLY = "websearchonly";
	@Kroll.constant
	public static final String ORIGIN = "origin";
	@Kroll.constant
	public static final String SECURE = "secure";

	@Kroll.constant
	public static final int RECOGNIZE = 1;
	@Kroll.constant
	public static final int WEBSERACH = 2;
	@Kroll.constant
	public static final int HANDSFREE = 3;

	@Kroll.constant
	public static final String LANGUAGEDETAILS = "languagedetails";

	@Kroll.constant
	public static final String LANGUAGE_PREFERENCE = "language_preference";
	@Kroll.constant
	public static final String SUPPORTED_LANGUAGES = "supported_languages";

	@Kroll.constant
	public static final String READYFORSPEECH = "readyforspeech";
	@Kroll.constant
	public static final String BEGINNINGOFSPEECH = "beginningofspeech";
	@Kroll.constant
	public static final String BUFFERRECEIVED = "bufferreceived";
	@Kroll.constant
	public static final String RMSCHANGED = "rmschanged";
	@Kroll.constant
	public static final String ENDOFSPEECH = "endofspeech";
	@Kroll.constant
	public static final String ERROR = "error";
	@Kroll.constant
	public static final String ERRORMESSAGE = "errormessage";
	@Kroll.constant
	public static final String EVENT = "event";
	@Kroll.constant
	public static final String PARTIALRESULTS = "partialresults";
	@Kroll.constant
	public static final String RESULTS = "results";
	@Kroll.constant
	public static final String RMSDB = "rmsdb";
	@Kroll.constant
	public static final String CONFIDENCE_SCORES = "confidence_scores";

	public class LanguageDetailsReceiver extends BroadcastReceiver {
		private static final String TAG = "LanguageDetailsReceiver";

		public LanguageDetailsReceiver() {
		}

		// @Override
		public void onReceive(Context context, Intent intent) {
			List<String> supportedLanguages = new ArrayList<String>();
			String languagePreference = "";
			Bundle results = getResultExtras(true);
			if (results.containsKey(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE)) {
				languagePreference = results
						.getString(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE);
			}
			if (results.containsKey(RecognizerIntent.EXTRA_SUPPORTED_LANGUAGES)) {
				supportedLanguages = results
						.getStringArrayList(RecognizerIntent.EXTRA_SUPPORTED_LANGUAGES);
			}

			KrollDict data = new KrollDict();
			data.put(TiC.EVENT_PROPERTY_SOURCE, SpeechrecognizerModule.this);
			data.put(TiC.PROPERTY_TYPE,
					SpeechrecognizerModule.BEGINNINGOFSPEECH);
			data.put(LANGUAGE_PREFERENCE, languagePreference);
			data.put(SUPPORTED_LANGUAGES,
					supportedLanguages.toArray(new String[0]));
			fireEvent(LANGUAGEDETAILS, data);
		}
	}

	public SpeechrecognizerModule() {
		super();
	}

	@Kroll.onAppCreate
	public static void onAppCreate(TiApplication app) {
		Log.d(LCAT, "inside onAppCreate");
	}

	@Override
	public void onStart(Activity activity) {
		super.onStart(activity);
	}

	@Override
	public void onPause(Activity activity) {
		super.onPause(activity);
	}

	@Override
	public void onResume(Activity activity) {
		super.onResume(activity);
	}

	@Override
	public void onStop(Activity activity) {
		super.onStop(activity);
	}

	@Override
	public void onDestroy(Activity activity) {
		super.onDestroy(activity);
	}

	@Kroll.method
	public boolean isRecognitionAvailable() {
		return SpeechRecognizer.isRecognitionAvailable(TiApplication
				.getInstance());
	}

	@Kroll.method
	public boolean isVoiceSearchHandsFreeAvailable() {
		return Build.VERSION.SDK_INT >= 16;
	}

	@Kroll.method
	public void getLanguageDetails() {
		/*
			Intent detailsIntent = 
				RecognizerIntent.getVoiceDetailsIntent(TiApplication.getInstance().getApplicationContext());
			 { act=android.speech.action.GET_LANGUAGE_DETAILS cmp=com.google.android.googlequicksearchbox/com.google.android.voicesearch.DetailsReceiver }
			dose not work.
		*
		*/
		Intent detailsIntent = new Intent(RecognizerIntent.ACTION_GET_LANGUAGE_DETAILS);
		TiApplication
				.getInstance()
				.getRootActivity()
				.sendOrderedBroadcast(detailsIntent, null,
						new LanguageDetailsReceiver(), null,
						Activity.RESULT_OK, null, null);
	}
}
