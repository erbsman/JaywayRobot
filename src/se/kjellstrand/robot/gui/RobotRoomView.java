package se.kjellstrand.robot.gui;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class RobotRoomView extends View {

    private String TAG = RobotRoomView.class.getCanonicalName();

    private Paint mRoomPaint = new Paint();

    private Paint mBackgroundPaint = new Paint();
    private Paint mRobotPathPaint = new Paint();

    private int mRobotPathStrokeWidth;
    private int mRoomStrokeWidth;

    private Path mWalls;
    private Path mRobotPath;

    private Matrix mMatrix;

    public RobotRoomView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public RobotRoomView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RobotRoomView(Context context) {
        super(context);
    }

    public void defineViewPort(int minX, int minY, int maxX, int maxY, float roomPadding) {
        float roomWidth = (roomPadding * 2) + maxX - minX;
        float roomHeight = (roomPadding * 2) + maxY - minY;

        float xScale = roomWidth / getWidth();
        float yScale = roomHeight / getHeight();

        float scale = 1 / Math.max(xScale, yScale);

        mRobotPathStrokeWidth = (int) (scale * 0.5);
        mRoomStrokeWidth = (int) (scale);

        mMatrix = new Matrix();

        // Move the walls onto 1, 1
        mMatrix.setTranslate(-minX + roomPadding, -minY + roomPadding);
        mMatrix.postScale(scale, scale);

    }

    public void setRobotPath(Path robotPath) {
        mRobotPathPaint.setColor(Color.DKGRAY);
        mRobotPathPaint.setStyle(Paint.Style.STROKE);
        mRobotPathPaint.setStrokeJoin(Paint.Join.ROUND);
        mRobotPathPaint.setStrokeWidth(mRobotPathStrokeWidth);

//        PathEffect pathEffect = new DashPathEffect(new float[] {
//                10, 10
//        }, 3);
//        mRobotPathPaint.setPathEffect(pathEffect);
        
        if (mMatrix != null && robotPath != null) {
            robotPath.transform(mMatrix);
        } else {
            Log.w(TAG, "No matrix set for scaling and translating!!!");
        }
        this.mRobotPath = robotPath;
    }

    public void setWalls(Path walls) {
        mBackgroundPaint.setColor(Color.WHITE);
        mBackgroundPaint.setStyle(Paint.Style.FILL);

        mRoomPaint.setColor(Color.GRAY);
        mRoomPaint.setStyle(Paint.Style.STROKE);
        mRoomPaint.setStrokeJoin(Paint.Join.MITER);
        mRoomPaint.setStrokeCap(Paint.Cap.SQUARE);
        mRoomPaint.setStrokeWidth(mRoomStrokeWidth);

        if (mMatrix != null && walls != null) {
            walls.transform(mMatrix);
        } else {
            Log.w(TAG, "No matrix set for scaling and translating!!!");
        }
        this.mWalls = walls;
    }

    @Override
    public void draw(android.graphics.Canvas canvas) {

        canvas.drawColor(Color.LTGRAY);

        if (mRobotPath != null && !mRobotPath.isEmpty()) {
            canvas.drawPath(mRobotPath, mRobotPathPaint);
        }

        if (mWalls != null && !mWalls.isEmpty()) {
            canvas.drawPath(mWalls, mRoomPaint);
        }
    }

}
