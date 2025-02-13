package UIComponents;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.ArrayList;

public abstract class VisualTerritory implements Serializable {

	public ArrayList<Coordinate> coordinates = new ArrayList<Coordinate>();
	public ArrayList<Coordinate> upScaledCoordinates = new ArrayList<Coordinate>();
	public ArrayList<Coordinate> cardCoordinates = new ArrayList<Coordinate>();
	
	public Coordinate[][] visualBuffer;
	public ArrayList<Coordinate> border = new ArrayList<Coordinate>();
	public Coordinate mainCoordinate;
	public Color color;
	
	public int[] extractXCoords() {
		int[] xCoords = new int[border.size()];
		for(int i = 0; i < border.size(); i++)
			xCoords[i] = border.get(i).xCoord;
		return xCoords;
	}
	
	public int[] extractYCoords() {
		int[] yCoords = new int[border.size()];
		for(int i = 0; i < border.size(); i++)
			yCoords[i] = border.get(i).yCoord;
		return yCoords;
	}
	
	//private boolean even = true; int changeIndex = 0;
	public static final int PIXEL_JUMP = 12;
	public static final int DEFAULT_DRAW_SIZE = PIXEL_JUMP * 7 / 10;
	public static enum SCALE_MODE { NORMAL, UPSCALED };
	public Coordinate drawCoordinate = mainCoordinate;
	public int drawSize = DEFAULT_DRAW_SIZE;
	public SCALE_MODE scale = SCALE_MODE.NORMAL;
	public void paint(Graphics painter, boolean selected) {
		int xCoord = ((drawCoordinate.xCoord - mainCoordinate.xCoord) / PIXEL_JUMP) * PIXEL_JUMP;
		int yCoord = ((drawCoordinate.yCoord - mainCoordinate.yCoord) / PIXEL_JUMP) * PIXEL_JUMP;
		if(scale == SCALE_MODE.NORMAL) {
			for(Coordinate a: coordinates)
				painter.fillRect(xCoord + a.xCoord, yCoord + a.yCoord, drawSize, drawSize); 
		}
		else {
			for(Coordinate a: upScaledCoordinates)
				painter.fillRect(xCoord + a.xCoord, yCoord + a.yCoord, drawSize, drawSize); 
		}
	}
	
	public void resetDrawCoord() {
		drawCoordinate = mainCoordinate;
	}
	
	public static Coordinate getIndexedCoordinate(double xRate, double yRate) {
		int xBound = (int)(xRate * ApplicationFrame.width);
		int yBound =  (int)(yRate * ApplicationFrame.height);
		
		int currX = 0, currY = 0;
		while(currX < xBound) 
			currX += PIXEL_JUMP;
		while(currY < yBound) 
			currY += PIXEL_JUMP;
		return new Coordinate(currX, currY);
	}
	
	public static boolean checkPixelJumpHierarchy(Coordinate check) {
		return check.xCoord % PIXEL_JUMP == 0 && check.yCoord % PIXEL_JUMP == 0;
	}
	
	public static void applyPixelJumpHierarchy(Coordinate mainCoord) {
		mainCoord.xCoord = (mainCoord.xCoord / VisualTerritory.PIXEL_JUMP) * VisualTerritory.PIXEL_JUMP;
		mainCoord.yCoord = (mainCoord.yCoord / VisualTerritory.PIXEL_JUMP) * VisualTerritory.PIXEL_JUMP;
	}

	public abstract VisualTerritory copy();
	public abstract void print();
	public abstract boolean checkItsCorresponding(String checkTag);
	public abstract String getCorrespondingTag();
		
}
