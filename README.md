![GitHub code size in bytes](https://img.shields.io/github/languages/code-size/JobGetabu/KaziChatVoicePlayer) ![GitHub](https://img.shields.io/github/license/JobGetabu/KaziChatVoicePlayer) 
[![](https://jitpack.io/v/JobGetabu/KaziChatVoicePlayer.svg)](https://jitpack.io/#JobGetabu/KaziChatVoicePlayer)
</p>


# ChatVoicePlayer
An Android library to make the implementation of voice/audio messages' playing easier

# Why to use this library? 
- To avoid the unwanted errors of using MediaPlayer in Android
- This is a ready-to-use component to use in playing the chat voice messages in chat applications or any other app
- Easy to use WaveForm for the played sounds

# Features
- Extremely easy to use
- Direct share of the audio file
- Full control of customization (colors, visibilities, shapes) 
- The controls are availabe in XML and programmatically
- WaveForm Seekbar



# Installation
Step 1. Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.JobGetabu:KaziChatVoicePlayer:Tag'
	}

# Usage
1. Add the voice player view in your xml layout: 
```
<me.jagar.chatvoiceplayerlibrary.VoicePlayerView
    android:id="@+id/voicePlayerView"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    app:enableVisualizer="true"
    app:visualizationPlayedColor="#ff7700"
    app:visualizationNotPlayedColor="#727171"
    app:playPauseBackgroundColor="#ff7700"
    app:timingBackgroundColor="#6AFF7700"
    app:seekBarProgressColor="#000"
    app:showShareButton="true"
    app:shareCornerRadius="100"
    app:playPauseCornerRadius="100"
    app:showTiming="true"
    app:viewCornerRadius="100"
    app:viewBackground="#C6C4CF"
    app:progressTimeColor="#000"
    app:seekBarThumbColor="#FFC107"
    app:shareBackgroundColor="#ff7700"
    app:playProgressbarColor="#ff7700"
    app:shareText="SHARE ME"/>
```
2. Now prepare your player view wherever you want in your activity: 
```
voicePlayerView.setAudio(AUDIO_FILE_PATH);
```

* **NOTE:** Make sure to allow READ & WRITE external storage permessions

# Supported functions

`onStop()` //This will avoid many errors if you called it on your activity onStrop() <br>
`onPause()` //If you don't want to play the voice out of your activity/app! <br>
`setViewBackgroundShape(int color, float radius)` //color should be R.color.YOUR_COLOR <br>
`setShareBackgroundShape(int color, float radius)` //color should be R.color.YOUR_COLOR <br>
`setPlayPaueseBackgroundShape(int color, float radius)` //color should be R.color.YOUR_COLOR <br>
`setSeekBarStyle(int progressColor, int thumbColor)` //both colors should be R.color.YOUR_COLOR <br>
`setTimingVisibility(boolean visibility)` <br>
`setShareButtonVisibility(boolean visibility)` <br>
`setShareText(String shareText)`<br>
`showPlayProgressbar()`<br>
`hidePlayProgressbar()`<br>
`refreshPlayer(String audioPath)`<br>

## credits Jagar Yousef

# License 
```
MIT License

Copyright (c) 2020 Job Getabu

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
