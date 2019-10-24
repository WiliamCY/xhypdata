package com.example.xinhuayipin.ui.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xinhuayipin.R;
import com.fgtit.data.Conversions;
import com.fgtit.data.wsq;
import com.fgtit.fpcore.FPMatch;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Timer;
import java.util.TimerTask;

import fgtit.fpengine.constants;
import fgtit.fpengine.fpdevice;


public class MainActivityS extends AppCompatActivity {


    private static fpdevice fpdev = new fpdevice();

    private String sDirectory = "";
    public byte mRefList[][] = new byte[2048][512];
    public int mRefCount = 0;

    private Timer mTimer = null;
    private TimerTask mTimerTask = null;
    private int mWorkmode = 0;
    private static boolean isopening = false;
    private static boolean isworking = false;
    private boolean isContinuous = false;
    private Timer mTmContinu = null;
    private TimerTask mTsContinu = null;

    private TextView tvStatus = null;
    private ImageView ivImage = null;
    private EditText mEditText;
    private Button bt1, bt2, bt10, bt12;

    private byte bmpdata[] = new byte[74806];
    private int bmpsize[] = new int[1];
    private byte fpdata[] = new byte[512];
    private int fpsize[] = new int[1];
    private byte isodata[] = new byte[512];

    private Handler handler = null;
    private int enrolCount = 0;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mains);

        CreateDirectory();
        ReadDataFromFile();

        FPMatch.getInstance().InitMatch(0, "http://www.fgtit.com/");

        bt1 = (Button) findViewById(R.id.button1);
        bt2 = (Button) findViewById(R.id.button2);
        bt10 = (Button) findViewById(R.id.button10);
        bt12 = (Button) findViewById(R.id.button12);

        tvStatus = (TextView) findViewById(R.id.textView1);
        ivImage = (ImageView) findViewById(R.id.imageView1);
        mEditText = (EditText) findViewById(R.id.editText1);
        mEditText.setVisibility(View.GONE);

        fpdev.SetInstance(this);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isopening) return;

                switch (fpdev.OpenDevice()) {
                    case 0:
                        isopening = true;
                        tvStatus.setText("Open Device OK");
                        break;
                    case -1:
                        tvStatus.setText("Link Device Fail");
                        break;
                    case -2:
                        tvStatus.setText("Evaluation version expires");
                        break;
                    case -3:
                        tvStatus.setText("Open Device Fail");
                        break;
                }
            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isopening) {
                    fpdev.CloseDevice();
                    tvStatus.setText("Close");
                    isopening = false;
                }
            }
        });

        //IntentFilter filter = new IntentFilter();
        //filter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED);
        //filter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);
        //filter.setPriority(500);
        //this.registerReceiver(mPermissionReceiver, filter);

        handler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1: {
                        int work = fpdev.GetWorkMsg();
                        int ret = fpdev.GetRetMsg();
                        switch (work) {
                            case constants.FPM_DEVICE:
                                tvStatus.setText("Please Open Device");
                                break;
                            case constants.FPM_PLACE:
                                tvStatus.setText("Place Finger");
                                break;
                            case constants.FPM_LIFT:
                                tvStatus.setText("Lift Finger");
                                break;
                            case constants.FPM_GENCHAR: {
                                TimerStop();
                                isworking = false;
                                if (ret == 1) {
                                    switch (mWorkmode) {
                                        case 1: {//enrol
                                            //fpdev.GetBmpImage(bmpdata,bmpsize);
                                            Bitmap bm1 = BitmapFactory.decodeByteArray(bmpdata, 0, 74806);
                                            //PNG
                                            SavePngFile(bm1, String.valueOf(mRefCount + 1) + ".png");
                                            //WSQ
                                            byte[] inpdata = new byte[73728];
                                            int inpsize = 73728;
                                            System.arraycopy(bmpdata, 1078, inpdata, 0, inpsize);
                                            SaveWsqFile(inpdata, inpsize, String.valueOf(mRefCount + 1) + ".wsq");

                                            tvStatus.setText("Enrol Template OK");
                                            fpdev.GetTemplateByGen(fpdata, fpsize);
                                            // ConverTemplate(fpdata, fpsize[0], isodata);
                                            if (mRefCount < 2048) {
                                                System.arraycopy(fpdata, 0, mRefList[mRefCount], 0, 512);
                                                mRefCount++;
                                                tvStatus.setText("Enrol OK:" + String.valueOf(mRefCount));
                                                WriteDataToFile();
                                            }
                                        }
                                        break;
                                        case 2: {//match
                                            tvStatus.setText("Generate Template OK");
                                            fpdev.GetTemplateByGen(fpdata, fpsize);
                                            // ConverTemplate(fpdata, fpsize[0], isodata);
                                            boolean bok = false;
                                            for (int i = 0; i < mRefCount; i++) {
                                                int score = MatchTemplate(mRefList[i], 378, fpdata, 378);
                                                if (score > 50) {
                                                    tvStatus.setText("Match Return ID:" + String.valueOf(i + 1) + "   Scope:" + String.valueOf(score));
                                                    bok = true;
                                                    //return;
                                                    break;
                                                }
                                            }
                                            if (!bok) tvStatus.setText("No Match");
                                        }
                                        break;
                                    }
                                } else {
                                    tvStatus.setText("Fail");
                                }
                            }
                            if (isContinuous) {
                                NextMatch();
                            }
                            break;
                            case constants.FPM_NEWIMAGE: {
                                fpdev.GetBmpImage(bmpdata, bmpsize);
                                Bitmap bm1 = BitmapFactory.decodeByteArray(bmpdata, 0, 74806);
//                                ivImage.setImageBitmap(bm1);

                            }
                            break;
                            case constants.FPM_TIMEOUT:
                                tvStatus.setText("Time Out");
                                isworking = false;
                                break;
                        }
                    }
                    break;
                }
                super.handleMessage(msg);
            }
        };

        bt10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isopening) {
                    if (isworking) return;
                    mWorkmode = 1;
                    enrolCount = 1;
                    TimerStart();
                    fpdev.GenerateTemplate();
                    isworking = true;
                }
            }
        });

        bt12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isopening) {
                    if (isworking) return;
                    mWorkmode = 2;
                    TimerStart();
                    fpdev.GenerateTemplate();
                    isworking = true;
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        fpdev.CloseDevice();
        isopening = false;
        switch (fpdev.OpenDevice()) {
            case 0:
                isopening = true;
                tvStatus.setText("Open Device OK");
                break;
            case -1:
                tvStatus.setText("Link Device Fail");
                break;
            case -2:
                tvStatus.setText("Evaluation version expires");
                break;
            case -3:
                tvStatus.setText("Open Device Fail");
                break;
        }
    }

    public void ConverTemplate(byte[] reftp, int refsize, byte[] isotp) {
        byte mTmpCoord[] = new byte[512];
        byte mTmpData[] = new byte[512];

        switch (Conversions.getInstance().GetDataType(reftp)) {
            case 1: {
                //STD
                Conversions.getInstance().StdChangeCoord(reftp, 256, mTmpCoord, 1);
                Conversions.getInstance().StdToIso(2, mTmpCoord, isotp);
                //String bsiso=Base64.encodeToString(isotp,0,378,Base64.DEFAULT);
                //mEditText.setText(bsiso);
            }
            break;
            case 2: {
                //ISO 1
                Conversions.getInstance().IsoToStd(1, reftp, mTmpData);
                Conversions.getInstance().StdChangeCoord(mTmpData, 256, mTmpCoord, 1);
                Conversions.getInstance().StdToIso(2, mTmpCoord, isotp);
                //String bsiso=Base64.encodeToString(isotp,0,378,Base64.DEFAULT);
                //mEditText.setText(bsiso);
            }
            break;
            case 3: {
                //ISO 2
                System.arraycopy(reftp, 0, isotp, 0, refsize);
            }
            break;
        }
    }

    public int MatchTemplate(byte[] reftp, int refsize, byte[] mattp, int matsize) {
        byte[] refbuf = new byte[512];
        byte[] matbuf = new byte[512];
        switch (Conversions.getInstance().GetDataType(reftp)) {
            case 1: {    //STD
                System.arraycopy(reftp, 0, refbuf, 0, refsize);
            }
            break;
            case 2: {    //ISO 1
                Conversions.getInstance().IsoToStd(1, reftp, refbuf);
            }
            break;
            case 3: {    //ISO 2
                Conversions.getInstance().IsoToStd(2, reftp, refbuf);
            }
            break;
        }
        switch (Conversions.getInstance().GetDataType(mattp)) {
            case 1: {    //STD
                System.arraycopy(mattp, 0, matbuf, 0, matsize);
            }
            break;
            case 2: {    //ISO 1
                Conversions.getInstance().IsoToStd(1, mattp, matbuf);
            }
            break;
            case 3: {    //ISO 2
                Conversions.getInstance().IsoToStd(2, mattp, matbuf);
            }
            break;
        }
        int mret = FPMatch.getInstance().MatchTemplate(refbuf, matbuf);
        return mret;
    }


    public void TimerStart() {
        if (mTimer == null) {
            mTimer = new Timer();
        }
        if (mTimerTask == null) {
            mTimerTask = new TimerTask() {
                @Override
                public void run() {
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);
                }
            };
        }
        if (mTimer != null && mTimerTask != null) mTimer.schedule(mTimerTask, 200, 200);
    }

    public void TimerStop() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
            mTimerTask.cancel();
            mTimerTask = null;
        }
    }

    public void NextMatch() {
        if (mTmContinu == null) {
            mTmContinu = new Timer();
        }
        if (mTsContinu == null) {
            mTsContinu = new TimerTask() {
                @Override
                public void run() {
                    if (isopening) {
                        if (isworking) return;
                        mWorkmode = 2;
                        TimerStart();
                        fpdev.GenerateTemplate();
                        isworking = true;
                    }
                    if (mTmContinu != null) {
                        mTmContinu.cancel();
                        mTmContinu = null;
                        mTsContinu.cancel();
                        mTsContinu = null;
                    }
                }
            };
        }
        if (mTmContinu != null && mTsContinu != null) mTmContinu.schedule(mTsContinu, 1000, 1000);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        //unregisterReceiver(mPermissionReceiver);
        super.onStop();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            WriteDataToFile();
            fpdev.CloseDevice();
            tvStatus.setText("Close");
            isopening = false;

            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

	/*
	private final BroadcastReceiver mPermissionReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent)
		{
			if (intent.getAction().equals(constants.ACTION_USB_PERMISSION))
			{
				if (!intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false))
				{
				}
				else
				{
					UsbDevice dev = (UsbDevice) intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
					if (dev != null)
					{
							//mainloop(dev);
					}
					else
					{
					}
				}
			}
		}
	};
	*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings: {
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivityS.this);
                dialog.setTitle("Fingerprint Demo");
                dialog.setMessage("\nFingerprint WSQ/PNG images\n stored in the directory.\n" + "(/sdcard/FingerprintReader/)\n ");
                dialog.setPositiveButton("Close", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                dialog.show();
            }
            return true;
        }
        return false;
    }

    public void CreateDirectory() {
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            sDirectory = Environment.getExternalStorageDirectory() + "/FingerprintReader";
            File destDir = new File(sDirectory);
            if (!destDir.exists()) {
                destDir.mkdirs();
            }
        }
    }

    public void ReadDataFromFile() {
        File f = new File(sDirectory + "/fingerprint.dat");
        if (f.exists()) {
        }
        try {
            RandomAccessFile randomFile = new RandomAccessFile(sDirectory + "/fingerprint.dat", "rw");
            long fileLength = randomFile.length();
            mRefCount = (int) (fileLength / 512);
            if (mRefCount > 2000) mRefCount = 2000;
            for (int i = 0; i < mRefCount; i++) {
                randomFile.read(mRefList[i]);
            }
            randomFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void WriteDataToFile() {
        File f = new File(sDirectory + "/fingerprint.dat");
        if (f.exists()) {
            f.delete();
        }
        new File(sDirectory + "/fingerprint.dat");
        try {
            RandomAccessFile randomFile = new RandomAccessFile(sDirectory + "/fingerprint.dat", "rw");
            for (int i = 0; i < mRefCount; i++) {
                randomFile.write(mRefList[i]);
            }
            randomFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void DeleteDataFile() {
        File f = new File(sDirectory + "/fingerprint.dat");
        if (f.exists()) {
            f.delete();
        }
        mRefCount = 0;
    }

    public void SavePngFile(Bitmap bmp, String filename) {
        File f = new File(sDirectory + "/" + filename);
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
        } catch (IOException e) {
        }
    }

    public void SaveWsqFile(byte[] rawdata, int rawsize, String filename) {
        byte[] outdata = new byte[73728];
        int[] outsize = new int[1];
        wsq.getInstance().RawToWsq(rawdata, rawsize, 256, 288, outdata, outsize, 2.833755f);
        try {
            File fs = new File(sDirectory + "/" + filename);
            if (fs.exists()) {
                fs.delete();
            }
            new File(sDirectory + "/" + filename);
            RandomAccessFile randomFile = new RandomAccessFile(sDirectory + "/" + filename, "rw");
            long fileLength = randomFile.length();
            randomFile.seek(fileLength);
            randomFile.write(outdata, 0, outsize[0]);
            randomFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
