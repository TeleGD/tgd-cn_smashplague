package plague;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public class Skill {
	
	private int id;
	
    private List<Integer> successors;
    
    private int isolement;
    private int persuasion;
    private int cohesion;
    
    private int experienceNeeded;
    
    private String name;
    private String description;
    
    private float x;
    private float y;
    private float width;
    
    public Skill() {
    	successors = new ArrayList<>();
    	
    	x = 100;
    	y = 100;
    	width = 75;
    }

	public void setId(int id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setIsolement(int isolement) {
		this.isolement = isolement;
	}
	
	public void setCohesion(int cohesion) {
		this.cohesion  = cohesion;
	}
	
	public void setPersuasion(int persuasion) {
		this.persuasion = persuasion;
	}
	
	public void setExperienceNeeded(int exp) {
		this.experienceNeeded = exp;
	}
	
	public void addSuccessor(int id) {
		this.successors.add(new Integer(id));
	}

	public int getId() {
		return id;
	}

	public int getExperienceNeeded() {
		return experienceNeeded;
	}

	public int getIsolement() {
		return isolement;
	}

	public int getPersuasion() {
		return persuasion;
	}

	public int getCohesion() {
		return cohesion;
	}

	public List<Integer> getSuccessors() {
		return this.successors;
	}
	
	public String toString() {
		return id + ", " + name + ", " + description + ", successors:" + successors.toString();
	}

	public void render(GameContainer container, StateBasedGame game, Graphics context, Color color) {
		Color currentColor = context.getColor();
		context.setColor(color);
		context.fillRect(x, y, width, width);
		context.setColor(currentColor);
		
	}

	public void setX(int x) {
		this.x = x;		
	}
	
	public void setY(int y) {
		this.y = y;		
	}
}
