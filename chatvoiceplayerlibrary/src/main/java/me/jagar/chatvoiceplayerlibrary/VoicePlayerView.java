    /*
    This class is a part of Chat Voice Player View Android Library
    Developed by: Jagar Yousef
    Date: 2019
    */

package me.jagar.chatvoiceplayerlibrary;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.GradientDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.StrictMode;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.net.URLConnection;


    public class VoicePlayerView extends LinearLayout {

    private int playPaueseBackgroundColor, shareBackgroundColor, viewBackgroundColor,
            seekBarProgressColor, seekBarThumbColor, progressTimeColor, timingBackgroundColor,
            visualizationPlayedColor, visualizationNotPlayedColor, playProgressbarColor;
    private float viewCornerRadius, playPauseCornerRadius, shareCornerRadius;
    private boolean showShareButton, showTiming, enableVirtualizer;
    private GradientDrawable playPauseShape, shareShape, viewShape;
    private Context context;
    private String path;
    private String shareTitle = "Share Voice";

    private LinearLayout main_layout, padded_layout, container_layout;
    private ImageView imgPlay, imgPause, imgShare;
    private SeekBar seekBar;
    private ProgressBar progressBar;
    private TextView txtProcess;
    private MediaPlayer mediaPlayer;
    private ProgressBar pb_play;

    private PlayerVisualizerSeekbar seekbarV;

    public VoicePlayerView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.main_view, this);
        this.context = context;
    }

    public VoicePlayerView(Context context,  AttributeSet attrs) {
        super(context, attrs);
        initViews(context, attrs);
        this.context = context;
    }

    public VoicePlayerView(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context, attrs);
        this.context = context;
    }

    private void initViews(Context context, AttributeSet attrs) {

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.VoicePlayerView, 0, 0);

        viewShape = new GradientDrawable();
        playPauseShape = new GradientDrawable();
        shareShape = new GradientDrawable();

        try{
            showShareButton = typedArray.getBoolean(R.styleable.VoicePlayerView_showShareButton, true);
            showTiming = typedArray.getBoolean(R.styleable.VoicePlayerView_showTiming, true);
            viewCornerRadius = typedArray.getFloat(R.styleable.VoicePlayerView_viewCornerRadius, 0);
            playPauseCornerRadius = typedArray.getFloat(R.styleable.VoicePlayerView_playPauseCornerRadius, 0);
            shareCornerRadius = typedArray.getFloat(R.styleable.VoicePlayerView_shareCornerRadius, 0);
            playPaueseBackgroundColor = typedArray.getColor(R.styleable.VoicePlayerView_playPauseBackgroundColor, getResources().getColor(R.color.pink));
            shareBackgroundColor = typedArray.getColor(R.styleable.VoicePlayerView_shareBackgroundColor, getResources().getColor(R.color.pink));
            viewBackgroundColor = typedArray.getColor(R.styleable.VoicePlayerView_viewBackground, getResources().getColor(R.color.white));
            seekBarProgressColor = typedArray.getColor(R.styleable.VoicePlayerView_seekBarProgressColor, getResources().getColor(R.color.pink));
            seekBarThumbColor = typedArray.getColor(R.styleable.VoicePlayerView_seekBarThumbColor, getResources().getColor(R.color.pink));
            progressTimeColor = typedArray.getColor(R.styleable.VoicePlayerView_progressTimeColor, Color.GRAY);
            shareTitle = typedArray.getString(R.styleable.VoicePlayerView_shareText);
            enableVirtualizer = typedArray.getBoolean(R.styleable.VoicePlayerView_enableVisualizer, false);
            timingBackgroundColor = typedArray.getColor(R.styleable.VoicePlayerView_timingBackgroundColor, getResources().getColor(android.R.color.transparent));
            visualizationNotPlayedColor = typedArray.getColor(R.styleable.VoicePlayerView_visualizationNotPlayedColor, getResources().getColor(R.color.gray));
            visualizationPlayedColor = typedArray.getColor(R.styleable.VoicePlayerView_visualizationPlayedColor, getResources().getColor(R.color.pink));
            playProgressbarColor= typedArray.getColor(R.styleable.VoicePlayerView_playProgressbarColor, getResources().getColor(R.color.pink));



        }finally {
            typedArray.recycle();
        }


        LayoutInflater.from(context).inflate(R.layout.main_view, this);
        main_layout = this.findViewById(R.id.collectorLinearLayout);
        padded_layout = this.findViewById(R.id.paddedLinearLayout);
        container_layout = this.findViewById(R.id.containerLinearLayout);
        imgPlay = this.findViewById(R.id.imgPlay);
        imgPause = this.findViewById(R.id.imgPause);
        imgShare = this.findViewById(R.id.imgShare);
        seekBar = this.findViewById(R.id.seekBar);
        progressBar = this.findViewById(R.id.progressBar);
        txtProcess = this.findViewById(R.id.txtTime);
        seekbarV = this.findViewById(R.id.seekBarV);
        pb_play = this.findViewById(R.id.pb_play);


        viewShape.setColor(viewBackgroundColor);
        viewShape.setCornerRadius(viewCornerRadius);
        playPauseShape.setColor(playPaueseBackgroundColor);
        playPauseShape.setCornerRadius(playPauseCornerRadius);
        shareShape.setColor(shareBackgroundColor);
        shareShape.setCornerRadius(shareCornerRadius);

        imgPlay.setBackground(playPauseShape);
        imgPause.setBackground(playPauseShape);
        imgShare.setBackground(shareShape);
        main_layout.setBackground(viewShape);
        seekBar.getProgressDrawable().setColorFilter(seekBarProgressColor, PorterDuff.Mode.SRC_IN);
        seekBar.getThumb().setColorFilter(seekBarThumbColor, PorterDuff.Mode.SRC_IN);

        GradientDrawable timingBackground = new GradientDrawable();
        timingBackground.setColor(timingBackgroundColor);
        timingBackground.setCornerRadius(25);
        txtProcess.setBackground(timingBackground);
        txtProcess.setPadding(16, 0, 16, 0);
        txtProcess.setTextColor(progressTimeColor);


        pb_play.getIndeterminateDrawable().setColorFilter(
                playProgressbarColor,
                android.graphics.PorterDuff.Mode.SRC_IN);



        if (!showShareButton)
            imgShare.setVisibility(GONE);
        if (!showTiming)
            txtProcess.setVisibility(INVISIBLE);

        if (enableVirtualizer){
            seekbarV.setVisibility(VISIBLE);
            seekBar.setVisibility(GONE);
            seekbarV.getProgressDrawable().setColorFilter(getResources().getColor(android.R.color.transparent), PorterDuff.Mode.SRC_IN);
            seekbarV.getThumb().setColorFilter(getResources().getColor(android.R.color.transparent), PorterDuff.Mode.SRC_IN);
            seekbarV.setColors(visualizationPlayedColor, visualizationNotPlayedColor);
        }

    }


    //Set the audio source and prepare mediaplayer

    public void setAudio(String audioPath){
        path = audioPath;
        mediaPlayer =  new MediaPlayer();
        if (path != null) {
            try {
                mediaPlayer.setDataSource(path);
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.prepareAsync();
                mediaPlayer.setVolume(10, 10);
                //START and PAUSE are in other listeners
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        seekBar.setMax(mp.getDuration());
                        if (seekbarV.getVisibility() == VISIBLE){
                            seekbarV.setMax(mp.getDuration());
                        }
                        txtProcess.setText("00:00:00/"+convertSecondsToHMmSs(mp.getDuration() / 1000));
                    }
                });
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        imgPause.setVisibility(View.GONE);
                        imgPlay.setVisibility(View.VISIBLE);
                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        seekBar.setOnSeekBarChangeListener(seekBarListener);
        imgPlay.setOnClickListener(imgPlayClickListener);
        imgPause.setOnClickListener(imgPauseClickListener);
        imgShare.setOnClickListener(imgShareClickListener);
        if (seekbarV.getVisibility() == VISIBLE){
            seekbarV.updateVisualizer(FileUtils.fileToBytes(new File(path)));
        }
        seekbarV.setOnSeekBarChangeListener(seekBarListener);
        seekbarV.updateVisualizer(FileUtils.fileToBytes(new File(path)));
    }



    //Components' listeners

    View.OnClickListener imgPlayClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            imgPause.setVisibility(View.VISIBLE);
            imgPlay.setVisibility(View.GONE);
            mediaPlayer.start();
            try{
                update(mediaPlayer, txtProcess, seekBar, context);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    };



    private SeekBar.OnSeekBarChangeListener seekBarListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser)
            {
                mediaPlayer.seekTo(progress);
                update(mediaPlayer, txtProcess, seekBar, context);
                if (seekbarV.getVisibility() == VISIBLE){
                    seekbarV.updatePlayerPercent((float) mediaPlayer.getCurrentPosition()/mediaPlayer.getDuration());
                }
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            imgPause.setVisibility(View.GONE);
            imgPlay.setVisibility(View.VISIBLE);
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            imgPlay.setVisibility(View.GONE);
            imgPause.setVisibility(View.VISIBLE);
            mediaPlayer.start();

        }
    };

    View.OnClickListener imgPauseClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            imgPause.setVisibility(View.GONE);
            imgPlay.setVisibility(View.VISIBLE);
            mediaPlayer.pause();
        }
    };

    View.OnClickListener imgShareClickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            ((Activity) context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    imgShare.setVisibility(GONE);
                    progressBar.setVisibility(VISIBLE);
                }
            });
            File file = new File(path);
            if (file.exists()){
                Intent intentShareFile = new Intent(Intent.ACTION_SEND);
                intentShareFile.setType(URLConnection.guessContentTypeFromName(file.getName()));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                    StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                    StrictMode.setVmPolicy(builder.build());
                }
                    intentShareFile.putExtra(Intent.EXTRA_STREAM,
                            Uri.parse("file://"+file.getAbsolutePath()));

                context.startActivity(Intent.createChooser(intentShareFile, shareTitle));
            }
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(GONE);
                            imgShare.setVisibility(VISIBLE);
                        }
                    });

                }
            }, 500);

        }
    };

    //Updating seekBar in realtime
    private void update(final MediaPlayer mediaPlayer, final TextView time, final SeekBar seekBar, final Context context) {
        ((Activity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                seekBar.setProgress(mediaPlayer.getCurrentPosition());
                if (seekbarV.getVisibility() == VISIBLE){
                    seekbarV.setProgress(mediaPlayer.getCurrentPosition());
                    seekbarV.updatePlayerPercent((float) mediaPlayer.getCurrentPosition() / mediaPlayer.getDuration());
                }



                if (mediaPlayer.getDuration() - mediaPlayer.getCurrentPosition() > 100) {
                    time.setText(convertSecondsToHMmSs(mediaPlayer.getCurrentPosition() / 1000) + " / " + convertSecondsToHMmSs(mediaPlayer.getDuration() / 1000));
                }
                else {
                    time.setText(convertSecondsToHMmSs(mediaPlayer.getDuration() / 1000));
                    seekBar.setProgress(0);
                    if (seekbarV.getVisibility() == VISIBLE){
                        seekbarV.updatePlayerPercent(0);
                        seekbarV.setProgress(0);
                    }
                }
                Handler handler = new Handler();
                try{
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            try{
                                if (mediaPlayer.getCurrentPosition() > -1) {
                                    try {
                                        update(mediaPlayer, time, seekBar, context);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    };
                    handler.postDelayed(runnable, 2);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
    }

    //Convert long milli seconds to a formatted String to display it

    private static String convertSecondsToHMmSs(long seconds) {
        long s = seconds % 60;
        long m = (seconds / 60) % 60;
        long h = (seconds / (60 * 60)) % 24;
        return String.format("%02d:%02d:%02d", h,m,s);
    }

    //These both functions to avoid mediaplayer errors

    public void onStop(){
        try{
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void onPause(){
        try{
            if (mediaPlayer != null){
                if (mediaPlayer.isPlaying())
                    mediaPlayer.pause();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        imgPause.setVisibility(View.GONE);
        imgPlay.setVisibility(View.VISIBLE);
    }


  // Programmatically functions

   public void setViewBackgroundShape(int color, float radius){
       GradientDrawable shape = new GradientDrawable();
       shape.setColor(getResources().getColor(color));
       shape.setCornerRadius(radius);
       main_layout.setBackground(shape);
   }
    public void setShareBackgroundShape(int color, float radius){
        GradientDrawable shape = new GradientDrawable();
        shape.setColor(getResources().getColor(color));
        shape.setCornerRadius(radius);
        imgShare.setBackground(shape);
    }
    public void setPlayPaueseBackgroundShape(int color, float radius){
        GradientDrawable shape = new GradientDrawable();
        shape.setColor(getResources().getColor(color));
        shape.setCornerRadius(radius);
        imgPause.setBackground(shape);
        imgPlay.setBackground(shape);
    }
    public void setSeekBarStyle(int progressColor, int thumbColor){
        seekBar.getProgressDrawable().setColorFilter(getResources().getColor(progressColor), PorterDuff.Mode.SRC_IN);
        seekBar.getThumb().setColorFilter(getResources().getColor(thumbColor), PorterDuff.Mode.SRC_IN);
    }
    public void setTimingVisibility(boolean visibility){
       if (!visibility)
           txtProcess.setVisibility(INVISIBLE);
       else
           txtProcess.setVisibility(VISIBLE);
    }
    public void setShareButtonVisibility(boolean visibility){
        if (!visibility)
            imgShare.setVisibility(GONE);
        else
            imgShare.setVisibility(VISIBLE);
    }
    public void setShareText(String shareText){
       shareTitle = shareText;
    }

    public void showPlayProgressbar(){
        imgPlay.setVisibility(GONE);
        pb_play.setVisibility(VISIBLE);
    }

    public void hidePlayProgresbar(){
        pb_play.setVisibility(GONE);
        imgPlay.setVisibility(VISIBLE);
    }

        public void refreshPlayer(String audioPath){
            path = audioPath;
            mediaPlayer = null;
            mediaPlayer =  new MediaPlayer();
            if (path != null) {
                try {
                    mediaPlayer.setDataSource(path);
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mediaPlayer.prepare();
                    mediaPlayer.setVolume(10, 10);
                    //START and PAUSE are in other listeners
                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            seekBar.setMax(mp.getDuration());
                            if (seekbarV.getVisibility() == VISIBLE){
                                seekbarV.setMax(mp.getDuration());
                            }
                            txtProcess.setText("00:00:00/"+convertSecondsToHMmSs(mp.getDuration() / 1000));
                        }
                    });
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            imgPause.setVisibility(View.GONE);
                            imgPlay.setVisibility(View.VISIBLE);
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            seekBar.setOnSeekBarChangeListener(seekBarListener);
            imgPlay.setOnClickListener(imgPlayClickListener);
            imgPause.setOnClickListener(imgPauseClickListener);
            imgShare.setOnClickListener(imgShareClickListener);
            if (seekbarV.getVisibility() == VISIBLE){
                seekbarV.updateVisualizer(FileUtils.fileToBytes(new File(path)));
                seekbarV.setOnSeekBarChangeListener(seekBarListener);
                seekbarV.updateVisualizer(FileUtils.fileToBytes(new File(path)));
            }
        }


    public void refreshVisualizer(){
        if (seekbarV.getVisibility() == VISIBLE){
            seekbarV.updateVisualizer(FileUtils.fileToBytes(new File(path)));
        }
    }
    public ProgressBar getPlayProgressbar(){
        return pb_play;
    }

        public int getPlayPaueseBackgroundColor() {
            return playPaueseBackgroundColor;
        }

        public void setPlayPaueseBackgroundColor(int playPaueseBackgroundColor) {
            this.playPaueseBackgroundColor = playPaueseBackgroundColor;
        }

        public int getShareBackgroundColor() {
            return shareBackgroundColor;
        }

        public void setShareBackgroundColor(int shareBackgroundColor) {
            this.shareBackgroundColor = shareBackgroundColor;
        }

        public int getViewBackgroundColor() {
            return viewBackgroundColor;
        }

        public void setViewBackgroundColor(int viewBackgroundColor) {
            this.viewBackgroundColor = viewBackgroundColor;
        }

        public int getSeekBarProgressColor() {
            return seekBarProgressColor;
        }

        public void setSeekBarProgressColor(int seekBarProgressColor) {
            this.seekBarProgressColor = seekBarProgressColor;
        }

        public int getSeekBarThumbColor() {
            return seekBarThumbColor;
        }

        public void setSeekBarThumbColor(int seekBarThumbColor) {
            this.seekBarThumbColor = seekBarThumbColor;
        }

        public int getProgressTimeColor() {
            return progressTimeColor;
        }

        public void setProgressTimeColor(int progressTimeColor) {
            this.progressTimeColor = progressTimeColor;
        }

        public int getTimingBackgroundColor() {
            return timingBackgroundColor;
        }

        public void setTimingBackgroundColor(int timingBackgroundColor) {
            this.timingBackgroundColor = timingBackgroundColor;
        }

        public int getVisualizationPlayedColor() {
            return visualizationPlayedColor;
        }

        public void setVisualizationPlayedColor(int visualizationPlayedColor) {
            this.visualizationPlayedColor = visualizationPlayedColor;
        }

        public int getVisualizationNotPlayedColor() {
            return visualizationNotPlayedColor;
        }

        public void setVisualizationNotPlayedColor(int visualizationNotPlayedColor) {
            this.visualizationNotPlayedColor = visualizationNotPlayedColor;
        }

        public int getPlayProgressbarColor() {
            return playProgressbarColor;
        }

        public void setPlayProgressbarColor(int playProgressbarColor) {
            this.playProgressbarColor = playProgressbarColor;
        }

        public float getViewCornerRadius() {
            return viewCornerRadius;
        }

        public void setViewCornerRadius(float viewCornerRadius) {
            this.viewCornerRadius = viewCornerRadius;
        }

        public float getPlayPauseCornerRadius() {
            return playPauseCornerRadius;
        }

        public void setPlayPauseCornerRadius(float playPauseCornerRadius) {
            this.playPauseCornerRadius = playPauseCornerRadius;
        }

        public float getShareCornerRadius() {
            return shareCornerRadius;
        }

        public void setShareCornerRadius(float shareCornerRadius) {
            this.shareCornerRadius = shareCornerRadius;
        }

        public boolean isShowShareButton() {
            return showShareButton;
        }

        public void setShowShareButton(boolean showShareButton) {
            this.showShareButton = showShareButton;
        }

        public boolean isShowTiming() {
            return showTiming;
        }

        public void setShowTiming(boolean showTiming) {
            this.showTiming = showTiming;
        }

        public boolean isEnableVirtualizer() {
            return enableVirtualizer;
        }

        public void setEnableVirtualizer(boolean enableVirtualizer) {
            this.enableVirtualizer = enableVirtualizer;
        }

        public GradientDrawable getPlayPauseShape() {
            return playPauseShape;
        }

        public void setPlayPauseShape(GradientDrawable playPauseShape) {
            this.playPauseShape = playPauseShape;
        }

        public GradientDrawable getShareShape() {
            return shareShape;
        }

        public void setShareShape(GradientDrawable shareShape) {
            this.shareShape = shareShape;
        }

        public GradientDrawable getViewShape() {
            return viewShape;
        }

        public void setViewShape(GradientDrawable viewShape) {
            this.viewShape = viewShape;
        }

        public void setContext(Context context) {
            this.context = context;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getShareTitle() {
            return shareTitle;
        }

        public void setShareTitle(String shareTitle) {
            this.shareTitle = shareTitle;
        }

        public LinearLayout getMain_layout() {
            return main_layout;
        }

        public void setMain_layout(LinearLayout main_layout) {
            this.main_layout = main_layout;
        }

        public LinearLayout getPadded_layout() {
            return padded_layout;
        }

        public void setPadded_layout(LinearLayout padded_layout) {
            this.padded_layout = padded_layout;
        }

        public LinearLayout getContainer_layout() {
            return container_layout;
        }

        public void setContainer_layout(LinearLayout container_layout) {
            this.container_layout = container_layout;
        }

        public ImageView getImgPlay() {
            return imgPlay;
        }

        public void setImgPlay(ImageView imgPlay) {
            this.imgPlay = imgPlay;
        }

        public ImageView getImgPause() {
            return imgPause;
        }

        public void setImgPause(ImageView imgPause) {
            this.imgPause = imgPause;
        }

        public ImageView getImgShare() {
            return imgShare;
        }

        public void setImgShare(ImageView imgShare) {
            this.imgShare = imgShare;
        }

        public SeekBar getSeekBar() {
            return seekBar;
        }

        public void setSeekBar(SeekBar seekBar) {
            this.seekBar = seekBar;
        }

        public ProgressBar getProgressBar() {
            return progressBar;
        }

        public void setProgressBar(ProgressBar progressBar) {
            this.progressBar = progressBar;
        }

        public TextView getTxtProcess() {
            return txtProcess;
        }

        public void setTxtProcess(TextView txtProcess) {
            this.txtProcess = txtProcess;
        }

        public MediaPlayer getMediaPlayer() {
            return mediaPlayer;
        }

        public void setMediaPlayer(MediaPlayer mediaPlayer) {
            this.mediaPlayer = mediaPlayer;
        }

        public ProgressBar getPb_play() {
            return pb_play;
        }

        public void setPb_play(ProgressBar pb_play) {
            this.pb_play = pb_play;
        }

        public PlayerVisualizerSeekbar getSeekbarV() {
            return seekbarV;
        }

        public void setSeekbarV(PlayerVisualizerSeekbar seekbarV) {
            this.seekbarV = seekbarV;
        }

        public OnClickListener getImgPlayClickListener() {
            return imgPlayClickListener;
        }

        public void setImgPlayClickListener(OnClickListener imgPlayClickListener) {
            this.imgPlayClickListener = imgPlayClickListener;
        }

        public SeekBar.OnSeekBarChangeListener getSeekBarListener() {
            return seekBarListener;
        }

        public void setSeekBarListener(SeekBar.OnSeekBarChangeListener seekBarListener) {
            this.seekBarListener = seekBarListener;
        }

        public OnClickListener getImgPauseClickListener() {
            return imgPauseClickListener;
        }

        public void setImgPauseClickListener(OnClickListener imgPauseClickListener) {
            this.imgPauseClickListener = imgPauseClickListener;
        }

        public OnClickListener getImgShareClickListener() {
            return imgShareClickListener;
        }

        public void setImgShareClickListener(OnClickListener imgShareClickListener) {
            this.imgShareClickListener = imgShareClickListener;
        }
    }
