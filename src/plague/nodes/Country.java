package plague.nodes;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import plague.Node;
import plague.World;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.GameContainer;

import plague.Node;
import plague.populations.*;

public class Country extends Node {
	private String name;
	private double latitude, longitude;
	private int x, y;
	private Normal normal;
	private Believer believer;
	private Recluse recluse;
	private Heretic heretic;
	private double[][] matrix;
	private float size;
	private double credulity;
	private float dashesStart;
	private double rate;
	private Color color;
	
	private ArrayList<Country> earthLinks;
	private ArrayList<Double> earthWeights;
	
	public Country(int population, double latitude, double longitude, double credulity, World world) {
		this.normal = new Normal(population);
		this.believer = new Believer(0);
		this.recluse = new Recluse(0);
		this.heretic = new Heretic(0);
		this.matrix = new double[][]{
			new double[]{1, 0, 0, 0},
			new double[]{0, 1, 0, 0},
			new double[]{0, 0, 1, 0},
			new double[]{0, 0, 0, 1}
		};
		this.size = (float) Math.pow(Math.log(population), 3);
		this.credulity = credulity;
		this.latitude = latitude;
		this.longitude = longitude;
		this.x = (int)(world.getWidth()*(longitude+180)/360);
		this.y = (int)(world.getHeight()*(-latitude+90)/180);
	}
	
	public Normal getNormal() {
		return this.normal;
	}
	
	public void setNormal(Normal normal) {
		this.normal = normal;
	}
	
	public Believer getBeliever() {
		return this.believer;
	}
	
	public void setBeliever(Believer believer) {
		this.believer = believer;
	}
	
	public Recluse getRecluse() {
		return this.recluse;
	}
	
	public void setRecluse(Recluse recluse) {
		this.recluse = recluse;
	}
	
	public Heretic getHeretic() {
		return this.heretic;
	}
	
	public void setHeretic(Heretic heretic) {
		this.heretic = heretic;
	}
	
	private double getPopulation() {
		return this.normal.getCount() + this.believer.getCount() + this.recluse.getCount() + this.heretic.getCount();
	}
	
	private double getRate() {
		return this.rate;
	}
	
	public Color getColor() {
		int red = (int)(110 + (240-110)*this.rate);
		int green = (int)(35 + (136-35)*this.rate);
		int blue = (int)(104 + (47-104)*this.rate);
		return new Color(65536*red + 256*green + blue);
	}

	public void update(GameContainer container, StateBasedGame game, int delta) {
		double[] inputVector = new double[]{
			this.believer.getCount(),
			this.heretic.getCount(),
			this.normal.getCount(),
			this.recluse.getCount()
		};
		double[] outputVector = new double[]{
			0,
			0,
			0,
			0
		};
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				outputVector[i] += matrix[i][j] * inputVector[j];
			}
		}
		this.believer.setCount(outputVector[0]);
		this.heretic.setCount(outputVector[1]);
		this.normal.setCount(outputVector[2]);
		this.recluse.setCount(outputVector[3]);
		
		this.rate = this.believer.getCount()/this.getPopulation();
		this.color = this.getColor();
		
		// DashesStart = point de départ du traçage des traits (entre 0 et 1).
		// Il varie selon le carré de la crédulité pour une meilleure distinction des populations très crédules
		this.dashesStart = (float) ((this.dashesStart-(delta*this.credulity*this.credulity/5000))%1);
	}
	
	public void render (GameContainer container, StateBasedGame game, Graphics context) {
		context.setColor(this.color);
		context.fillOval(x-size/64, y-size/64, size/32, size/32);
		context.setColor(Color.white);
		context.setLineWidth(2);
		int n = 64;
		double theta1, theta2;
		for (int i = 0; i < n; i+=2) {
			theta1 = 2*Math.PI*(this.dashesStart+(double)i/n);
			theta2 = 2*Math.PI*(this.dashesStart+(double)(i+1)/n);
			context.drawLine((float)(x+size/72*Math.cos(theta1)), (float)(y-size/72*Math.sin(theta1)), (float)(x+size/72*Math.cos(theta2)), (float)(y-size/72*Math.sin(theta2)));;
		}
	}
}
