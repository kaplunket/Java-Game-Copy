package dev.game.creatures;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;
import java.awt.image.BufferedImage;
import dev.game.Handler;
import dev.game.entity.Entity;
import dev.launcher.Animation;
import dev.launcher.Assets;

public class Bull extends Creature {

	private boolean aggressive = false;
	private boolean face=false;
	private boolean alwaysAggressive;
	private boolean attacking = false;
	private Animation animDown,animUp,animLeft,animRight;
	private long lastMoveTimer,moveCooldown=1500,moveTimer=moveCooldown;
	private Rectangle cb =this.getCollisionBounds(0,0);
	private Rectangle ar= new Rectangle();
	private Random rand = new Random();

	//constructor
	public Bull(Handler handler, float x, float y, int width, int height, boolean fixedAggre) {
		super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH,Creature.DEFAULT_CREATURE_HEIGHT);
		id=6;
		bounds.x=16;
		bounds.y=32;
		bounds.width=32;
		bounds.height=32;
		speed=Creature.DEFAULT_SPEED/6;
		alwaysAggressive = fixedAggre;
		name="Bull";
		health=12;
		//animations
		animDown = new Animation(200,Assets.bull_down);
		animLeft = new Animation(200,Assets.bull_left);
		animUp = new Animation(200,Assets.bull_up);
		animRight = new Animation(200,Assets.bull_right);

	}

	//tick animations
	private void tickAnim(){
		animDown.tick();
		animUp.tick();
		animRight.tick();
		animLeft.tick();
	}


	@Override
	public void tick() {
		tickAnim();
		flickerDecay();
		getInput();
		move();
		if(!stunned) {
			aggression();
			checkAttacks();
		}
		stunDecay();
		adjustBounds();
	}

	//change bounds based on direction facing
	private void adjustBounds() {
		if((lastDirection==Facing.LEFT)||(lastDirection==Facing.RIGHT)) {		
			bounds.x=5;
			bounds.y=20;
			bounds.width=60;
			bounds.height=32;
		}else {
			bounds.x=16;
			bounds.y=20;
			bounds.width=32;
			bounds.height=40;
		}
	}

	//check if creature will attack
	private void checkAttacks() {
		if(!aggressive && !alwaysAggressive) {
			return;
		}
		if(!face) {
			return;
		}
		if(xMove==1||xMove==-1||yMove==-1||yMove==1) {
			return;
		}
		attacking = true;

		//attack in front of creature and run in a line
		cb =this.getCollisionBounds(0,0);
		ar= new Rectangle();
		int arSize=15;
		ar.width=arSize;
		ar.height=arSize;
		xMove=0;
		yMove=0;
		if(lastDirection==Facing.UP) {
			ar.x=cb.x;
			ar.y=cb.y-arSize;
			ar.width=cb.width;
			yMove = -4;
		}
		else if(lastDirection==Facing.DOWN) {
			ar.x=cb.x;
			ar.y=cb.y+cb.height;
			ar.width=cb.width;
			yMove = 4;
		}
		else if(lastDirection==Facing.LEFT) {
			ar.x=cb.x-arSize;
			ar.y=cb.y;
			ar.height=cb.height;
			xMove = -4;

		}
		else if(lastDirection==Facing.RIGHT) {
			ar.x=cb.x+cb.width;
			ar.y=cb.y;
			ar.height=cb.height;
			xMove = 4;
		}

		for(Entity e : handler.getWorld().getEntityManager().getEntities()) {
			if(e.equals(this)) {continue;}
			if(e.getCollisionBounds(0, 0).intersects(ar)) {
				int deltaX=(int) ((this.getCollisionBounds(0, 0).x+this.getCollisionBounds(0, 0).width/2) - (e.getCollisionBounds(0, 0).x+e.getCollisionBounds(0, 0).width/2));
				int deltaY=(int) ((this.getCollisionBounds(0, 0).y+this.getCollisionBounds(0, 0).height/2) - (e.getCollisionBounds(0, 0).y+e.getCollisionBounds(0, 0).height/2));
				//hurt whoever got hit
				e.hurt(damage,deltaX,deltaY);
				//stun user after attack
				attacking=false;
				stunned=true;
				stunnedDuration=150;
			}
		}

	}


	@Override
	public void render(Graphics g) {
		if (damageFlicker%20<15) {
			g.drawImage(getCurrentAnimationFrame(),(int)(x-handler.getGameCamera().getxOffset()),(int)(y-handler.getGameCamera().getyOffset()),width,height,null);
		}

	}
	//get aggressive if nearby
	public void aggression() {
		//circle detection of radius 300
		if ((Math.pow(x-handler.getWorld().getEntityManager().getPlayer().getX(), 2) + Math.pow(y-handler.getWorld().getEntityManager().getPlayer().getY(),2))<Math.pow(300, 2)){
			setAggressive(true);
		} else {
			setAggressive(false);
			face=false;
			attacking = false;
		}
	}
	
	//set movement
	private void getInput() {
		if(stunned) {
			xMove = 0;
			yMove = 0;
			attacking = false;
			aggressive = false;
			return;
		}
		//chase player
		if ((aggressive || alwaysAggressive) && !attacking) {
			xMove = 0;
			yMove = 0;
			float xDelta = x-handler.getWorld().getEntityManager().getPlayer().getX();
			float yDelta = y-handler.getWorld().getEntityManager().getPlayer().getY();


			if (Math.abs(xDelta) < 15){
				if (!face){//face the player before shooting
					face=true;
					if(yDelta>0) {
						yMove=-1;
					}else if(yDelta<0) {
						yMove=1;
					}
				}
			}else if(Math.abs(yDelta)<15){
				if (!face){//face the player before shooting
					face=true;
					if(xDelta>0) {
						xMove=-1;
					}else if(xDelta<0) {
						xMove=1;
					}
				}
			}else if (Math.abs(xDelta)<Math.abs(yDelta)) {//LOS with player is closest in x direction
				face=false;
				if(xDelta>0) {
					xMove=-speed;
				}else if(xDelta<0) {
					xMove=speed;
				}
			}else if (Math.abs(yDelta)<Math.abs(xDelta)) {//LOS with player is closest in y direction
				face=false;
				if(yDelta>0) {
					yMove=-speed;
				}else if(yDelta<0) {
					yMove=speed;
				}
			}
		} else if(attacking) {
			return;
		} else {
			//move randomly
			moveTimer+=System.currentTimeMillis()-lastMoveTimer;
			lastMoveTimer=System.currentTimeMillis();
			if(moveTimer>=moveCooldown) {
				moveTimer=0;
				switch (rand.nextInt(5)) {
				//up
				case 0:
					xMove=0;
					yMove=speed;
					break;
					//down
				case 1:
					xMove=0;
					yMove=-speed;
					break;
					//left
				case 2:
					xMove=-speed;
					yMove=0;
					break;
					//right
				case 3:
					xMove=speed;
					yMove=0;
					break;
					//stop
				case 4:
					xMove=0;
					yMove=0;
					break;
				}
			}
		}
	}
	
	//add to score
	@Override
	public void die() {
		active=false;
		handler.getWorld().getEntityManager().getPlayer().setScore(handler.getWorld().getEntityManager().getPlayer().getScore()+75);
	}
	
	//set aggression
	public void setAggressive(boolean aggro) {
		aggressive = aggro;
	}
	
	//get animation frame for rendering
	private BufferedImage getCurrentAnimationFrame() {
		if (stunned) {
			if(lastDirection==Facing.LEFT) {
				return Assets.bull_stunned[1];
			}else if(lastDirection==Facing.RIGHT) {
				return Assets.bull_stunned[2];
			}else if(lastDirection==Facing.DOWN) {
				return Assets.bull_stunned[0];
			}else {
				return Assets.bull_stunned[3];
			}
		}else if(xMove<0) {
			lastDirection=Facing.LEFT;
			return animLeft.getCurrentFrame();
		}else if(xMove>0) {
			lastDirection=Facing.RIGHT;
			return animRight.getCurrentFrame();
		}else if (yMove<0) {
			lastDirection=Facing.UP;
			return animUp.getCurrentFrame();
		}else if (yMove>0) {
			lastDirection=Facing.DOWN;
			return animDown.getCurrentFrame();
		}else if (lastDirection==Facing.LEFT) {;
		return Assets.bull_left[1];
		}
		else if (lastDirection==Facing.RIGHT) {
			return Assets.bull_right[1];
		}
		else if (lastDirection==Facing.UP) {
			return Assets.bull_up[1];
		}
		else if (lastDirection==Facing.DOWN) {
			return Assets.bull_down[1];
		}
		//default animation to display if not condition is met.
		return Assets.bull_down[1];
	}
}
