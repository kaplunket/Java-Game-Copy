package dev.game.creatures;

import java.awt.Graphics;
import java.awt.Rectangle;
import dev.game.Handler;
import dev.game.entity.Entity;
import dev.game.states.Credits;
import dev.game.states.HighScore;
import dev.game.states.State;
import dev.launcher.Assets;

public class Boss extends Creature{
	private BossHandLeft leftHand;
	private BossHandRight rightHand;
	private boolean up, right;
	private float leftYDelta,rightXDelta,rightYDelta,leftXDelta;
	private int leftCooldown=30,leftAttackCount=0,leftAttackLoop=600,leftAttackLoopCount=0,meleeAttackCount=0,meleeAttackDelay=240,
			rightAttackCount=0,rightCooldown=120,invulnerable = 0,delay=0;
	//boss constructor
	public Boss(Handler handler, float x, float y, int width, int height,BossHandLeft leftHand,BossHandRight rightHand) {
		super(handler, x, y, width, height);
		bounds.x=(int) x;
		bounds.y=(int) y;
		bounds.width=width;
		bounds.height=height;
		this.maxHealth=40;
		this.health=40;
		this.id=7;
		this.leftHand=leftHand;
		this.rightHand=rightHand;
		this.damage=1;
	}

	//being hit by stun attacks increase delay between attacks
	@Override
	public void setStun(boolean stunned){
		if (stunned) {
			if (delay < 200) {
				delay+=20;
			}
		}
	}

	@Override
	public void tick() {
		idleMove();
		moveHands();
		checkAttacks();
		decayInvulnerable();
		checkHandHealth();
	}

	//check the health of the hands and apply damage to boss
	private void checkHandHealth() {
		int trueHealth = health-40;
		if(leftHand!=null) {
			if(leftHand.getHealth()<1) {
				leftHand.die();
				leftHand=null;
			}else {
				trueHealth+=leftHand.getHealth();
			}
		}
		if(rightHand!=null) {			
			if(rightHand.getHealth()<1) {
				rightHand.die();
				rightHand=null;
			}else {
				trueHealth+=rightHand.getHealth();
			}
		}
		if (trueHealth<1) {
			die();
		}
	}

	//count down invulnerability time
	private void decayInvulnerable() {
		if(invulnerable <0) {
			invulnerable--;
		}
	}

	//boss gets no knockback
	@Override
	public void hurt(int damage,int deltaX,int deltaY) {
		if(invulnerable <0) {
			return;
		} else {
			health -= damage;
			invulnerable = 40;
		}
		if (health<1) {
			die();
		}
	}

	//check if hands or head needs to perform an attack
	private void checkAttacks() {
		float yDelta = y-handler.getWorld().getEntityManager().getPlayer().getY();
		if(!(yDelta>-(this.getHeight()+20))) {
			meleeAttackCount=0;
			if(leftHand!=null) {
				leftHand.setAttacking(true);
				if(leftAttackLoopCount>=(leftAttackLoop+delay)) {
					leftAttackLoopCount=0;
				}
				if (leftAttackLoopCount<=150) {
					if(leftAttackCount>(leftCooldown+delay)) {
						leftAttackCount=0;
						leftHand.spreadAttack();
					}else {
						leftAttackCount++;
					}
					leftAttackLoopCount++;
				}else {
					leftAttackLoopCount++;
				}
			}
			if(rightHand!=null) {
				rightHand.setAttacking(true);
				if(rightAttackCount>(rightCooldown+delay)) {
					rightAttackCount=0;
					rightHand.targetAttack();
				}else {
					rightAttackCount++;
				}
			}
		}else {
			if(leftHand!=null) {
				leftHand.setAttacking(false);
			}
			if(rightHand!=null) {
				rightHand.setAttacking(false);
				rightAttackCount++;
				if(rightAttackCount>(rightCooldown+delay)) {
					rightAttackCount=rightCooldown+delay;
				}
			}
			if(meleeAttackCount>=(meleeAttackDelay+delay)) {
				meleeAttackCount=0;
				Rectangle cb = this.getCollisionBounds(0, 0);
				Rectangle ar = null;
				ar= new Rectangle();
				int arSize=50;
				ar.height=arSize;
				ar.x=cb.x;
				ar.y=cb.y+cb.height;
				ar.width=cb.width;
				for(Entity e : handler.getWorld().getEntityManager().getEntities()) {
					if(e.equals(this)) {continue;}
					if(e.getCollisionBounds(0, 0).intersects(ar)) {
						int deltaX=(int) ((this.getCollisionBounds(0, 0).x+this.getCollisionBounds(0, 0).width/2) - (e.getCollisionBounds(0, 0).x+e.getCollisionBounds(0, 0).width/2));
						int deltaY=(int) ((this.getCollisionBounds(0, 0).y+this.getCollisionBounds(0, 0).height/2) - (e.getCollisionBounds(0, 0).y+e.getCollisionBounds(0, 0).height/2));
						e.hurt(damage,deltaX,deltaY);
					}
				}
			}else {
				meleeAttackCount++;
			}
		}
	}

