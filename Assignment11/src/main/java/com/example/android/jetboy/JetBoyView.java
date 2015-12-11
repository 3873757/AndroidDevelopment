

package com.example.android.jetboy;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.JetPlayer;
import android.media.JetPlayer.OnJetEventListener;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;
public class JetBoyView extends SurfaceView implements SurfaceHolder.Callback {
    public static final int mSuccessThreshold = 50;
    public int mHitStreak = 0;
    public int mHitTotal = 0;
    public int mCurrentBed = 0;
    private Bitmap mTitleBG;
    private Bitmap mTitleBG2;
    class GameEvent {
        public GameEvent() {
            eventTime = System.currentTimeMillis();
        }
        long eventTime;
    }
    class JetGameEvent extends GameEvent {
        public JetGameEvent(JetPlayer player, short segment, byte track, byte channel,
                            byte controller, byte value) {
            this.player = player;
            this.segment = segment;
            this.track = track;
            this.channel = channel;
            this.controller = controller;
            this.value = value;
        }
        public JetPlayer player;
        public short segment;
        public byte track;
        public byte channel;
        public byte controller;
        public byte value;
    }
    class JetBoyThread extends Thread implements OnJetEventListener {
        public static final int STATE_START = -1;
        public static final int STATE_PLAY = 0;
        public static final int STATE_LOSE = 1;
        public static final int STATE_RUNNING = 3;
        public boolean mInitialized = false;
        protected ConcurrentLinkedQueue<GameEvent> mEventQueue = new ConcurrentLinkedQueue<GameEvent>();
        protected Object mKeyContext = null;
        public int mTimerLimit;
        public final int TIMER_LIMIT = 72;
        public int mState;
        boolean mLaserOn = false;
        private Bitmap mBackgroundImageFar;
        private Bitmap mBackgroundImageNear;
        private Bitmap[] mShipFlying = new Bitmap[4];
        private Bitmap[] mBeam = new Bitmap[4];
        private Bitmap[] mExplosions = new Bitmap[4];
        private Bitmap mTimerShell;
        private Bitmap mLaserShot;
        private JetPlayer mJet = null;
        private boolean mJetPlaying = false;
        private Handler mHandler;
        private SurfaceHolder mSurfaceHolder;
        private Context mContext;
        private boolean mRun = false;
        private Timer mTimer = null;
        private TimerTask mTimerTask = null;
        private int mTaskIntervalInMillis = 1000;
        private int mCanvasHeight = 1;
        private int mCanvasWidth = 1;
        private int mShipIndex = 0;
        private int mBGFarMoveX = 0;
        private int mBGNearMoveX = 0;
        private int mJetBoyYMin = 40;
        private int mJetBoyX = 0;
        private int mJetBoyY = 0;
        Resources mRes;
        private boolean muteMask[][] = new boolean[9][32];
        public JetBoyThread(SurfaceHolder surfaceHolder, Context context, Handler handler) {
            mSurfaceHolder = surfaceHolder;
            mHandler = handler;
            mContext = context;
            mRes = context.getResources();
            for (int ii = 0; ii < 8; ii++) {
                for (int xx = 0; xx < 32; xx++) {
                    muteMask[ii][xx] = true;
                }
            }
            muteMask[0][2] = false;
            muteMask[0][3] = false;
            muteMask[0][4] = false;
            muteMask[0][5] = false;
            muteMask[1][2] = false;
            muteMask[1][3] = false;
            muteMask[1][4] = false;
            muteMask[1][5] = false;
            muteMask[1][8] = false;
            muteMask[1][9] = false;
            muteMask[2][2] = false;
            muteMask[2][3] = false;
            muteMask[2][6] = false;
            muteMask[2][7] = false;
            muteMask[2][8] = false;
            muteMask[2][9] = false;
            muteMask[3][2] = false;
            muteMask[3][3] = false;
            muteMask[3][6] = false;
            muteMask[3][11] = false;
            muteMask[3][12] = false;
            muteMask[4][2] = false;
            muteMask[4][3] = false;
            muteMask[4][10] = false;
            muteMask[4][11] = false;
            muteMask[4][12] = false;
            muteMask[4][13] = false;
            muteMask[5][2] = false;
            muteMask[5][3] = false;
            muteMask[5][10] = false;
            muteMask[5][12] = false;
            muteMask[5][15] = false;
            muteMask[5][17] = false;
            muteMask[6][2] = false;
            muteMask[6][3] = false;
            muteMask[6][14] = false;
            muteMask[6][15] = false;
            muteMask[6][16] = false;
            muteMask[6][17] = false;
            muteMask[7][2] = false;
            muteMask[7][3] = false;
            muteMask[7][6] = false;
            muteMask[7][14] = false;
            muteMask[7][15] = false;
            muteMask[7][16] = false;
            muteMask[7][17] = false;
            muteMask[7][18] = false;
            for (int xx = 0; xx < 32; xx++) {
                muteMask[8][xx] = false;
            }
            mState = STATE_START;
            setInitialGameState();
            mTitleBG = BitmapFactory.decodeResource(mRes, R.drawable.title_hori);
            mBackgroundImageFar = BitmapFactory.decodeResource(mRes, R.drawable.background_a);
            mLaserShot = BitmapFactory.decodeResource(mRes, R.drawable.laser);
            mBackgroundImageNear = BitmapFactory.decodeResource(mRes, R.drawable.background_b);
            mShipFlying[0] = BitmapFactory.decodeResource(mRes, R.drawable.ship2_1);
            mShipFlying[1] = BitmapFactory.decodeResource(mRes, R.drawable.ship2_2);
            mShipFlying[2] = BitmapFactory.decodeResource(mRes, R.drawable.ship2_3);
            mShipFlying[3] = BitmapFactory.decodeResource(mRes, R.drawable.ship2_4);
            mBeam[0] = BitmapFactory.decodeResource(mRes, R.drawable.intbeam_1);
            mBeam[1] = BitmapFactory.decodeResource(mRes, R.drawable.intbeam_2);
            mBeam[2] = BitmapFactory.decodeResource(mRes, R.drawable.intbeam_3);
            mBeam[3] = BitmapFactory.decodeResource(mRes, R.drawable.intbeam_4);
            mTimerShell = BitmapFactory.decodeResource(mRes, R.drawable.int_timer);
            mExplosions[0] = BitmapFactory.decodeResource(mRes, R.drawable.asteroid_explode1);
            mExplosions[1] = BitmapFactory.decodeResource(mRes, R.drawable.asteroid_explode2);
            mExplosions[2] = BitmapFactory.decodeResource(mRes, R.drawable.asteroid_explode3);
            mExplosions[3] = BitmapFactory.decodeResource(mRes, R.drawable.asteroid_explode4);
        }
        private void initializeJetPlayer() {
            mJet = JetPlayer.getJetPlayer();
            mJetPlaying = false;
            mJet.clearQueue();
            mJet.setEventListener(this);
            Log.d(TAG, "opening jet file");
            mJet.loadJetFile(mContext.getResources().openRawResourceFd(R.raw.level1));
            Log.d(TAG, "opening jet file DONE");
            mCurrentBed = 0;
            byte sSegmentID = 0;
            Log.d(TAG, " start queuing jet file");
            mJet.queueJetSegment(0, 0, 0, 0, 0, sSegmentID);
            mJet.queueJetSegment(1, 0, 4, 0, 0, sSegmentID);
            mJet.queueJetSegment(1, 0, 4, 1, 0, sSegmentID);
            mJet.setMuteArray(muteMask[0], true);
            Log.d(TAG, " start queuing jet file DONE");
        }
        private void doDraw(Canvas canvas) {
            if (mState == STATE_RUNNING) {
                doDrawRunning(canvas);
            } else if (mState == STATE_START) {
                doDrawReady(canvas);
            } else if (mState == STATE_PLAY || mState == STATE_LOSE) {
                if (mTitleBG2 == null) {
                    mTitleBG2 = BitmapFactory.decodeResource(mRes, R.drawable.title_bg_hori);
                }
                doDrawPlay(canvas);
            }
        }
        private void doDrawRunning(Canvas canvas) {
            mBGFarMoveX = mBGFarMoveX - 1;
            mBGNearMoveX = mBGNearMoveX - 4;
            int newFarX = mBackgroundImageFar.getWidth() - (-mBGFarMoveX);
            if (newFarX <= 0) {
                mBGFarMoveX = 0;
                canvas.drawBitmap(mBackgroundImageFar, mBGFarMoveX, 0, null);
            } else {
                canvas.drawBitmap(mBackgroundImageFar, mBGFarMoveX, 0, null);
                canvas.drawBitmap(mBackgroundImageFar, newFarX, 0, null);
            }
            int newNearX = mBackgroundImageNear.getWidth() - (-mBGNearMoveX);
            if (newNearX <= 0) {
                mBGNearMoveX = 0;
                canvas.drawBitmap(mBackgroundImageNear, mBGNearMoveX, 0, null);
            } else {
                canvas.drawBitmap(mBackgroundImageNear, mBGNearMoveX, 0, null);
                canvas.drawBitmap(mBackgroundImageNear, newNearX, 0, null);
            }
            canvas.drawBitmap(mBeam[mShipIndex], 51 + 20, 0, null);
            mShipIndex++;
            if (mShipIndex == 4)
                mShipIndex = 0;
            canvas.drawBitmap(mShipFlying[mShipIndex], mJetBoyX, mJetBoyY, null);
            if (mLaserOn) {
                canvas.drawBitmap(mLaserShot, mJetBoyX + mShipFlying[0].getWidth(), mJetBoyY
                        + (mShipFlying[0].getHeight() / 2), null);
            }
            canvas.drawBitmap(mTimerShell, mCanvasWidth - mTimerShell.getWidth(), 0, null);
        }
        private void setInitialGameState() {
            mTimerLimit = TIMER_LIMIT;
            mJetBoyY = mJetBoyYMin;
            initializeJetPlayer();
            mTimer = new Timer();
            mInitialized = true;
        }
        private void doDrawReady(Canvas canvas) {
            canvas.drawBitmap(mTitleBG, 0, 0, null);
        }
        private void doDrawPlay(Canvas canvas) {
            canvas.drawBitmap(mTitleBG2, 0, 0, null);
        }
        public void run() {
            while (mRun) {
                Canvas c = null;
                if (mState == STATE_RUNNING) {
                    if (!mJetPlaying) {
                        mInitialized = false;
                        Log.d(TAG, "------> STARTING JET PLAY");
                        mJet.play();
                        mJetPlaying = true;
                    }
                    if (mTimerTask == null) {
                        mTimerTask = new TimerTask() {
                            public void run() {
                            }
                        };
                        mTimer.schedule(mTimerTask, mTaskIntervalInMillis);
                    }
                } else if (mState == STATE_PLAY && !mInitialized) {
                    setInitialGameState();
                } else if (mState == STATE_LOSE) {
                    mInitialized = false;
                }
                try {
                    c = mSurfaceHolder.lockCanvas(null);
                    doDraw(c);
                } finally {
                    if (c != null) {
                        mSurfaceHolder.unlockCanvasAndPost(c);
                    }
                }
            }
        }
        public void setRunning(boolean b) {
            mRun = b;
            if (mRun == false) {
                if (mTimerTask != null)
                    mTimerTask.cancel();
            }
        }
        public int getGameState() {
            synchronized (mSurfaceHolder) {
                return mState;
            }
        }
        public void setGameState(int mode) {
            synchronized (mSurfaceHolder) {
                setGameState(mode, null);
            }
        }
        public void setGameState(int state, CharSequence message) {
            synchronized (mSurfaceHolder) {
                if (mState != state) {
                    mState = state;
                }
                if (mState == STATE_PLAY) {
                    Resources res = mContext.getResources();
                    mBackgroundImageFar = BitmapFactory
                            .decodeResource(res, R.drawable.background_a);
                    mBackgroundImageFar = Bitmap.createScaledBitmap(mBackgroundImageFar,
                            mCanvasWidth * 2, mCanvasHeight, true);
                    mBackgroundImageNear = BitmapFactory.decodeResource(res,
                            R.drawable.background_b);
                    mBackgroundImageNear = Bitmap.createScaledBitmap(mBackgroundImageNear,
                            mCanvasWidth * 2, mCanvasHeight, true);
                } else if (mState == STATE_RUNNING) {
                    mEventQueue.clear();

                    mKeyContext = null;
                }
            }
        }
        public void setSurfaceSize(int width, int height) {
            synchronized (mSurfaceHolder) {
                mCanvasWidth = width;
                mCanvasHeight = height;
                mBackgroundImageFar = Bitmap.createScaledBitmap(mBackgroundImageFar, width * 2,
                        height, true);
                mBackgroundImageNear = Bitmap.createScaledBitmap(mBackgroundImageNear, width * 2,
                        height, true);
            }
        }
        public void onJetNumQueuedSegmentUpdate(JetPlayer player, int nbSegments) {
        }
        public void onJetEvent(JetPlayer player, short segment, byte track, byte channel,
                               byte controller, byte value) {
            mEventQueue.add(new JetGameEvent(player, segment, track, channel, controller, value));
        }
        public void onJetPauseUpdate(JetPlayer player, int paused) {
        }
        public void onJetUserIdUpdate(JetPlayer player, int userId, int repeatCount) {
        }
    }
    public static final String TAG = "JetBoy";
    private JetBoyThread thread;
    public JetBoyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        if (isInEditMode() == false) {
            thread = new JetBoyThread(holder, context, new Handler() {
                public void handleMessage(Message m) {
                    if (m.getData().getString("STATE_LOSE") != null) {
                        Log.d(TAG, "the total was " + mHitTotal);
                        if (mHitTotal >= mSuccessThreshold) {
                        } else {
                        }
                    }
                }
            });
        }
        setFocusable(true);
        Log.d(TAG, "@@@ done creating view!");
    }
    public JetBoyThread getThread() {
        return thread;
    }
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        thread.setSurfaceSize(width, height);
    }
    public void surfaceCreated(SurfaceHolder arg0) {
        thread.setRunning(true);
        thread.start();
    }
    public void surfaceDestroyed(SurfaceHolder arg0) {
        boolean retry = true;
        thread.setRunning(false);
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }

}
