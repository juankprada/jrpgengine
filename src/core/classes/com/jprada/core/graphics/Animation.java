package com.jprada.core.graphics;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Juan Camilo Prada Date: 8/16/12 Time: 3:23 PM
 */
public class Animation {

    public static final int ANIMATION_STOP_ON_FINISH = 1;
    public static final int ANIMATION_LOOPING = 2;
    public static final int ANIMATION_CYCLE = 3;

    //	private TextureRegion[] frames;
   // private int fps;
    private int currentFrame = 0;
    //	private double oldTime = 0;
    private int frameIncrement = 1;
    private int numberOfFrames = 0;
    private int animationType = 1;

    private Double timeStart = 0.0;
    private Double timeCurrent = 0.0;

    private int startFrame = 0;
    private int finishFrame = 0;

    private boolean stoped;
    private boolean started;
    private boolean paused;

    private List<SpriteFrame> frames = new ArrayList<SpriteFrame>();
   // private AnimationRange animationRange;

    private boolean animationFinished = false;

    public static double getMillisecs() {
        // return (Sys.getTime() * 1000) / Sys.getTimerResolution();
        return System.nanoTime() / 1000000;
    }



    public int getAnimationType() {
        return animationType;
    }



    public void setAnimationType(int animationType) {
        this.animationType = animationType;
    }


    public Animation(List<SpriteFrame> frames, int animationType) {
        this.frames.addAll(frames);
        this.paused = false;
        this.stoped = true;
        this.started = false;
        this.currentFrame = 0;
        this.frameIncrement = 1;
        this.numberOfFrames = this.frames.size();
        this.animationType = animationType;
        this.animationFinished = false;
        this.startFrame = 0;
        this.finishFrame = this.numberOfFrames - 1;
    }


    public int getNumberOfFrames() {
        return numberOfFrames;
    }

    public void setNumberOfFrames(int numberOfFrames) {

        this.numberOfFrames = numberOfFrames;
        this.finishFrame = this.numberOfFrames -1;

    }

    public int getStartFrame() {
        return startFrame;
    }

    public void setStartFrame(int startFrame) {
        this.startFrame = startFrame;
    }

    public int getFinishFrame() {
        return finishFrame;
    }

    public void setFinishFrame(int finishFrame) {
        this.finishFrame = finishFrame;
    }





    public void setAnimationRange(int startFrame, int finishFrame) {

//		if(startFrame < 0 || finishFrame >= this.numberOfFrames) {
//			throw new IndexOutOfBoundsException("Animation range boundaries error");
//		}

        this.startFrame = startFrame;
        this.finishFrame = finishFrame;
        this.currentFrame = startFrame;

        this.numberOfFrames = Math.abs(finishFrame - startFrame) + 1;
    }




    public SpriteFrame getNextFrame() {
        if(this.animationFinished)
            return this.frames.get(currentFrame);

        if(timeStart == 0) {
            timeStart = getMillisecs();
            timeCurrent = timeStart;
            return this.frames.get(this.startFrame);
        }

        timeCurrent = getMillisecs();
        double diffTime = timeCurrent - timeStart;

        // Check if we should move to next frame
        if(diffTime < this.frames.get(currentFrame).getDuration())
            return this.frames.get(currentFrame);

        timeStart = timeCurrent;

        // Move to next frame
        currentFrame += frameIncrement;

        // If we passed the array of frames then return the last frame of the animation
        if(animationType == ANIMATION_STOP_ON_FINISH && currentFrame == finishFrame + 1) {
            currentFrame = finishFrame;
            animationFinished = true;
            return this.frames.get(currentFrame);
        }
        // If we passed the array of frames then we start over from cero
        else if(animationType == ANIMATION_LOOPING && currentFrame == finishFrame +1) {
            currentFrame = startFrame;
            return this.frames.get(currentFrame);
        }
        else if(animationType == ANIMATION_CYCLE && currentFrame == finishFrame +1) {
            frameIncrement *= -1;
            currentFrame = finishFrame - 1;
        }
        else if(animationType == ANIMATION_CYCLE && currentFrame == startFrame -1) {
            frameIncrement *= -1;
            currentFrame = startFrame + 1;
        }

        return this.frames.get(currentFrame);

    }

    public void setToFrame(int frameNumber) {
        if (frameNumber >= numberOfFrames) {
            throw new IndexOutOfBoundsException(
                    "Frame number specified is larger than number of frames for current animation");
        }

        currentFrame = frameNumber;
    }

    public void reset() {
        this.currentFrame = this.startFrame;
        this.animationFinished = false;
    }

    public boolean isAnimationFinished() {
        return animationFinished;
    }


    public int getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
    }

    public boolean isStoped() {
        return stoped;
    }

    public void setStoped(boolean stoped) {
        this.stoped = stoped;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }



	public List<SpriteFrame> getFrames() {
		return frames;
	}



	public void setFrames(List<SpriteFrame> frames) {
		this.frames = frames;
	}
    
    
}