	//determine the movements of the hands
	private void idleMove() {
		if (leftYDelta>10||leftYDelta<-10) {
			up=!up;
		}
		if(up) {
			leftYDelta+=0.25;
		}else {
			leftYDelta-=0.25;
		}
		if (leftXDelta>15||leftXDelta<-15) {
			right=!right;
		}
		if(right) {
			leftXDelta+=0.25;
		}else {
			leftXDelta-=0.25;
		}
		rightXDelta=leftXDelta;
		rightYDelta=leftYDelta;
	}

	//move the hands
	private void moveHands() {
		if (leftHand!=null) {
			leftHand.setX(this.getX()+this.getWidth()/2-leftHand.getWidth()/2-100+leftXDelta);
			leftHand.setY(this.getY()+this.getHeight()/2-leftHand.getHeight()/2+100+leftYDelta);
		}
		if (rightHand!=null) {
			rightHand.setX(this.getX()+this.getWidth()/2-rightHand.getWidth()/2+100+rightXDelta);
			rightHand.setY(this.getY()+this.getHeight()/2-rightHand.getHeight()/2+100+rightYDelta);
		}
	}

	//render eyetracking on the boss
	@Override
	public void render(Graphics g) {
		if(handler.getWorld().getEntityManager().getPlayer().getX()<x) {
			g.drawImage(Assets.BossHead[2],(int)(x-handler.getGameCamera().getxOffset()),(int)(y-handler.getGameCamera().getyOffset()-(meleeAttackCount*50)/(meleeAttackDelay+delay)),width,height,null);
		}else if(handler.getWorld().getEntityManager().getPlayer().getX()>(x+width)){
			g.drawImage(Assets.BossHead[1],(int)(x-handler.getGameCamera().getxOffset()),(int)(y-handler.getGameCamera().getyOffset()-(meleeAttackCount*50)/(meleeAttackDelay+delay)),width,height,null);
		}else {
			g.drawImage(Assets.BossHead[0],(int)(x-handler.getGameCamera().getxOffset()),(int)(y-handler.getGameCamera().getyOffset()-(meleeAttackCount*50)/(meleeAttackDelay+delay)),width,height,null);
		}
	}

	//upon death of the boss, calculate high score and change states
	@Override
	public void die() {
		active=false;
		HighScore highScore=new HighScore(handler);
		handler.getWorld().getEntityManager().getPlayer().setScore(handler.getWorld().getEntityManager().getPlayer().getScore()+1000+handler.getHud().getTimeLimit()*2+handler.getWorld().getEntityManager().getPlayer().getInventory().getItemCount(1)*200+handler.getWorld().getEntityManager().getPlayer().getInventory().getItemCount(0)*50+handler.getWorld().getEntityManager().getPlayer().getHealth()*20+handler.getWorld().getEntityManager().getPlayer().getCorruptionMax()-handler.getWorld().getEntityManager().getPlayer().getCorruption() );
		if(leftHand!=null) {leftHand.die();}
		if(rightHand!=null) {rightHand.die();}
		highScore.checkHighScore(handler.getWorld().getEntityManager().getPlayer().getScore());
		Credits credits = new Credits(handler);
		State.setState(credits);
	}

}
